/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lasaro
 */
public class ChungToi extends UnicastRemoteObject implements CTInterface {
    private final int MAX_MATCHES = 500;
    private final int MAX_PLAYERS = MAX_MATCHES * 2;
    private Map<Integer, String> players;
    private List<Match> matches;
    private Integer nextUserId;
    
    public ChungToi() throws RemoteException{
        this.players = new HashMap<Integer,String>();
        this.matches = new ArrayList<>();
        this.nextUserId = 1;
    }
    
    /*
1) registraJogador
Recebe: string com o nome do usuário/jogador
Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
este usuário durante uma partida), ­1 se este usuário já está  cadastrado ou ­2 se o número
máximo de jogadores tiver sido atingido
*/
    public int PlayerSignup(String name) throws RemoteException{
        if(this.players.values().contains(name))
            return -1;
        if(this.players.size() == this.MAX_PLAYERS)
            return -2;
        int userId = this.nextUserId;
        this.players.put(userId, name);
        this.nextUserId++;
        return userId;
    }
/*2) encerraPartida
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­1 (erro), 0 (ok)
*/
    public int EndMatch(int userId) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*3) temPartida
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­2 (tempo de espera esgotado), ­1 (erro), 0 (ainda não há partida), 1 (sim, há partida e o
jogador inicia jogando com as peças claras, identificadas, por exemplo, com letras de “C” para
deslocamento perpendicular ou “c” para deslocamento diagonal) ou 2 (sim, há  partida e o
jogador é o segundo a jogar, com os as peças escuras, identificadas, por exemplo, a letra “E”
para deslocamento perpendicular ou “e” para deslocamento diagonal)
*/
    public int HaveMatch(int userId) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*4) ehMinhaVez
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 1 (sim), 2
(é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
*/
    public int IsMyTurn(int userId) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
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
    public String GetBoard(int userId) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*6) posicionaPeca
Recebe: id do usuário (obtido através da chamada registraJogador), posição do tabuleiro onde a
peça deve ser posicionada (de 0 até 8, inclusive) e orientação da peça (0 correspondendo à
orientação perpendicular, e 1 correspondendo à orientação diagonal).
Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
jogada e ocorra o  time­out  de 60 segundos para envio de jogadas), 1 (tudo certo), 0 (posição
inválida, por exemplo, devido a uma casa já ocupada), ­1 (parâmetros inválidos), ­2 (partida não
iniciada: ainda não há dois jogadores registrados na partida), ­3 (não é a vez do jogador).
*/
    public int PlacePiece(int userId, int position, int orientation) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*7) movePeca
Recebe: id do usuário (obtido através da chamada registraJogador), posição do tabuleiro onde se
encontra a peça que se deseja mover (de 0 até 8, inclusive), sentido do deslocamento (0 a 8,
inclusive), número de casas deslocadas (0, 1 ou 2) e orientação da peça depois da jogada (0
correspondendo a orientação perpendicular, e 1 correspondendo à orientação diagonal). Para o
sentido do deslocamento deve­se usar a seguinte convenção: 0 = diagonal esquerda­superior; 1 =
para cima; 2 = diagonal direita­superior; 3 = esquerda; 4 = sem movimento; 5 = direita; 6 =
diagonal esquerda­inferior; 7 = para baixo; 8 = diagonal direita­inferior.
Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
jogada e ocorra o time­out de 60 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
inválido, por exemplo, em um sentido e deslocamento que resulta em uma posição ocupada ou
fora   do   tabuleiro),   ­1   (parâmetros   inválidos),   ­2   (partida   não   iniciada:   ainda   não   há   dois
jogadores registrados na partida), ­3 (não é a vez do jogador).
*/
    public int MovePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*8) obtemOponente
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: string vazio para erro ou string com o nome do oponente
*/
    public String GetOpponent(int UserId) throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}