package chungtoi.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 *
 * @author lasaro
 */
@WebService(serviceName = "ChungToiWS")
public class ChungToi {

    private final int MAX_MATCHES = 500;
    private final int MAX_PLAYERS = MAX_MATCHES * 2;
    private Map<String, Integer> preSignupMap;
    private Map<Integer, Integer> preSignupMatchMap;
    private Map<Integer, Player> players;
    private Integer nextUserId;
    private List<Integer> waitingList;
    private final Semaphore playerSem;
    private final Semaphore waitQueueSem;
    private SimpleDateFormat sdf;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChungToi() {
        this.preSignupMap = new HashMap<>();
        this.preSignupMatchMap = new HashMap<>();
        this.players = new HashMap<>();
        this.nextUserId = 1;
        this.waitingList = new ArrayList(this.MAX_PLAYERS);
        this.playerSem = new Semaphore(1, true);
        this.waitQueueSem = new Semaphore(1, true);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        ChungToiCleaner cleaner = new ChungToiCleaner(this);
        exec.scheduleAtFixedRate(cleaner, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * (usada para viabilizar o teste): informa ao servidor o nome de um
     * jogador (o primeiro da dupla), o identificador que o servidor deverá utilizar para este primeiro
     * jogador, o nome de outro jogador (o segundo da dupla) e o respectivo identificador que o servidor
     * deverá utilizar para este segundo jogador. Esta operação retorna sempre 0 e não haverá nenhuma
     * inconsistência   nas   entradas   referente   às   operações   de  
     * pré­registro   (ou   seja,   elas   serão   sempre consistentes). This
     * method is used to allow a better testing approach. It receives the names
     * of the players and also their names
     *
     * @param playerOneName the name of the first player
     * @param playerOneId the desired id for the first player
     * @param playerTwoName the name of the second player
     * @param playerTwoId the desired id for the second player
     * @return MUST be zero, otherwise an error happened
     */
    @WebMethod(operationName = "preRegistro")
    public int preSignup(
            @WebParam(name = "playerOneName") String playerOneName,
            @WebParam(name = "playerOneId") int playerOneId,
            @WebParam(name = "playerTwoName") String playerTwoName,
            @WebParam(name = "playerTwoId") int playerTwoId) {
        int ret = -1;
        try {
            if (!this.preSignupMap.containsKey(playerOneName)) {
                this.preSignupMap.put(playerOneName, 0);
            }
            if (!this.preSignupMap.containsKey(playerTwoName)) {
                this.preSignupMap.put(playerTwoName, 0);
            }
            this.preSignupMap.put(playerOneName, playerOneId);
            this.preSignupMap.put(playerTwoName, playerTwoId);
            this.preSignupMatchMap.put(playerOneId, playerTwoId);
            ret = 0;
        } catch (Exception e) {
            this.log(e);
        }
        return ret;
    }

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
    @WebMethod(operationName = "registraJogador")
    public int playerSignup(
            @WebParam(name = "name") String name) {
        int userId = -3;
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            while(!havePlayerPermit)
                havePlayerPermit = this.playerSem.tryAcquire();
            if (this.players.values().stream().filter(p -> p.getName().equals(name)).count() > 0)
                return -1;
            if (this.players.size() == this.MAX_PLAYERS)
                return -2;
            if (this.preSignupMap.containsKey(name)) {
                userId = this.preSignupMap.get(name);
            } else {
                userId = this.nextUserId;
                this.nextUserId++;
            }
            Player player = new Player(userId, name);
            this.players.put(userId, player);
            this.log(String.format("Player %s joined an has the user id '%s'", name, userId));

            while(!haveWaitQueuePermit)
                haveWaitQueuePermit=this.waitQueueSem.tryAcquire();
            this.waitingList.add(userId);
            if (this.waitingList.size() > 1) {
                Integer whitePlayerId = null, blackPlayerId = null;
                for (Map.Entry<Integer, Integer> preMatch : preSignupMatchMap.entrySet()) {
                    Integer whiteCandidate = preMatch.getKey();
                    Integer blackCandidate = preMatch.getValue();
                    if (this.waitingList.contains(whiteCandidate) && this.waitingList.contains(blackCandidate)) {
                        whitePlayerId = whiteCandidate;
                        blackPlayerId = blackCandidate;
                        break;
                    }
                }
                if (whitePlayerId != null && blackPlayerId != null) {
                    this.waitingList.remove(whitePlayerId);
                    this.waitingList.remove(blackPlayerId);
                    Player white = this.players.get(whitePlayerId);
                    Player black = this.players.get(blackPlayerId);
                    Match match = new Match(white.getUserId(), white.getName(), black.getUserId(), black.getName());
                    white.setMatch(match);
                    black.setMatch(match);
                    this.log(String.format("Match started. Player %s against %s", white.getName(), black.getName()));
                }
            }
            this.waitQueueSem.release();
            haveWaitQueuePermit = false;
            this.playerSem.release();
            havePlayerPermit = false;
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return userId;
    }

    /**
     * The player gives the match for ended. A player should give its match for
     * ended when it either loses or wins the match. This allow the server to
     * know that both players can be signed out
     *
     * @param userId the id of the player
     * @return 1 to error and 0 to success
     * @see #playerSignup(String name)
     */
    @WebMethod(operationName = "encerraPartida")
    public int endMatch(
            @WebParam(name = "userId") int userId) {
        int ret = -1;
        boolean havePlayerPermit = false;
        try {
            String p = this.players.get(userId).toString();
            this.log(p + " logging out...");
            while(!havePlayerPermit)
                havePlayerPermit=this.playerSem.tryAcquire();
            if (this.players.containsKey(userId)) {
                this.players.get(userId).getMatch().endMatch(userId);
                this.players.remove(userId);
                ret = 0;
            }
            this.playerSem.release();
            havePlayerPermit = false;
            this.log(p + " logged out");
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();

        }
        return ret;
    }

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
    @WebMethod(operationName = "temPartida")
    public int haveMatch(
            @WebParam(name = "userId") int userId) {
        int ret = -1;
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            Player player = null;
            while (!havePlayerPermit)
                havePlayerPermit = this.playerSem.tryAcquire();
            if (this.players.containsKey(userId)) {
                player = this.players.get(userId);
                if (player.isTimedOut()) {
                    while (!haveWaitQueuePermit)
                        haveWaitQueuePermit = this.waitQueueSem.tryAcquire();
                    if (this.waitingList.contains(userId))
                        this.waitingList.remove(userId);
                    this.waitQueueSem.release();
                    haveWaitQueuePermit=false;
                    this.players.remove(userId);
                    ret = -2;
                    player = null;
                }
            }

            if (player != null) {
                if (player.getMatch() == null)
                    ret = 0;
                else if (player.getMatch().isWhitePlayer(userId))
                    ret = 1;
                else if (player.getMatch().isBlackPlayer(userId))
                    ret = 2;
            }

            this.playerSem.release();
            havePlayerPermit =false;
        } catch (Exception e) {
            this.log(e);
            if (havePlayerPermit)
                this.playerSem.release();
            if (haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return ret;
    }

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
    @WebMethod(operationName = "ehMinhaVez")
    public int isMyTurn(
            @WebParam(name = "userId") int userId) {
        int ret = -1;
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            while (!haveWaitQueuePermit)
                haveWaitQueuePermit = this.waitQueueSem.tryAcquire();
            if (this.waitingList.contains(userId))
                ret = -2;

            this.waitQueueSem.release();
            haveWaitQueuePermit=false;

            if (ret == -1) {
                while (!havePlayerPermit)
                    havePlayerPermit = this.playerSem.tryAcquire();
                if (this.players.containsKey(userId))
                    ret = this.players.get(userId).isMyTurn();
                this.playerSem.release();
                havePlayerPermit=false;
            }
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return ret;
    }

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
    @WebMethod(operationName = "obtemTabuleiro")
    public String getBoard(
            @WebParam(name = "userId") int userId) {
        String ret = "";
        boolean havePlayerPermit = false;
        try {
            while(!havePlayerPermit)
                havePlayerPermit=this.playerSem.tryAcquire();
            if (this.players.containsKey(userId))
                ret = this.players.get(userId).getBoard();
            this.playerSem.release();
            havePlayerPermit=false;
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
        }
        return ret;
    }

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
     * -1 player not found<br>
     * -2 match not started yet<br>
     * -3 invalid position and/or orientation parameters<br>
     * -4 not player's turn<br>
     * -5 placement phase already ended<br>
     * @see #playerSignup(String name)
     * @see #movePiece(int userId, int currentPosition, int direction, int
     * movement, int newOrientation)
     */
    @WebMethod(operationName = "posicionaPeca")
    public int placePiece(
            @WebParam(name = "userId") int userId,
            @WebParam(name = "position") int position,
            @WebParam(name = "orientation") int orientation) {
        int ret = -3; //The case where it's an invalid position and/or orientation almost never happen
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            while(!haveWaitQueuePermit)
                haveWaitQueuePermit=this.waitQueueSem.tryAcquire();
            if (this.waitingList.contains(userId))
                ret = -2;
            this.waitQueueSem.release();
            haveWaitQueuePermit=false;

            if (ret == -3) {
                while(!havePlayerPermit)
                    havePlayerPermit=this.playerSem.tryAcquire();
                if (this.players.containsKey(userId))
                    ret = this.players.get(userId).placePiece(position, orientation);
                else
                    ret = -1;
                this.playerSem.release();
                havePlayerPermit=false;
            }
        } catch (Exception e) {
            this.log(e);
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return ret;
    }

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
     * -1 player not found<br>
     * -2 match not started yet<br>
     * -3 invalid position and/or orientation parameters<br>
     * -4 not player's turn<br>
     * -5 not in the move phase yet<br>
     * @see #playerSignup(String name)
     * @see #placePiece(int userId, int position, int orientation)
     */
    @WebMethod(operationName = "movePeca")
    public int movePiece(
            @WebParam(name = "userId") int userId,
            @WebParam(name = "currentPosition") int currentPosition,
            @WebParam(name = "direction") int direction,
            @WebParam(name = "movement") int movement,
            @WebParam(name = "newOrientation") int newOrientation) {
        int ret = 0;
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            while(!haveWaitQueuePermit)
                haveWaitQueuePermit= this.waitQueueSem.tryAcquire();
            if (this.waitingList.contains(userId))
                ret = -2;
            this.waitQueueSem.release();
            haveWaitQueuePermit = false;

            if (ret == 0) {
                while(!havePlayerPermit)
                    havePlayerPermit=this.playerSem.tryAcquire();
                if (this.players.containsKey(userId)) {
                    Player player = this.players.get(userId);
                    if (player.getMatch().isActive())
                        ret = player.movePiece(currentPosition, direction, movement, newOrientation);
                    else
                        ret = 2;
                } else {
                    ret = -1;
                }
                this.playerSem.release();
                havePlayerPermit = false;
            }
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return ret;
    }

    /**
     * Used to know the name of the opponent of the player
     *
     * @param userId the id of the player
     * @return an empty string in case of error OR the name of the opponent
     * @
     * @see #playerSignup(String name)
     * @see #haveMatch(int userId)
     */
    @WebMethod(operationName = "obtemOponente")
    public String getOpponent(
            @WebParam(name = "name") int userId) {
        String ret = "";
        boolean havePlayerPermit = false;
        try {
            while(!havePlayerPermit)
                havePlayerPermit= this.playerSem.tryAcquire();
            if (this.players.containsKey(userId)) {
                Player player = this.players.get(userId);
                if (player.isWhitePlayer())
                    ret = player.getMatch().getBlackPlayerName();
                else if (player.isBlackPlayer(userId))
                    ret = player.getMatch().getWhitePlayerName();
            }
            this.playerSem.release();
            havePlayerPermit = false;
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
    public int checkPlayersTimeouts() {
        int removed = 0;
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            this.log("Checking timeouts..");
            while(!havePlayerPermit)
                havePlayerPermit = this.playerSem.tryAcquire();
            while(!haveWaitQueuePermit)
                haveWaitQueuePermit = this.waitQueueSem.tryAcquire();
            for (Integer id : this.waitingList) {
                Player player = this.players.get(id);
                this.log(player.toString());
                if (player.isTimedOut() || (player.getMatch() != null && !player.getMatch().isActive())) {
                    this.log("Removing " + player + " by timeout.");
                    this.players.remove(player.getUserId());
                    removed++;
                }
            }
            List<Integer> toRemove = new ArrayList<>();
            for (Map.Entry<Integer, Player> entry : players.entrySet()) {
                Integer userId = entry.getKey();
                Player player = entry.getValue();
                if (player.getMatch() != null && !player.getMatch().isActive()) {
                    Long wt = (System.currentTimeMillis() - player.getMatch().getTimeSinceEnded()) / 1000;
                    //wait 10 seconds before forcing the player out
                    if (wt >= 10)
                        toRemove.add(userId);
                }
            }
            for (Integer userId : toRemove) {
                this.log("Removing " + this.players.get(userId) + " by match ended.");
                this.players.remove(userId);
                removed++;
            }

            this.waitQueueSem.release();
            haveWaitQueuePermit = false;
            this.playerSem.release();
            havePlayerPermit = false;

            this.log("ended checking timeouts");
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return removed;
    }

    public String printStatus() {
        String ret = "";
        boolean havePlayerPermit = false;
        boolean haveWaitQueuePermit = false;
        try {
            StringBuilder sb = new StringBuilder();
            long currMilliseconds = System.currentTimeMillis();
            Date resultdate = new Date(currMilliseconds);

            while(!havePlayerPermit)
                havePlayerPermit = this.playerSem.tryAcquire();
            while(!haveWaitQueuePermit)
                haveWaitQueuePermit = this.waitQueueSem.tryAcquire();
            sb.append("Server time: " + sdf.format(resultdate) + "\n");
            sb.append("Players online: " + this.players.size() + "\n");
            sb.append("Player wainting a match: " + this.waitingList.size());

            this.waitQueueSem.release();
            haveWaitQueuePermit = false;
            this.playerSem.release();
            havePlayerPermit = false;

            ret = sb.toString();
        } catch (Exception e) {
            this.log(e);
            if(havePlayerPermit)
                this.playerSem.release();
            if(haveWaitQueuePermit)
                this.waitQueueSem.release();
        }
        return ret;
    }

    public void log(String message) {
        System.out.println(String.format("[INFO] %s", message));
    }

    public void log(Exception ex) {
        System.out.println(String.format("[ERROR] %s\n%s", ex.getMessage(), Arrays.toString(ex.getStackTrace()).replace("), ", "),\n")));
    }
}
