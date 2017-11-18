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
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 *
 * @author lasaro
 */
public class CTClient {
    public static void main(String[] args) {
        try {
            ChungToiWS service = new ChungToiWS();
            ChungToi chungToi = service.getChungToiPort();

            if(args.length == 0){
                InteractiveClient client = new InteractiveClient(chungToi);
                client.startGame();
            }
            else{
                String path = args[0];
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
                            if(filePath.toString().endsWith(".in") || filePath.toString().endsWith(".lst"))
                                filesToProcess.add(filePath);
                        }
                        System.out.println("Found files "+files.length+" in folder "+path+", and "+filesToProcess.size()+" will be processed");
                    }
                    else{
                        filesToProcess.add(file);
                    }
                    for (Path f : filesToProcess) {
                        if(f.toString().endsWith(".in")){
                            String fileRad = f.toString();
                            fileRad = fileRad.substring(0, fileRad.lastIndexOf("."));
                            System.out.println("fileRad: " + fileRad);
                            BatchClient bc = new BatchClient(chungToi);
                            bc.executaTeste(fileRad);
                        } else if (f.toString().endsWith(".lst")) {
                            System.out.println("This is a test list");
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
}
