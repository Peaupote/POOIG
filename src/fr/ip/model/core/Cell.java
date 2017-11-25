package fr.ip.model.core;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Cell {

    public final int id;
    private static ArrayList<Cell> cells = new ArrayList<Cell>();
    private Listener listener;

    public static class SinglePawnCase {

        private boolean empty = false;

        public SinglePawnCase(Cell cell) {
            empty = true;
            try {
                cell.listener().add("enter", (Event.CellEvent event) -> {
                    System.out.println("Single pawn cell event");
                    if (!empty) {
                        event.getPawn().goToCell(Cell.get(event.getTarget().id - 1));
                        event.stopPropagation();
                    } else empty = false;
                });

                cell.listener().add("leave", (Event.CellEvent event) -> {
                    empty = true;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static class TrapCell {

        private Player player;

        public TrapCell(Cell cell) {
            player = null;
            try {
                cell.listener.add("enter", (Event.CellEvent event) -> {
                    System.out.println("Trap cell event");
                    if (player != null)
                        Game.getInstance().add(player);
                    player = event.getPawn().getPlayer();
                    Game.getInstance().removePlayer();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        public void trigger(Event.CellEvent event) throws Exception {
            LinkedList<ActionEvent<Event.CellEvent>> list = null;
            switch (event.getName()) {
                case "enter": list = enter;break;
                case "stay": list = stay;break;
                case "leave": list = leave;break;
                default: throw new Exception();
            }

            runAll(list, event);
        }

        @Override
        public void add(String name, ActionEvent<Event.CellEvent> event) throws Exception {
            switch (name) {
                case "enter": enter.add(event);break;
                case "stay": stay.add(event);break;
                case "leave": leave.add(event);break;
                default: throw new Exception();
            }
        }

    }

    protected Cell() {
        cells.add(this);
        id = cells.size();

        listener = new Listener();
    }

    public Cell next (int incr) {
        return next(incr, true);
    }

    public Cell next (int incr, boolean forward) {
        return get(id + ((forward) ? incr : -incr));
    }

    static Cell get (int index) {
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
