package fr.ip.model.core;

import java.util.LinkedList;

public class Board {

    public static class Cycle {

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
            index = (index + 1) % ps.length;
            while (removed.indexOf(index) != -1) {
                try {
                    ps[index].listener().trigger(new Event("pass"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index = (index + 1) % ps.length;
            }
            return ps[index];
        }

        public void remove () {
            removed.add(index);
        }

        public void add () {
            removed.add(index);
        }

        public void add (Player player) {
            int i;
            for (i = 0; i < ps.length; i++)
                if (player == ps[i])
                    removed.remove(new Integer(i));
        }

    }

    private Cycle players;

    public Board(Player[] ps) {
        players = new Cycle(ps);
    }

    public Cycle iterator() {
        return players;
    }
}
