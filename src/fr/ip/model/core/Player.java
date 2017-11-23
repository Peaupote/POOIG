package fr.ip.model.core;

import java.util.LinkedList;
import java.util.Random;

public abstract class Player {

    public final String name;
    private Listener listener;

    public static abstract class PawnAction implements ActionEvent {

        protected Pawn pawn;

        public PawnAction(Pawn pawn) {
            this.pawn = pawn;
        }

        public abstract void run(Event event);

    }

    public static class EndIfOverLastCell extends PawnAction {

        public EndIfOverLastCell(Pawn pawn) {
            super(pawn);
        }

        public void run (Event event) {
            Cell locate = pawn.getLocation();
            int target = locate.id + (new Random()).nextInt(6);
            if (target < Cell.size()) pawn.goToCell(Cell.get(target));
            else Game.getInstance().removePlayer();
        }

    }

    public static class EndIfOnLastCell extends PawnAction {

        public EndIfOnLastCell(Pawn pawn) {
            super(pawn);
        }

        @Override
        public void run(Event event) {
            Cell locate = pawn.getLocation();
            int target = locate.id + (new Random()).nextInt(6);
            if (target < Cell.size()) pawn.goToCell(Cell.get(target));
            else if (target > Cell.size()) pawn.goToCell(Cell.get(2 * Cell.size() - target));
            else {
                pawn.goToCell(Cell.get(target));
                Game.getInstance().removePlayer();
            }
        }
    }

    public class Listener implements EventListener<Event> {

        private LinkedList<ActionEvent> passEvent, startEvent;

        public Listener() {
            passEvent  = new LinkedList<ActionEvent>();
            startEvent = new LinkedList<ActionEvent>();
        }

        @Override
        public void trigger(Event event) throws Exception {
            LinkedList<ActionEvent> list = null;
            switch (event.getName()) {
                case "pass": list = passEvent;break;
                case "play": list = startEvent;break;
                default: throw new Exception();
            }

            for (ActionEvent e: list) e.run(event);
        }

        @Override
        public void add(String name, ActionEvent event) throws Exception {
            switch (name) {
                case "pass": passEvent.add(event);break;
                case "play": startEvent.add(event);break;
                default: throw new Exception();
            }
        }
    }

    public Player (String name) {
        this.name = name;
        listener = new Listener();
    }

    public Listener listener () {
        return listener;
    }

    @Override
    public String toString() {
        return name;
    }
}
