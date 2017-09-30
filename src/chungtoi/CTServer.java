/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            ChungToi chungToi = new ChungToi();
            Naming.rebind("ChungToi", chungToi);
            System.out.println("NotasServer is ready.");

            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        int removedMatches = chungToi.clearEndedMatches();
                        if (removedMatches > 0) {
                            System.out.println(String.format("Removed %s matches.", removedMatches));
                        }
                        int removedPlayers = chungToi.checkPlayersTimeouts();
                        if (removedPlayers > 0) {
                            System.out.println(String.format("Removed %s players", removedPlayers));
                        }
                    } catch (Exception e) {
                        System.out.println(String.format("Exception in scheduled task: %s", e.getMessage()));
                    }
                }
            }, 0, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("NotasServer failed:");
            e.printStackTrace();
        }
    }

}
