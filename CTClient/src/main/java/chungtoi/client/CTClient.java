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
                for (int i = 0; i < args.length; i++) {
                    BatchClient bc = new BatchClient(chungToi);
                    bc.executaTeste(args[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}
