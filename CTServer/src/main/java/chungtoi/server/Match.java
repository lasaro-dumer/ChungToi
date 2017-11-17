/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lasaro
 */
public class Match {

    private static final char WHITE_CHAR_P = 'C';
    private static final char WHITE_CHAR_D = 'c';
    private static final char BLACK_CHAR_P = 'E';
    private static final char BLACK_CHAR_D = 'e';
    public static final int PLAYER_TIMEOUT = 60;

    private Integer whitePlayerId;
    private Integer blackPlayerId;
    private String whitePlayerName;
    private String blackPlayerName;
    private MatchState state;
    private Long lastActionTime, endedTime;
    private boolean endedByWhite, endedByBlack;
    private char[] board;
    private int whitePiecesCount;
    private int blackPiecesCount;

    public Match(Integer whitePlayerId, String whiteName, Integer blackPlayerId, String blackName) {
        this.whitePlayerId = whitePlayerId;
        this.whitePlayerName = whiteName;
        this.blackPlayerId = blackPlayerId;
        this.blackPlayerName = blackName;
        this.state = MatchState.WHITE_TURN;
        this.lastActionTime = System.currentTimeMillis();
        this.endedByWhite = false;
        this.endedByBlack = false;
        this.endedTime = null;
        this.board = new char[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = '.';
        }
        this.whitePiecesCount = 0;
        this.blackPiecesCount = 0;
    }

    public Integer getWhitePlayerId() {
        return this.whitePlayerId;
    }

    public String getWhitePlayerName(){
        return this.whitePlayerName;
    }

    public Integer getBlackPlayerId() {
        return this.blackPlayerId;
    }

    public String getBlackPlayerName(){
        return this.blackPlayerName;
    }

    public boolean isWhitePlayer(int userId) {
        return this.whitePlayerId == userId;
    }

    public boolean isBlackPlayer(int userId) {
        return this.blackPlayerId == userId;
    }

    public int isMyTurn(int userId) {
        this.checkTimeouts();
        this.checkWinConditions();
        if (this.isWhitePlayer(userId)) {
            return this.state.getWhiteResponse();
        } else {
            return this.state.getBlackResponse();
        }
    }

    private void checkTimeouts() {
        Long timeSpan = (System.currentTimeMillis() - this.lastActionTime) / 1000;
        if (timeSpan >= PLAYER_TIMEOUT) {
            if (this.state == MatchState.WHITE_TURN) {
                this.state = MatchState.BLACK_WIN_WO;
            } else if (this.state == MatchState.BLACK_TURN) {
                this.state = MatchState.WHITE_WIN_WO;
            }
        }
    }

    public void endMatch(int userId) {
        if (this.isWhitePlayer(userId) && !this.endedByWhite) {
            this.endedByWhite = true;
        } else if (this.isBlackPlayer(userId) && !this.endedByBlack) {
            this.endedByBlack = true;
        }

        if (this.endedByBlack && this.endedByWhite && this.endedTime == null) {
            this.endedTime = System.currentTimeMillis();
        }
    }

    public Long getTimeSinceEnded() {
        return this.endedTime;
    }

    public boolean isActive() {
        this.checkTimeouts();
        if (this.state.isEndState() && this.endedTime == null) {
            this.endedTime = System.currentTimeMillis();
        }
        return this.endedTime == null;
    }

    public String getBoard(int userId) {
        String ret = "";
        for (int i = 0; i < board.length; i++) {
            ret += board[i];
        }
        return ret;
    }

