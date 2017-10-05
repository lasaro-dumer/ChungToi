/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.common;

import java.rmi.Remote;

/**
 *
 * @author lasaro
 */
public interface CTInterface extends Remote {

    /**
     * Sign up a new player. After signup the player remains in the application
     * registry while waits for a match and durigin the duration of it. When a
     * match ends, its players will be signed out
     *
     * @param name the name of the player signing up
     * @return the id of the player (to be used in further communications) OR -1
     * in case it's already signed up OR -2 if the maxium number of players is
     * reached
     */
    public int playerSignup(String name) throws Exception;

    /**
     * The player gives the match for ended. A player should give its match for
     * ended when it either loses or wins the match. This allow the server to
     * know that both players can be signed out
     *
     * @param userId the id of the player
     * @return 1 to error and 0 to success
     * @see #playerSignup(String name)
     */
    public int endMatch(int userId) throws Exception;

    /**
     * Allows the client to know when a player has a match and which piece it
     * will be playing with. Will also be usefull to know if the waiting time
     * was exceeded
     *
     * @param userId the id of the player
     * @return -2 wait time exceeded <br>
     * -1 error <br>
     * 0 no match yet <br>
     * 1 have match and player plays as white pieces (C and c) <br>
     * 2 have match and player plays as black pieces (E and e)
     * @see #playerSignup(String name)
     */
    public int haveMatch(int userId) throws Exception;

    /**
     * Allows the client to know a player's turn OR if the match has ended and
     * in wich result
     *
     * @param userId the id of the player
     * @return -2 no match with two players yet <br>
     * -1 error <br>
     * 0 not player's turn yet <br>
     * 1 player's turn <br>
     * 2 player won <br>
     * 3 player lose <br>
     * 4 match ended even<br>
     * 5 winner by WO <br>
     * 6 loser by WO
     * @see #playerSignup(String name)
     * @see #haveMatch(int userId)
     */
    public int isMyTurn(int userId) throws Exception;

    /**
     * Retrieves the board of the current match of the player. The board will be
     * formated as a string and each character represents a piece as
     * follows:<br>
     * <ul>
     * <li><b>C</b>-white player perpendicular</li>
     * <li><b>c</b>-white player diagonal</li>
     * <li><b>E</b>-black player perpendicular</li>
     * <li><b>e</b>-black player diagonal</li>
     * <li><b>.</b>-an empty cell</li>
     * </ul>
     * Example:<br>
     * The string 'C.E.e.CEc' represents the board:<br>
     * <table style="font-family: monospace;border: 1px solid black;border-collapse:collapse;">
     * <tr>
     * <td style="border: 1px solid black;">C</td><td style="border: 1px solid black;">.</td><td style="border: 1px solid black;">E</td>
     * </tr>
     * <tr>
     * <td style="border: 1px solid black;">.</td><td style="border: 1px solid black;">e</td><td style="border: 1px solid black;">.</td>
     * </tr>
     * <tr>
     * <td style="border: 1px solid black;">C</td><td style="border: 1px solid black;">E</td><td style="border: 1px solid black;">c</td>
     * </tr>
     * </table>
     *
     * @param userId the id of the player
     * @return an empty String in case of error OR the board
     * @see #playerSignup(String name)
     */
    public String getBoard(int userId) throws Exception;

    /**
     * Allows the client to place a piece in the given position using the
     * desired orientation. The player must be in a match to place pieces and it
     * can place only 3 pieces, after that they should be moved using
     * {@link #movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) movePiece}
     *
     * @param userId the id of the player
     * @param position the position to place the piece
     * @param orientation the orientation of the piece. Where:<br>
     * 0 - perpendicular<br>
     * 1 - diagonal
     * @return a code representing the final status of placing the piece, as
     * follows:<br>
     * 2 match ended, timeout<br>
     * 1 placed alright<br>
     * 0 invalid position<br>
     * -1 invalid parameters<br>
     * -2 match not started yet<br>
     * -3 not player's turn<br>
     * @see #playerSignup(String name)
     * @see #movePiece(int userId, int currentPosition, int direction, int
     * movement, int newOrientation)
     */
    public int placePiece(int userId, int position, int orientation) throws Exception;

    /**
     * Used to move pieces, after the 3 pieces were placed
     *
     * @param userId the id of the player
     * @param currentPosition the current position of the piece to move
     * @param direction the direction of the movement, as follows:<br>
     * 0 upper left<br>
     * 1 up<br>
     * 2 up right<br>
     * 3 left<br>
     * 4 stand still<br>
     * 5 right<br>
     * 6 down left<br>
     * 7 down<br>
     * 8 down right<br>
     * @param movement the amount of cells to move
     * @param newOrientation the new orientation of the piece. Where:<br>
     * 0 - perpendicular<br>
     * 1 - diagonal
     * @return a code representing the final status of placing the piece, as
     * follows:<br>
     * 2 match ended, probably timeout<br>
     * 1 moved alright<br>
     * 0 invalid move<br>
     * -1 invalid parameters<br>
     * -2 match not started yet<br>
     * -3 not player's turn
     * @see #playerSignup(String name)
     * @see #placePiece(int userId, int position, int orientation)
     */
    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws Exception;

    /**
     * Used to know the name of the opponent of the player
     *
     * @param userId the id of the player
     * @return an empty string in case of error OR the name of the opponent
     * @throws Exception
     * @see #playerSignup(String name)
     * @see #haveMatch(int userId)
     */
    public String getOpponent(int userId) throws Exception;
}
