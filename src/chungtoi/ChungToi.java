/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

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
    private Queue<Integer> waitingQueue;
    private Map<Integer, Long> waitingTime;
    private List<Integer> timedOut;
    private final Semaphore playerSem;
    private final Semaphore waitTimeSem;
    private final Semaphore matchesSem;
    private final Semaphore timedOutSem;
    private final Semaphore waitQueueSem;

    public ChungToi() throws Exception {
        this.players = new HashMap<>();
        this.waitingTime = new HashMap<>();
        this.matches = new ArrayList<>();
        this.timedOut = new ArrayList<>();
        this.nextUserId = 1;
        this.waitingQueue = new ArrayBlockingQueue(this.MAX_PLAYERS);
        this.playerSem = new Semaphore(1, true);
        this.waitTimeSem = new Semaphore(1, true);
        this.matchesSem = new Semaphore(1, true);
        this.timedOutSem = new Semaphore(1, true);
        this.waitQueueSem = new Semaphore(1, true);
    }

    /*
1) registraJogador
Recebe: string com o nome do usuário/jogador
Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
este usuário durante uma partida), ­1 se este usuário já está  cadastrado ou ­2 se o número
máximo de jogadores tiver sido atingido
     */
    @Override
    public int playerSignup(String name) throws Exception {
        this.playerSem.acquire();
        if (this.players.values().contains(name)) {
            return -1;
        }
        if (this.players.size() == this.MAX_PLAYERS) {
            return -2;
        }
        int userId = this.nextUserId;
        this.nextUserId++;
        this.players.put(userId, name);
        this.playerSem.release();
        System.out.println(String.format("Player %s joined an has the user id '%s'", name, userId));

        this.waitQueueSem.acquire();
        this.waitTimeSem.acquire();

        this.waitingQueue.add(userId);
        this.waitingTime.put(userId, System.currentTimeMillis());
        if (this.waitingQueue.size() > 1) {
            Match createdMatch = this.createMatch(this.waitingQueue.poll(), this.waitingQueue.poll());
            this.waitingTime.remove(createdMatch.getWhitePlayerId());
            this.waitingTime.remove(createdMatch.getBlackPlayerId());
        }

        this.waitTimeSem.release();
        this.waitQueueSem.release();

        return userId;
    }

    /*2) encerraPartida
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­1 (erro), 0 (ok)
     */
    @Override
    public int endMatch(int userId) throws Exception {
        this.playerSem.acquire();

        if (this.players.containsKey(userId)) {
            this.players.remove(userId);
        }

        this.playerSem.release();

        this.matchesSem.acquire();

        for (Iterator<Match> it = matches.iterator(); it.hasNext();) {
            Match match = it.next();
            if (match.isWhitePlayer(userId) || match.isBlackPlayer(userId)) {
                match.endMatch(userId);
                if (match.canKill()) {
                    it.remove();
                }
                this.matchesSem.release();
                return 0;
            }
        }

        this.matchesSem.release();

        return -1;
    }

    /*3) temPartida
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­2 (tempo de espera esgotado), ­1 (erro), 0 (ainda não há partida), 1 (sim, há partida e o
jogador inicia jogando com as peças claras, identificadas, por exemplo, com letras de “C” para
deslocamento perpendicular ou “c” para deslocamento diagonal) ou 2 (sim, há  partida e o
jogador é o segundo a jogar, com os as peças escuras, identificadas, por exemplo, a letra “E”
para deslocamento perpendicular ou “e” para deslocamento diagonal)
     */
    @Override
    public int haveMatch(int userId) throws Exception {
        int ret = -1;
        this.matchesSem.acquire();
        for (Match match : matches) {
            if (match.isWhitePlayer(userId)) {
                ret = 1;
                break;
            } else if (match.isBlackPlayer(userId)) {
                ret = 2;
                break;
            }
        }

        this.matchesSem.release();

        if (ret == -1) {
            this.waitQueueSem.acquire();
            this.waitTimeSem.acquire();
            this.timedOutSem.acquire();

            if (this.waitingTime.containsKey(userId)) {
                Long wt = (System.currentTimeMillis() - this.waitingTime.get(userId)) / 1000;
                if (wt >= Match.PLAYER_TIMEOUT) {
                    this.waitingQueue.remove(userId);
                    this.waitingTime.remove(userId);
                    this.timedOut.add(userId);
                } else {
                    ret = 0;
                }
            }
            if (this.timedOut.contains(userId)) {
                this.players.remove(userId);
                ret = -2;//Timeout
            }
            this.timedOutSem.release();
            this.waitTimeSem.release();
            this.waitQueueSem.release();
        }
        return ret;
    }

    /*4) ehMinhaVez
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 1 (sim), 2
(é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
     */
    @Override
    public int isMyTurn(int userId) throws Exception {
        int ret = -1;
        this.waitQueueSem.acquire();

        if (this.waitingQueue.contains(userId)) {
            ret = -2;
        }

        this.waitQueueSem.release();

        if (ret == -1) {
            this.matchesSem.acquire();

            for (Match match : matches) {
                boolean isWhite = match.isWhitePlayer(userId);
                boolean isBlack = match.isBlackPlayer(userId);
                if (isWhite || isBlack) {
                    ret = match.isMyTurn(userId);
                }
            }

            this.matchesSem.release();
        }
        return ret;
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
    @Override
    public String getBoard(int userId) throws Exception {
        String ret = "";
        this.matchesSem.acquire();

        for (Match match : matches) {
            boolean isWhite = match.isWhitePlayer(userId);
            boolean isBlack = match.isBlackPlayer(userId);
            if (isWhite || isBlack) {
                ret = match.getBoard(userId);
            }
        }

        this.matchesSem.release();

        return ret;
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
    @Override
    public int placePiece(int userId, int position, int orientation) throws Exception {
        int ret = -1;

        this.waitQueueSem.acquire();
        if (this.waitingQueue.contains(userId)) {
            ret = -2;
        }
        this.waitQueueSem.release();

        if (ret == -1) {
            this.matchesSem.acquire();

            for (Match match : matches) {
                boolean isWhite = match.isWhitePlayer(userId);
                boolean isBlack = match.isBlackPlayer(userId);
                if (isWhite || isBlack) {
                    if (match.isActive()) {
                        if (match.isMyTurn(userId) == 1) {
                            ret = match.placePiece(userId, position, orientation);
                        } else {
                            ret = -3;
                        }
                    } else {
                        ret = 2;
                    }
                    break;
                }
            }

            this.matchesSem.release();
        }
        return ret;
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
    @Override
    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) throws Exception {
        int ret = 0;

        this.waitQueueSem.acquire();

        if (this.waitingQueue.contains(userId)) {
            ret = -2;
        }

        this.waitQueueSem.release();
        if (ret == 0) {
            this.matchesSem.acquire();

            for (Match match : matches) {
                boolean isWhite = match.isWhitePlayer(userId);
                boolean isBlack = match.isBlackPlayer(userId);
                if (isWhite || isBlack) {
                    if (match.isActive()) {
                        if (match.isMyTurn(userId) == 1) {
                            ret = match.movePiece(userId, currentPosition, direction, movement, newOrientation);
                        } else {
                            ret = -3;
                        }
                    } else {
                        ret = 2;
                    }
                    break;
                }
            }

            this.matchesSem.release();
        }
        return ret;
    }

    /*8) obtemOponente
Recebe: id do usuário (obtido através da chamada registraJogador)
Retorna: string vazio para erro ou string com o nome do oponente
     */
    @Override
    public String getOpponent(int userId) throws Exception {
        String ret = "";

        this.playerSem.acquire();
        this.matchesSem.acquire();

        for (Match match : matches) {
            if (match.isWhitePlayer(userId)) {
                ret = this.players.get(match.getBlackPlayerId());
            } else if (match.isBlackPlayer(userId)) {
                ret = this.players.get(match.getWhitePlayerId());
            }
            if (!ret.equals("")) {
                break;
            }
        }

        this.matchesSem.release();
        this.playerSem.release();

        return ret;
    }

    private Match createMatch(Integer whitePlayerId, Integer blackPlayerId) throws Exception {
        Match match = new Match(whitePlayerId, blackPlayerId);

        this.matchesSem.acquire();

        this.matches.add(match);

        this.matchesSem.release();

        System.out.println(String.format("Match started. Player %s against %s", this.players.get(whitePlayerId), this.players.get(blackPlayerId)));
        return match;
    }

    public int clearEndedMatches() throws Exception {
        int removed = 0;

        this.matchesSem.acquire();

        for (Iterator<Match> it = matches.iterator(); it.hasNext();) {
            Match match = it.next();
            if (match.canKill()) {
                it.remove();
                removed++;
            }
        }

        this.matchesSem.release();

        return removed;
    }

    public int checkPlayersTimeouts() throws Exception {
        int removed = 0;

        this.waitQueueSem.acquire();
        this.waitTimeSem.acquire();
        this.timedOutSem.acquire();

        for (Iterator iterator = waitingTime.keySet().iterator(); iterator.hasNext();) {
            Integer userId = (Integer) iterator.next();
            Long wt = (System.currentTimeMillis() - this.waitingTime.get(userId)) / 1000;
            if (wt >= Match.PLAYER_TIMEOUT) {
                this.waitingQueue.remove(userId);
                this.waitingTime.remove(userId);
                this.timedOut.add(userId);
                removed++;
            }
        }

        this.timedOutSem.release();
        this.waitTimeSem.release();
        this.waitQueueSem.release();

        return removed;
    }
}
