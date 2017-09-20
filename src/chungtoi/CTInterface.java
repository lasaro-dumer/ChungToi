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
    public int PlayerSignup(String name) throws RemoteException;
    public int EndMatch(int userId) throws RemoteException;
    public int HaveMatch(int userId) throws RemoteException;
    public int IsMyTurn(int userId) throws RemoteException;
    public String GetBoard(int userId) throws RemoteException;
    public int PlacePiece(int userId, int position, int orientation) throws RemoteException;
    public int MovePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws RemoteException;
    public String GetOpponent(int UserId) throws RemoteException;
}
