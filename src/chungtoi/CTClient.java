/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Naming;

/**
 *
 * @author lasaro
 */
public class CTClient {
    public static void main(String[] args) {
        int myUserId;

        if (args.length != 1) {
            System.out.println("Usage: java CTClient <server_name>");
            System.exit(1);
        }
        try {
            CTInterface chungToi = (CTInterface)Naming.lookup("//" + args[0] + "/ChungToi");
            myUserId = chungToi.PlayerSignup("John");
            switch (myUserId) {
                case -1:
                    System.out.println("Player already signed in!");
                    break;
                case -2:
                    System.out.println("Maximun number of players reached!");
                    break;
                default:
                    System.out.println("Your user id "+myUserId);
                    break;
            }
        } catch (Exception e) {
            System.out.println("NotasClient failed.");
            e.printStackTrace();
        }
    }
}