    public int placePiece(int userId, int position, int orientation) {
        int ret = -3;
        boolean whitePlayer = this.isWhitePlayer(userId);
        boolean blackPlayer = this.isBlackPlayer(userId);
        if ((whitePlayer && this.state != MatchState.WHITE_TURN)
                || (blackPlayer && this.state != MatchState.BLACK_TURN)) {
            return -4;
        }

        if((whitePlayer && this.whitePiecesCount == 3) || (blackPlayer && this.blackPiecesCount == 3)){
            return -5;
        }

        if(!(position >= 0 && position <= 8)
            || (orientation != 0 && orientation != 1))
            return ret;//-3

        if (this.board[position] == '.') {
            char piece = getPieceChar(whitePlayer, blackPlayer, orientation);
            if (piece != '.') {
                this.board[position] = piece;
                this.lastActionTime = System.currentTimeMillis();
                if (whitePlayer) {
                    this.whitePiecesCount++;
                    this.state = MatchState.BLACK_TURN;
                } else {
                    this.blackPiecesCount++;
                    this.state = MatchState.WHITE_TURN;
                }
                ret = 1;
            }
        } else {
            ret = 0;
        }

        return ret;
    }

    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) {
        int ret = -3;
        Movement mov = Movement.INVALID;
        boolean whitePlayer = this.isWhitePlayer(userId);
        boolean blackPlayer = this.isBlackPlayer(userId);

        if ((whitePlayer && this.state != MatchState.WHITE_TURN)
                || (blackPlayer && this.state != MatchState.BLACK_TURN)) {
            return -4;
        }

        if((whitePlayer && this.whitePiecesCount < 3) || (blackPlayer && this.blackPiecesCount < 3)){
            return -5;
        }
        if((this.board[currentPosition] != '.'))
            return 0;

        System.out.println("is player turn " + userId);
        if (currentPosition >= 0 && currentPosition <= 8 && (newOrientation == 0 || newOrientation == 1)) {
            char piece = this.board[currentPosition];
            int currOrientation = (piece == WHITE_CHAR_P || piece == BLACK_CHAR_P) ? 0 : 1;

            boolean isPieceOwner = (whitePlayer && (piece == WHITE_CHAR_D || piece == WHITE_CHAR_P))
                    || (blackPlayer && (piece == BLACK_CHAR_D || piece == BLACK_CHAR_P));

            if (isPieceOwner) {
                mov = Movement.processMovement(currentPosition, currOrientation, direction, movement);
            }
        } else {
            return ret;
        }

        boolean moveIsValid = (mov != Movement.STILL && this.board[mov.getNewPosition()] == '.')
                || (mov == Movement.STILL && this.board[mov.getNewPosition()] != '.');
        System.out.println("movement:" + mov + " and moveIsValid:" + moveIsValid);
        System.out.println("newPosition[" + mov.getNewPosition() + "]= " + this.board[mov.getNewPosition()]);
        if (mov != Movement.INVALID && moveIsValid) {
            char piece = getPieceChar(whitePlayer, blackPlayer, newOrientation);

            this.board[currentPosition] = '.';
            this.board[mov.getNewPosition()] = piece;
            this.lastActionTime = System.currentTimeMillis();
            if (whitePlayer) {
                this.state = MatchState.BLACK_TURN;
            } else {
                this.state = MatchState.WHITE_TURN;
            }
            ret = 1;
        } else {
            ret = 0;
        }
        return ret;
    }

    private char getPieceChar(boolean whitePlayer, boolean blackPlayer, int orientation) {
        char piece = '.';
        if (whitePlayer && (orientation == 0)) {
            piece = WHITE_CHAR_P;
        } else if (whitePlayer && (orientation == 1)) {
            piece = WHITE_CHAR_D;
        } else if (blackPlayer && (orientation == 0)) {
            piece = BLACK_CHAR_P;
        } else if (blackPlayer && (orientation == 1)) {
            piece = BLACK_CHAR_D;
        }
        return piece;
    }

    private void checkWinConditions() {
        char[] tb = new char[this.board.length];
        for (int i = 0; i < tb.length; i++) {
            tb[i] = (new String(this.board, i, 1)).toUpperCase().toCharArray()[0];
        }

        char winP = '.';
        if (tb[0] == tb[1] && tb[0] == tb[2]) {
            winP = tb[0];
        } else if (tb[3] == tb[4] && tb[3] == tb[5]) {
            winP = tb[3];
        } else if (tb[6] == tb[7] && tb[6] == tb[8]) {
            winP = tb[6];
        } else if (tb[0] == tb[3] && tb[0] == tb[6]) {
            winP = tb[0];
        } else if (tb[1] == tb[4] && tb[1] == tb[7]) {
            winP = tb[1];
        } else if (tb[2] == tb[5] && tb[2] == tb[8]) {
            winP = tb[2];
        } else if (tb[0] == tb[4] && tb[4] == tb[8]) {
            winP = tb[0];
        } else if (tb[6] == tb[4] && tb[6] == tb[2]) {
            winP = tb[6];
        }

        if (winP != '.') {
            if (winP == WHITE_CHAR_P) {
                this.state = MatchState.WHITE_WIN;
            } else if (winP == BLACK_CHAR_P) {
                this.state = MatchState.BLACK_WIN;
            }
        }
    }
}
