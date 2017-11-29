package fr.ip.model.core;

import fr.ip.model.util.Tuple;


public class Board {

    public static class Cycle {

        private Tuple<Player, Boolean>[] ps;
        private int index;
        int removed;

        public Cycle (Player[] ps) {
            index = ps.length - 1;
            this.ps = new Tuple[ps.length];

            for(int i = 0; i < ps.length; i++)
                this.ps[i] = new Tuple<>(ps[i], true);

            this.removed = 0;
        }

        public boolean hasNext() {
            return ps.length != 0 && removed != ps.length;
        }

        public Player next () {
            index = (index + 1) % ps.length;
            while (!ps[index].y) {
                ps[index].x.listener().trigger(new Event("pass"));
                index = (index + 1) % ps.length;
            }
            return ps[index].x;
        }

        public void remove () {
            if(!ps[index].y)
                return;
            ps[index].y = false;
            removed ++;
        }

        public void add () {
            if(ps[index].y)
                return;
            ps[index].y = true;
            removed --;
        }

        public void add (Player player) {
            int i;
            for (i = 0; i < ps.length; i++)
                if (player == ps[i].x){
                    if(ps[i].y)
                        return;
                    ps[i].y = true;
                    removed --;
                }
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
