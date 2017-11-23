package fr.ip.model.core;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Cell {

    public final int id;
    private static ArrayList<Cell> cells = new ArrayList<Cell>();
    private Listener listener;

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

    public void move (Pawn p, Cell dest) {
        try {
            listener.trigger(new Event.CellEvent("enter", dest, p));
            listener.trigger(new Event.CellEvent("leave", this, p));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
