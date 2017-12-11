package fr.ip.model.core;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Game implements Iterable<Player> {

    protected LinkedList<Player> ps;
    protected Board board;

    private static State instance;

    public static class State {

        private Player currentPlayer;

        private Board.Cycle p;

        public State (Board.Cycle p) {
            this.p = p;
        }

        public void removePlayer () {
            p.remove();
        }

        public void add () {
            p.add();
        }

        public void add(Player player) {
            p.add(player);
        }

        public void playAgain () {
            p.previous();
        }

        public Player getCurrentPlayer () {
            return currentPlayer;
        }

    }

    public Game () {
        ps = new LinkedList<Player>();
        Cell.flush();
    }

    public void addPlayer (Player p) {
        ps.add(p);
    }

    public void start () {
        board = new Board(ps.toArray(new Player[0]));
        Board.Cycle p = board.iterator();
        instance = new State(p);
    }

    public void playTurn () {
        Player player = instance.p.next();
        instance.currentPlayer = player;
        player.listener().trigger(new Event("play"));
        player.listener().trigger(new Event("end"));
    }

    public void play () {
        setup();
        start();
        while(!isEnd() && instance.p.hasNext())
            playTurn();
    }

    public abstract void setup ();
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
