package fr.ip.model.core;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Abstract class representing the game
 */
public abstract class Game implements Iterable<Player> {

    /**
     * List of all registered players
     */
    protected LinkedList<Player> ps;

    /**
     * Cycle of player in-game
     */
    protected Board board;

    /**
     * Static instance representing the game state
     */
    private static State instance;

    /**
     * Default number of cell in game
     */
    public static final int LENGTH = 10;

    /**
     * Represent game state
     * Allow you to act on the game state from anywhere "safely"
     */
    public static class State {

        /**
         * The last player to play
         */
        private Player currentPlayer;

        /**
         * The cycle of players
         */
        private Board.Cycle p;

        public State (Board.Cycle p) {
            this.p = p;
        }

        /**
         * The current player can not play anymore
         */
        public void removePlayer () {
            p.remove();
        }

        /**
         * The current play can now play
         */
        public void add () {
            p.add();
        }

        /**
         * The given player can now play
         * @param player
         */
        public void add(Player player) {
            p.add(player);
        }

        /**
         * The current player play again
         */
        public void playAgain () {
            p.previous();
        }

        public Player getCurrentPlayer () {
            return currentPlayer;
        }

    }

    /**
     * Create a game, with no cells and no players
     */
    public Game () {
        ps = new LinkedList<>();
        Cell.flush();
    }

    /**
     * Register the given player
     * @param p player to add
     */
    public void addPlayer (Player p) {
        ps.add(p);
    }

    /**
     * Create game state and board instances
     */
    public void start () {
        board = new Board(ps.toArray(new Player[0]));
        Board.Cycle p = board.iterator();
        instance = new State(p);
    }

    /**
     * Trigger play and end on the current player
     */
    public void playTurn () {
        Player player = instance.p.next();
        instance.currentPlayer = player;
        player.listener().trigger(new Event("play"));
        player.listener().trigger(new Event("end"));
    }

    /**
     * Function calling all needed functions to play
     */
    public void play () {
        setup();
        start();
        while(!isEnd() && instance.p.hasNext())
            playTurn();
    }

    /**
     * Define the board state (cells)
     */
    public abstract void setup ();

    /**
     * Define either if the game is end or not
     * @return true if the game has end
     */
    public abstract boolean isEnd ();

    @Override
    public Iterator<Player> iterator() {
        return ps.iterator();
    }

    public int size() {
        return ps.size();
    }

    public static State getInstance() {
        return instance;
    }

}
