/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.server;

/**
 *
 * @author lasaro
 */
public enum MatchState {

    WHITE_TURN(false, 1, 0), BLACK_TURN(false, 0, 1),
    EVEN(true, 4, 4), WHITE_WIN(true, 2, 3), BLACK_WIN(true, 3, 2), WHITE_WIN_WO(true, 5, 6 ), BLACK_WIN_WO(true, 6, 5);
    private boolean endState;
    private int whiteResponse;
    private int blackResponse;
    
    private MatchState(boolean endState, int whiteResponse, int blackResponse){
        this.endState = endState;
        this.whiteResponse = whiteResponse;
        this.blackResponse = blackResponse;
    }
    
    public int getWhiteResponse() {
        return this.whiteResponse;
    }

    public int getBlackResponse() {
        return this.blackResponse;
    }

    public boolean isEndState() {
        return this.endState;
    }
}
