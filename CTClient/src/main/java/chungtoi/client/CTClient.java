/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.client;

import chungtoi.client.proxy.ChungToiWS;
import chungtoi.client.proxy.ChungToi;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author lasaro
 */
public class CTClient {
    private static String cloudURL = "http://solr-impbd.eastus.cloudapp.azure.com:8080/ctwebservice/ChungToiWS";
    private static String cloudURL_WSDL = cloudURL+"?wsdl";

    public static void main(String[] args) {
        try {

            String path = "";
            boolean useCloud = false;
            String secondaryURL = "";
            String baseDir = "";
            for (int i=0; i<args.length; i++) {
                if(args[i].equals("-h")){
                    printHelp();
                    return;
                }else if(args[i].equals("-c")){
                    useCloud = true;
                }else if(args[i].equals("-u")){
                    i++;
                    secondaryURL = args[i];
                }else if(args[i].equals("-b")){
                    i++;
                    baseDir = args[i];
                }else {
                    path = args[i];
                }
            }

            ChungToiWS service = null;
            ChungToi chungToi = null;
            try{
                if(useCloud){
                    secondaryURL = cloudURL;
                }

                if(secondaryURL.equals("")){
                    service = new ChungToiWS();
                    chungToi = service.getChungToiPort();
                }else{
                    service = new ChungToiWS(new URL(secondaryURL+"?wsdl"));
                    chungToi = service.getChungToiPort();
                    BindingProvider bindingProvider = (BindingProvider) chungToi;
                    bindingProvider.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                        secondaryURL);
                }
            }
            catch(Exception e){
                service = null;
                chungToi = null;
            }

            System.out.println(String.format("Endpoint being used: %s", (secondaryURL.equals("")?"localhost":secondaryURL)));

            if(service == null || chungToi == null){
                System.out.println(String.format("Unable to estabilish connection with the server"));
                return;
            }

            if(!baseDir.equals("")){
                List sequential = new ArrayList<>();
                sequential.add(Paths.get(baseDir,"ChungToi-0000").toString());
                sequential.add(Paths.get(baseDir,"ChungToi-0100").toString());
                sequential.add(Paths.get(baseDir,"ChungToi-1000").toString());
                sequential.add(Paths.get(baseDir,"ChungToi-3000").toString());
                List parallel1 = new ArrayList<>();
                parallel1.add(Paths.get(baseDir,"ChungToi-2000").toString());
                parallel1.add(Paths.get(baseDir,"ChungToi-2500").toString());
                List parallel2 = new ArrayList<>();
                parallel2.add(Paths.get(baseDir,"ChungToi-4000").toString());
                parallel2.add(Paths.get(baseDir,"ChungToi-4500").toString());
                BatchClient bc = new BatchClient(chungToi);
                bc.runSequential(sequential);
                bc.runParallel(parallel1);
                bc.runParallel(parallel2);
            }else if(path.equals("")){
                System.out.println("Starting InteractiveClient");
                InteractiveClient client = new InteractiveClient(chungToi);
                client.startGame();
            }
            else{
                System.out.println("Starting BatchClient");

                Path file = new File(path).toPath();

                boolean exists =      Files.exists(file);        // Check if the file exists
                boolean isDirectory = Files.isDirectory(file);   // Check if it's a directory
                boolean isFile =      Files.isRegularFile(file); // Check if it's a regular file
                if(exists){
                    List<Path> filesToProcess = new ArrayList<>();
                    if(isDirectory){
                        File dir = new File(path);
                        File[] files = dir.listFiles();
                        for (File f : files) {
                            Path filePath = f.toPath();
                            if(filePath.toString().endsWith(".in"))
                                filesToProcess.add(filePath);
                        }
                        System.out.println("Found ["+files.length+"] files in folder ["+path+"], and ["+filesToProcess.size()+"] will be processed");
                    }
                    else{
                        filesToProcess.add(file);
                    }
                    for (Path f : filesToProcess) {
                        if(f.toString().endsWith(".in")){
                            batchTestFile(chungToi, f.toString());
                        } else if (f.toString().endsWith(".lst")) {
                            System.out.println("This is a test list");
                            List<String> listItems = new ArrayList<>();
                            listItems = Files.lines(f).collect(Collectors.toList());
                            for (String item : listItems) {
                                batchTestFile(chungToi, item);
                            }
                        } else {
                            System.out.println("Invalid file ["+f.toString()+"] skipped.");
                        }
                    }
                }
                else{
                    System.out.println("Path ["+path+"] is invalid.");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }

    private static void batchTestFile(ChungToi chungToi, String filePath) throws Exception{
        String fileRad = filePath.substring(0, filePath.lastIndexOf("."));
        System.out.println("fileRad: " + fileRad);
        BatchClient bc = new BatchClient(chungToi);
        bc.executaTeste(fileRad);
    }

    private static void printHelp(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n", "Usage:"));
        sb.append(String.format("%s\n", "\tCTClient-*.jar [-c] [file|directory]"));
        sb.append(String.format("%s\n", "\t-c\tuse could URL ("+cloudURL+")"));
        sb.append(String.format("\n"));
        sb.append(String.format("%s\n", "\tfile|directory"));
        sb.append(String.format("%s\n", "\t\tIf the argument is a file it can be:"));
        sb.append(String.format("%s\n", "\t\t\t.in : a file to be processed, generating a .out"));
        sb.append(String.format("%s\n", "\t\t\t.lst: a file with one or more .in to be processed"));
        sb.append(String.format("%s\n", "\t\tIf the argument is a directory all the .in file in it will be processed"));
        System.out.println(sb.toString());
    }
}
