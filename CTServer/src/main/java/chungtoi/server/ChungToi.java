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
    private Map<Integer, Player> players;
    private Integer nextUserId;
    private Queue<Integer> waitingQueue;
    private final Semaphore playerSem;
    private final Semaphore waitQueueSem;
    private SimpleDateFormat sdf;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChungToi() {
        this.players = new HashMap<>();
        this.nextUserId = 1;
        this.waitingQueue = new ArrayBlockingQueue(this.MAX_PLAYERS);
        this.playerSem = new Semaphore(1, true);
        this.waitQueueSem = new Semaphore(1, true);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ChungToi chungToi = this;
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    int removedPlayers = chungToi.checkPlayersTimeouts();
                    if (removedPlayers > 0) {
                        chungToi.log(String.format("Removed %s players", removedPlayers));
                    }
                    chungToi.log(chungToi.printStatus());
                } catch (Exception e) {
                    chungToi.log(e);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
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
    @WebMethod(operationName = "playerSignup")
	public int playerSignup(
		@WebParam(name = "name") String name) {
        int userId = -3;
        try{
            this.playerSem.acquire();
            if (this.players.values().stream().filter(p -> p.getName().equals(name)).count() > 0) {
                return -1;
            }
            if (this.players.size() == this.MAX_PLAYERS) {
                return -2;
            }
            userId = this.nextUserId;
            this.nextUserId++;
            Player player = new Player(userId, name);
            this.players.put(userId, player);
            this.log(String.format("Player %s joined an has the user id '%s'", name, userId));

            this.waitQueueSem.acquire();
            this.waitingQueue.add(userId);
            if (this.waitingQueue.size() > 1) {
                Integer whitePlayerId = this.waitingQueue.poll();
                Integer blackPlayerId = this.waitingQueue.poll();
                Match match = new Match(whitePlayerId, blackPlayerId);
                this.players.get(whitePlayerId).setMatch(match);
                this.players.get(blackPlayerId).setMatch(match);
                this.log(String.format("Match started. Player %s against %s", this.players.get(whitePlayerId).getName(), this.players.get(blackPlayerId).getName()));
            }
            this.waitQueueSem.release();
            this.playerSem.release();
        }
        catch (Exception e){
            this.log(e);
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
    @WebMethod(operationName = "endMatch")
	public int endMatch(
		@WebParam(name = "userId") int userId) {
        int ret = -1;
        try{
            String p = this.players.get(userId).toString();
            this.log(p + " logging out...");
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                this.players.get(userId).getMatch().endMatch(userId);
                this.players.remove(userId);
                ret = 0;
            }
            this.playerSem.release();
            this.log(p + " logged out");
        }
        catch(Exception e){
            this.log(e);
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
    @WebMethod(operationName = "haveMatch")
	public int haveMatch(
		@WebParam(name = "userId") int userId) {
        int ret = -1;
        try{
            Player player = null;
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                player = this.players.get(userId);
                if (player.isTimedOut()) {
                    this.waitQueueSem.acquire();
                    this.waitingQueue.remove(userId);
                    this.waitQueueSem.release();
                    this.players.remove(userId);
                    ret = -2;
                    player = null;
                }
            }
            this.playerSem.release();

            if (player != null) {
                if (player.getMatch() == null) {
                    ret = 0;
                } else if (player.getMatch().isWhitePlayer(userId)) {
                    ret = 1;
                } else if (player.getMatch().isBlackPlayer(userId)) {
                    ret = 2;
                }
            }
        }
        catch(Exception e){
            this.log(e);
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
    @WebMethod(operationName = "isMyTurn")
	public int isMyTurn(
		@WebParam(name = "userId") int userId) {
        int ret = -1;
        try{
            this.waitQueueSem.acquire();

            if (this.waitingQueue.contains(userId)) {
                ret = -2;
            }

            this.waitQueueSem.release();

            if (ret == -1) {
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                ret = this.players.get(userId).isMyTurn();
            }
            this.playerSem.release();
        }
        }
        catch (Exception e) {
            this.log(e);
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
    @WebMethod(operationName = "getBoard")
	public String getBoard(
		@WebParam(name = "userId") int userId) {
        String ret = "";
        try{
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                ret = this.players.get(userId).getBoard();
            }
            this.playerSem.release();
        }
        catch (Exception e) {
            this.log(e);
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
     * -1 invalid parameters<br>
     * -2 match not started yet<br>
     * -3 not player's turn<br>
     * @see #playerSignup(String name)
     * @see #movePiece(int userId, int currentPosition, int direction, int
     * movement, int newOrientation)
     */
    @WebMethod(operationName = "placePiece")
	public int placePiece(
		@WebParam(name = "userId") int userId,
		@WebParam(name = "position") int position,
		@WebParam(name = "orientation") int orientation) {
        int ret = -1;
        try{
            this.waitQueueSem.acquire();
            if (this.waitingQueue.contains(userId)) {
                ret = -2;
            }
            this.waitQueueSem.release();

            if (ret == -1) {
                this.playerSem.acquire();
                if (this.players.containsKey(userId)) {
                    ret = this.players.get(userId).placePiece(position, orientation);
                }
                this.playerSem.release();
            }
        }
        catch (Exception e) {
            this.log(e);
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
     * -1 invalid parameters<br>
     * -2 match not started yet<br>
     * -3 not player's turn
     * @see #playerSignup(String name)
     * @see #placePiece(int userId, int position, int orientation)
     */
    @WebMethod(operationName = "movePiece")
	public int movePiece(
		@WebParam(name = "userId") int userId,
		@WebParam(name = "currentPosition") int currentPosition,
		@WebParam(name = "direction") int direction,
		@WebParam(name = "movement") int movement,
		@WebParam(name = "newOrientation") int newOrientation) {
        int ret = 0;
        try {
            this.waitQueueSem.acquire();
            if (this.waitingQueue.contains(userId)) {
                ret = -2;
            }
            this.waitQueueSem.release();

            if (ret == 0) {
                this.playerSem.acquire();
                if (this.players.containsKey(userId)) {
                    Player player = this.players.get(userId);
                    if (player.getMatch().isActive()) {
                        ret = player.movePiece(currentPosition, direction, movement, newOrientation);
                    } else {
                        ret = 2;
                    }
                }
                this.playerSem.release();
            }
        }
        catch (Exception e) {
            this.log(e);
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
    @WebMethod(operationName = "getOpponent")
	public String getOpponent(
		@WebParam(name = "name") int userId) {
        String ret = "";
        try{
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                Player player = this.players.get(userId);
                if (player.isWhitePlayer()) {
                    ret = this.players.get(player.getMatch().getBlackPlayerId()).getName();
                } else if (player.isBlackPlayer(userId)) {
                    ret = this.players.get(player.getMatch().getWhitePlayerId()).getName();
                }
            }
            this.playerSem.release();
        }
        catch (Exception e) {
            this.log(e);
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
    private int checkPlayersTimeouts() {
        int removed = 0;
        try{
            this.log("Checking timeouts..");
            this.playerSem.acquire();
            this.waitQueueSem.acquire();
            for (Iterator it = this.waitingQueue.stream().iterator(); it.hasNext();) {
                Player player = this.players.get((Integer) it.next());
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
                    if ((wt >= 10)) {
                        toRemove.add(userId);
                    }
                }
            }
            for (Integer userId : toRemove) {
                this.log("Removing " + this.players.get(userId) + " by match ended.");
                this.players.remove(userId);
                removed++;
            }
            this.waitQueueSem.release();
            this.playerSem.release();
            this.log("ended checking timeouts");
        }
        catch (Exception e) {
            this.log(e);
        }
        return removed;
    }

    private String printStatus() {
        String ret = "";
        try{
            StringBuilder sb = new StringBuilder();
            long currMilliseconds = System.currentTimeMillis();
            Date resultdate = new Date(currMilliseconds);

            this.playerSem.acquire();
            this.waitQueueSem.acquire();
            sb.append("Server time: " + sdf.format(resultdate) + "\n");
            sb.append("Players online: " + this.players.size() + "\n");
            sb.append("Player wainting a match: " + this.waitingQueue.size());
            this.waitQueueSem.release();
            this.playerSem.release();
            ret = sb.toString();
        }
        catch (Exception e) {
            this.log(e);
        }
        return ret;
    }

    private void log(String message){
        System.out.println(String.format("[INFO] %s", message));
    }

    private void log(Exception ex){
        System.out.println(String.format("[ERROR] %s\n%s", ex.getMessage(), ex.getStackTrace().toString()));
    }
}
