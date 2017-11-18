/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.server;

/**
 *
 * @author Lásaro
 */
public class Player {

    private int userId;
    private String name;
    private Match match;
    private Long signupTime;

    Player(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.match = null;
        this.signupTime = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public boolean isTimedOut() {
        Long wt = (System.currentTimeMillis() - this.signupTime) / 1000;
        return (wt >= Match.PLAYER_TIMEOUT);
    }

    public int isMyTurn() {
        if (this.match != null) {
            return this.match.isMyTurn(this.userId);
        } else {
            return -1;
        }
    }

    public String getBoard() {
        if (this.match != null) {
            return this.match.getBoard();
        } else {
            return "";
        }
    }

    public int placePiece(int position, int orientation) {
        if (this.match != null) {
            if (this.match.isActive()) {
                return this.match.placePiece(this.userId, position, orientation);
            } else {
                return 2;
            }
        } else {
            return -2;
        }
    }

    public int movePiece(int currentPosition, int direction, int movement, int newOrientation) {
        if (this.match != null) {
            if (this.match.isActive()) {
                return this.match.movePiece(this.userId, currentPosition, direction, movement, newOrientation);
            } else {
                return 2;
            }
        } else {
            return -2;
        }
    }

    public boolean isWhitePlayer() {
        if (this.match != null) {
            return this.match.isWhitePlayer(this.userId);
        }
        return false;
    }

    public boolean isBlackPlayer(int userId) {
        if (this.match != null) {
            return this.match.isBlackPlayer(this.userId);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + this.userId + "]" + this.name;
    }
}
