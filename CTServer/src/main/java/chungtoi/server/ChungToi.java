/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.server;

import chungtoi.common.CTInterface;
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
    private Map<Integer, Player> players;
    private Integer nextUserId;
    private Queue<Integer> waitingQueue;
    private final Semaphore playerSem;
    private final Semaphore waitQueueSem;

    public ChungToi() throws Exception {
        this.players = new HashMap<>();
        this.nextUserId = 1;
        this.waitingQueue = new ArrayBlockingQueue(this.MAX_PLAYERS);
        this.playerSem = new Semaphore(1, true);
        this.waitQueueSem = new Semaphore(1, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int playerSignup(String name) throws Exception {
        this.playerSem.acquire();
        if (this.players.values().stream().filter(p -> p.getName().equals(name)).count() > 0) {
            return -1;
        }
        if (this.players.size() == this.MAX_PLAYERS) {
            return -2;
        }
        int userId = this.nextUserId;
        this.nextUserId++;
        Player player = new Player(userId, name);
        this.players.put(userId, player);
        System.out.println(String.format("Player %s joined an has the user id '%s'", name, userId));

        this.waitQueueSem.acquire();
        this.waitingQueue.add(userId);
        if (this.waitingQueue.size() > 1) {
            Integer whitePlayerId = this.waitingQueue.poll();
            Integer blackPlayerId = this.waitingQueue.poll();
            Match match = new Match(whitePlayerId, blackPlayerId);
            this.players.get(whitePlayerId).setMatch(match);
            this.players.get(blackPlayerId).setMatch(match);
            System.out.println(String.format("Match started. Player %s against %s", this.players.get(whitePlayerId).getName(), this.players.get(blackPlayerId).getName()));
        }
        this.waitQueueSem.release();
        this.playerSem.release();

        return userId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int endMatch(int userId) throws Exception {
        int ret = -1;
        this.playerSem.acquire();
        if (this.players.containsKey(userId)) {
            this.players.get(userId).getMatch().endMatch(userId);
            this.players.remove(userId);
        }
        this.playerSem.release();
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int haveMatch(int userId) throws Exception {
        int ret = -1;
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
        return ret;
    }

    /**
     * {@inheritDoc}
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
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                ret = this.players.get(userId).isMyTurn();
            }
            this.playerSem.release();
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBoard(int userId) throws Exception {
        String ret = "";
        this.playerSem.acquire();
        if (this.players.containsKey(userId)) {
            ret = this.players.get(userId).getBoard();
        }
        this.playerSem.release();

        return ret;
    }

    /**
     * {@inheritDoc}
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
            this.playerSem.acquire();
            if (this.players.containsKey(userId)) {
                ret = this.players.get(userId).placePiece(position, orientation);
            }
            this.playerSem.release();
        }
        return ret;
    }

    /**
     * {@inheritDoc}
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
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpponent(int userId) throws Exception {
        String ret = "";
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
        return ret;
    }

    public int checkPlayersTimeouts() throws Exception {
        int removed = 0;

        this.playerSem.acquire();
        this.waitQueueSem.acquire();
        for (Iterator it = this.waitingQueue.stream().iterator(); it.hasNext();) {
            Player player = this.players.get((Integer) it.next());
            if (player.isTimedOut() || (player.getMatch() != null && !player.getMatch().isActive())) {
                this.players.remove(player.getUserId());
                removed++;
            }
        }
        this.waitQueueSem.release();
        this.playerSem.release();
        return removed;
    }

    public String printStatus() throws InterruptedException {
        StringBuilder sb = new StringBuilder();

        this.playerSem.acquire();
        this.waitQueueSem.acquire();
        sb.append("Players online: " + this.players.size() + "\n");
        sb.append("Player wainting a match: " + this.waitingQueue.size());
        this.waitQueueSem.release();
        this.playerSem.release();
        return sb.toString();
    }
}
