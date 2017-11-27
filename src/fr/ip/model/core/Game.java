package fr.ip.model.core;

import java.util.LinkedList;

public abstract class Game {

    protected LinkedList<Player> ps;
    protected Board board;

    private static State instance;

    public static class State {

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

    }

    public Game () {
        ps = new LinkedList<Player>();
    }

    public void addPlayer (Player p) {
        ps.add(p);
    }

    public void play () {
        board = new Board(ps.toArray(new Player[0]));
        Board.Cycle p = board.iterator();
        instance = new State(p);
        setup();

        while(!isEnd() && p.hasNext()) {
            Player player = p.next();
            player.listener().trigger(new Event("play"));
            player.listener().trigger(new Event("end"));
        }
    }

    protected abstract void setup ();
    public abstract boolean isEnd ();

    public static State getInstance() {
        return instance;
    }

}
