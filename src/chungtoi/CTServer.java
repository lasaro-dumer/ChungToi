/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author lasaro
 */
public class CTServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (RemoteException e) {
            System.out.println("RMI registry already running.");
        }
        try {
            Naming.rebind("ChungToi", new ChungToi());
            System.out.println("NotasServer is ready.");
        } catch (Exception e) {
            System.out.println("NotasServer failed:");
            e.printStackTrace();
        }
    }

}
