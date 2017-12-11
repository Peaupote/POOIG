package fr.ip.model.core;

import fr.ip.model.util.Facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

public abstract class Cell {

    public final int id;
    private static ArrayList<Cell> cells = new ArrayList<Cell>();
    private Listener listener;

    public class SinglePawnCell {

        private Pawn pawn;

        public SinglePawnCell(boolean forward) {
            pawn = null;
            listener.add("enter", (Event.CellEvent event) -> {
                if (pawn != null) {
                    event.getPawn().goToCell(next(1, forward));
                    event.stopPropagation();
                } else pawn = event.getPawn();
            });

            listener.add("leave", (Event.CellEvent event) -> {
                if (pawn == event.getPawn()) pawn = null;
            });
        }

    }

    protected class TrapCell {

        private Player player;

        public TrapCell() {
            player = null;
            Cell.this.listener.add("enter", (Event.CellEvent event) -> {
                if (player != null)
                    Game.getInstance().add(player);
                player = event.getPawn().getPlayer();
                Game.getInstance().removePlayer();
            });
        }
    }

    public class JumpCell {

        public JumpCell (int target) {
            Cell.this.listener.add("enter", (Event.CellEvent event) -> {
                Facade.show("JUMP TO " + target);
                event.getPawn().goToCell(Cell.get(target));
                event.stopPropagation();
            });
        }

    }

    protected class CounterCell {

        private HashMap<Player, Integer> map;

        public CounterCell (int i) {
            map = new HashMap<>();
            Cell.this.listener.add("enter", (Event.CellEvent event) -> {
                map.put(event.getPawn().getPlayer(), i);
                Game.getInstance().removePlayer();
            });

            Cell.this.listener.add("stay", (Event.CellEvent event) -> {
                Player player = event.getPawn().getPlayer();
                map.compute(player, (p, j) -> j - 1);
                if (map.get(player) == 0) {
                    map.remove(player);
                    Game.getInstance().add(player);
                }
            });
        }

    }

    protected class QuestionCell {

        public QuestionCell (Predicate<String> isCorrect, ActionEvent<Event.CellEvent> success) {
            this (isCorrect, success, (Event.CellEvent event) -> {});
        }

        public QuestionCell (Predicate<String> isCorrect, ActionEvent<Event.CellEvent> success, ActionEvent<Event.CellEvent> fail) {
            Cell.this.listener().add("enter", (Event.CellEvent event) -> {
                String answer = Facade.read();
                if (isCorrect.test(answer)) success.run(event);
                else fail.run(event);
            });
        }

    }


    public class Listener implements EventListener<Event.CellEvent> {

        private LinkedList<ActionEvent<Event.CellEvent>> enter, stay, leave;

        public Listener() {
            enter = new LinkedList<>();
            stay  = new LinkedList<>();
            leave = new LinkedList<>();
        }

        @Override
        public void trigger(Event.CellEvent event) {
            LinkedList<ActionEvent<Event.CellEvent>> list;
            switch (event.getName()) {
                case "enter": list = enter;break;
                case "stay": list = stay;break;
                case "leave": list = leave;break;
                default: return;
            }

            runAll(list, event);
        }

        @Override
        public void add(String name, ActionEvent<Event.CellEvent> event) {
            switch (name) {
                case "enter": enter.add(event);break;
                case "stay": stay.add(event);break;
                case "leave": leave.add(event);break;
            }
        }

    }

    protected Cell() {
        cells.add(this);
        id = cells.size();

        listener = new Listener();
    }

    public static void flush () {
        cells = new ArrayList<>();
    }

    public Cell next (int incr) {
        return next(incr, true);
    }

    public Cell next (int incr, boolean forward) {
        return get(id + (forward ? incr : -incr));
    }

    public static Cell get (int index) {
        return cells.get(index - 1);
    }

    public static int size() {
        return cells.size();
    }

    public Listener listener() {
        return listener;
    }

    @Override
    public String toString() {
        return "case: " + id;
    }
}
