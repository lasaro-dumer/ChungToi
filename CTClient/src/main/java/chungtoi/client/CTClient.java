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
        // if (args.length != 1) {
        //     System.out.println("Usage: java CTClient <server_name>");
        //     System.exit(1);
        // }
        try {
            ChungToiWS service = new ChungToiWS();
            ChungToi chungToi = service.getChungToiPort();

            InteractiveClient client = new InteractiveClient(chungToi);
            client.startGame();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}
