/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author lasaro
 */
public class CTClient {

    private CTInterface server;
    private int myUserId;
    private String name;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CTClient <server_name>");
            System.exit(1);
        }
        try {
            CTInterface chungToi = (CTInterface) Naming.lookup("//" + args[0] + "/ChungToi");
            CTClient client = new CTClient(chungToi);
            client.signup();
            client.play();
        } catch (Exception e) {
            //System.out.println("NotasClient failed.");
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private boolean inMatch;
    private boolean isWhite;

    private CTClient(CTInterface chungToi) {
        this.server = chungToi;
    }

    private void signup() throws RemoteException, Exception {
        Scanner scan = new Scanner(System.in);
        this.myUserId = -3;
        while (this.myUserId < 0) {
            System.out.println("What's your name?");
            this.name = scan.next();
            this.myUserId = this.server.playerSignup(name);
            switch (myUserId) {
                case -1:
                    System.out.println("Player already signed in!");
                    break;
                case -2:
                    throw new Exception("Maximun number of players reached!");
            }
        }
    }

    private void play() throws Exception {
        System.out.println("Waiting match start...");
        waitMatchStart();
        //Keep in the loop while the match is active
        gameloop:
        while (this.server.haveMatch(this.myUserId) > 0) {
            /*
            ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 
            1 (sim), 2 (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
             */
            int matchState = this.server.isMyTurn(this.myUserId);
            switch (matchState) {
                case -2:
                    //no opponent yet
                    continue;
                case -1:
                    System.out.println("Error");
                    break;
                case 0:
                    //not your turn yet
                    continue;
                case 1:
                    makeMove();
                    break;
                case 2:
                    System.out.println("You won!!");
                    break gameloop;
                case 3:
                    System.out.println("You lost!! :(");
                    break gameloop;
                case 4:
                    System.out.println("You neither won or lost. =/");
                    break gameloop;
                case 5:
                    System.out.println("You won!! (by WO)");
                    break gameloop;
                case 6:
                    System.out.println("You lost!! (by WO)");
                    break gameloop;
            }
            Thread.sleep(500);
        }
    }

    private void waitMatchStart() throws Exception {
        this.inMatch = false;
        while (!this.inMatch) {
            int hm = this.server.haveMatch(this.myUserId);
            switch (hm) {
                case -2:
                    throw new Exception("Player waiting timeout");
                case -1:
                    throw new Exception("Error querying for match");
                case 1:
                    this.inMatch = true;
                    this.isWhite = true;
                    break;
                case 2:
                    this.inMatch = true;
                    this.isWhite = false;
                case 0:
                    //no match, continue querying;
                    System.out.println("no match, continue querying");
                    break;
                default:
                    //unknown response, keep trying
                    break;
            }
            if (!this.inMatch) {
                Thread.sleep(1000);
            }
        }
    }

    private void makeMove() throws Exception {
        printBoard();
        Scanner scan = new Scanner(System.in);
        int pos = scan.nextInt();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void printBoard() throws Exception {
        String board = this.server.getBoard(this.myUserId);
        if (!board.equals("")) {
            char[] boardChars = board.toCharArray();
            board = String.format("%s|%s|%s\n­+­+­\n%s|%s|%s\n­+­+­\n%s|%s|%s\n", boardChars[0], boardChars[1], boardChars[2], boardChars[3], boardChars[4], boardChars[5], boardChars[6], boardChars[7], boardChars[8]);
            System.out.println(board);
        }
    }
}
