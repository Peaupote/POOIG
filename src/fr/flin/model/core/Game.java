package fr.flin.model.core;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Game {

    protected LinkedList<Player> ps;
    protected Board board;

    private static State instance;

    public static class State {

        private Iterator<Player> p;

        public State (Iterator<Player> p) {
            this.p = p;
        }

        public void removePlayer () {
            this.p.remove();
        }

    }

    public Game () {
        ps = new LinkedList<Player>();
    }

    public void addPlayer (Player p) {
        ps.add(p);
    }

    public void play () {
        board = new Board(ps.toArray(new Player[0]));
        Iterator<Player> p = board.iterator();
        instance = new State(p);
        setup();

        while(!isEnd() && p.hasNext())
            try {
                p.next().listener().trigger(new Event("play"));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    protected abstract void setup ();
    public abstract boolean isEnd ();

    public static State getInstance() {
        return instance;
    }

}
