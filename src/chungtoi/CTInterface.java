/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author lasaro
 */
public interface CTInterface extends Remote{
    public int playerSignup(String name) throws Exception;
    public int endMatch(int userId) throws Exception;
    public int haveMatch(int userId) throws Exception;
    public int isMyTurn(int userId) throws Exception;
    public String getBoard(int userId) throws Exception;
    public int placePiece(int userId, int position, int orientation) throws Exception;
    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws Exception;
    public String getOpponent(int userId) throws Exception;
}
