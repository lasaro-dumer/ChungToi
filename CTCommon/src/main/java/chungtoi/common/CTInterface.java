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
     * in case it's already signed up OR -2 if the maxium number of players
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
     * @see playerSignup(String name)
     */
    public int endMatch(int userId) throws Exception;

    /**
     * Allows the client to know when a player has a match and which piece it
     * will be playing with. Will also be usefull to know if the waiting time
     * was exceeded
     *
     * @param userId the id of the player
     * @return -2 wait time exceeded -1 error 0 no match yet 1 have match and
     * player plays as white pieces (C and c) 2 have match and player plays as
     * black pieces (E and e)
     * @see playerSignup(String name)
     */
    public int haveMatch(int userId) throws Exception;

    /**
     * Allows the client to know when a player has a match and which piece it
     * will be playing with. Will also be usefull to know if the waiting time
     * was exceeded
     *
     * @param userId the id of the player
     * @return -2 wait time exceeded -1 error 0 no match yet 1 have match and
     * player plays as white pieces (C and c) 2 have match and player plays as
     * black pieces (E and e)
     * @see playerSignup(String name)
     */
    public int isMyTurn(int userId) throws Exception;

    /*5) obtemTabuleiro
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: string vazio em caso de erro ou string representando o tabuleiro de jogo
O tabuleiro pode, por exemplo, ser representado por 9 caracteres indicando respectivamente o
estado de cada casa (de 0 até 8) do tabuleiro: 'C' (peça clara no sentido perpendicular), 'c' (peça
clara no sentido diagonal), 'E' (peça escura no sentido perpendicular), 'e' (peça escura no sentido
diagonal), '.' (casa não ocupada). Por exemplo, para a Figura 2g, a representação do tabuleiro
corresponderia ao seguinte  string  “C.E.e.CEc”, que, em uma interface de texto poderia ser
mostrado como:
C|.|E
­+­+­
.|e|.
­+­+­
C|E|c
     */
    /**
     *
     * @param userId the id of the player
     * @return an empty String in case of error or the board
     * @see playerSignup(String name)
     */
    public String getBoard(int userId) throws Exception;

    public int placePiece(int userId, int position, int orientation) throws Exception;

    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws Exception;

    public String getOpponent(int userId) throws Exception;
}
