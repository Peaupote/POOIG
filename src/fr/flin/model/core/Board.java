package fr.flin.model.core;

import java.util.Iterator;
import java.util.LinkedList;

public class Board implements Iterable<Player> {

    private static class Cycle implements Iterator<Player> {

        private Player[] ps;
        private int index;
        private LinkedList<Integer> removed;

        public Cycle (Player[] ps) {
            index = ps.length - 1;
            this.ps = ps;
            this.removed = new LinkedList<Integer>();
        }

        public boolean hasNext() {
            return ps.length != 0 && removed.size() != ps.length;
        }

        public Player next () {
            while (removed.indexOf(index) != -1) {
                try {
                    ps[index].listener().trigger(new Event("pass"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index++;
            }
            index = (index + 1) % ps.length;
            return ps[index];
        }

        public void remove () {
            removed.add(index - 1);
        }

    }

    private Cycle players;

    public Board(Player[] ps) {
        players = new Cycle(ps);
    }

    @Override
    public Iterator<Player> iterator() {
        return players;
    }
}
