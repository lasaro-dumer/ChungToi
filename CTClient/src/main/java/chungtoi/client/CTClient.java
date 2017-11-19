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
import java.nio.file.Path;
import java.nio.file.Files;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author lasaro
 */
public class CTClient {
    private static String cloudURL = "http://solr-impbd.eastus.cloudapp.azure.com:8080/ctwebservice/ChungToiWS";

    public static void main(String[] args) {
        try {
            ChungToiWS service = new ChungToiWS();
            ChungToi chungToi = service.getChungToiPort();

            String path = "";
            boolean useCloud = false;
            for (int i=0; i<args.length; i++) {
                if(args[i].equals("-h")){
                    printHelp();
                    return;
                }else if(args[i].equals("-c")){
                    useCloud = true;
                }else {
                    path = args[i];
                }
            }

            if(useCloud){
                BindingProvider bindingProvider = (BindingProvider) chungToi;
                bindingProvider.getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    cloudURL);
            }
            System.out.println(String.format("Endpoint being used: %s", (useCloud?"cloud":"localhost")));

            if(path.equals("")){
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
