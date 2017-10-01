/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lasaro
 */
public class CTClient {

    private CTInterface server;
    private int myUserId;
    private String name;
    private boolean inMatch;
    private MyColorEnum myColor;
    private String opponent;
    private int placedPieces;

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

    private CTClient(CTInterface chungToi) {
        this.server = chungToi;
        this.placedPieces = 0;
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
                    System.out.println("Player already signed in, try again.");
                    break;
                case -2:
                    throw new Exception("Maximun number of players reached!");
            }
        }
    }

    private void play() throws Exception {
        System.out.println("Waiting match start...");
        waitMatchStart();
        this.opponent = this.server.getOpponent(this.myUserId);
        System.out.println(String.format("Your match is against %s. You are %s. And the best will win.", opponent, this.myColor.getName()));
        int matchState = 0;

        //Keep in the loop while the match is active
        gameloop:
        do {
            /*
            ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 
            1 (sim), 2 (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
             */
            matchState = this.server.isMyTurn(this.myUserId);
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
                    System.out.println("Your turn!");
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
        } while (matchState <= 1);
    }

    private void waitMatchStart() throws Exception {
        this.inMatch = false;
        System.out.println("Please, wait while someone joins.");
        while (!this.inMatch) {
            int hm = this.server.haveMatch(this.myUserId);
            switch (hm) {
                case -2:
                    throw new Exception("Player waiting timeout");
                case -1:
                    throw new Exception("Error querying for match");
                case 1:
                    this.inMatch = true;
                    this.myColor = MyColorEnum.WHITE;
                    break;
                case 2:
                    this.inMatch = true;
                    this.myColor = MyColorEnum.BLACK;
                case 0:
                    //no match, continue querying;
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
        Integer position = null;
        Integer orientation = null;
        int ret = -1;

        if (this.placedPieces < 3) {
            System.out.println("Placing piece.");
            try {
                System.out.println("What is the position of the new piece?\n[0-8]");
                position = scan.nextInt();
                System.out.println("What is the orientation of the new piece?");
                System.out.println("0 - perpendicular\n1 - diagonal");
                orientation = scan.nextInt();
            } catch (Exception e) {
            }
            if (position != null && orientation != null) {
                ret = this.server.placePiece(this.myUserId, position, orientation);
                if (ret == 1) {
                    this.placedPieces++;
                }
            }
        } else {
            Integer direction = null;
            Integer movement = null;
            Integer newOrientation = null;
            System.out.println("Moving pieces.");

            try {
//                System.out.println("What is the current position of the piece?\n[0-8]");
//                position = scan.nextInt();
//                System.out.println("What is the direction of the moviment?");
//                System.out.println("0 - up left");
//                System.out.println("1 - up");
//                System.out.println("2 - up right");
//                System.out.println("3 - left");
//                System.out.println("4 - still");
//                System.out.println("5 - right");
//                System.out.println("6 - down left");
//                System.out.println("7 - down");
//                System.out.println("8 - down right");
//                direction = scan.nextInt();
//                System.out.println("What is the lenght of the movement?");
//                movement = scan.nextInt();
//                System.out.println("What is the new orientation of the piece?");
//                System.out.println("0 - perpendicular\n1 - diagonal");
//                newOrientation = scan.nextInt();
            } catch (Exception e) {
            }

            if (position != null && direction != null && movement != null && newOrientation != null) {
                ret = this.server.movePiece(this.myUserId, position, direction, movement, newOrientation);
            }
        }

        switch (ret) {
            case 2:
                System.out.println("Matched ended, probably timed out...");
                break;
            case 1:
                System.out.println("Move done!");
                break;
            case 0:
                System.out.println("Invalid movement.");
                break;
            case -1:
                System.out.println("Invalid parameters.");
                break;
            case -2:
                System.out.println("Match not started yet");
                break;
            case -3:
                System.out.println("Not your turn.");
                break;
            default:
                System.out.println("Unknown return (" + ret + ")");
                break;
        }
    }

    private void printBoard() throws Exception {
        String board = this.server.getBoard(this.myUserId);
        if (!board.equals("")) {
            char[] boardChars = board.toCharArray();
            board = String.format("%s|%s|%s\n­+­+­\n%s|%s|%s\n­+­+­\n%s|%s|%s\n", boardChars[0], boardChars[1], boardChars[2], boardChars[3], boardChars[4], boardChars[5], boardChars[6], boardChars[7], boardChars[8]);
            System.out.println(board);
        }
    }

    private enum MyColorEnum {
        WHITE("white"), BLACK("black");

        private String colorName;

        private MyColorEnum(String colorName) {
            this.colorName = colorName;
        }

        public String getName() {
            return this.colorName;
        }
    }
}
