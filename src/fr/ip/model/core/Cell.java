package fr.ip.model.core;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Cell {

    public final int id;
    private static ArrayList<Cell> cells = new ArrayList<Cell>();
    private Listener listener;

    public static class SinglePawnCase {

        private boolean empty = false;
        public final Enter enter;
        public final Leave leave;

        public SinglePawnCase() {
            empty = true;
            enter = new Enter();
            leave = new Leave();
        }

        public class Enter implements ActionEvent<Event.CellEvent> {
            @Override
            public void run(Event.CellEvent event) {
                if (!empty) {
                    event.getPawn().goToCell(Cell.get(event.getTarget().id - 1));
                } else empty = false;
            }
        }

        public class Leave implements ActionEvent<Event.CellEvent> {
            @Override
            public void run(Event.CellEvent event) {
                empty = true;
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

            for (ActionEvent e: list) e.run(event);
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
