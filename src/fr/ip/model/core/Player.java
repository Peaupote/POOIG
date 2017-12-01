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
            pawn.getPlayer().listener.add("end", (Event event) -> {
                if (pawn.getLocation().id == Cell.size())
                    Game.getInstance().removePlayer();
            });
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
            // System.out.println("--> target: " + target);
            if (target < Cell.size()) pawn.goToCell(Cell.get(target));
        }

    }

    public static class EndIfOnLastCell extends PawnAction {

        public EndIfOnLastCell(Pawn pawn) {
            super(pawn);
        }

        @Override
        public void run(Event event) {
            Cell locate = pawn.getLocation();
            int target = locate.id + (new Random()).nextInt(5) + 1;
            // System.out.println("--> target: " + target);
            if (target <= Cell.size()) pawn.goToCell(Cell.get(target));
            else if (target > Cell.size()) pawn.goToCell(Cell.get(2 * Cell.size() - target));
        }
    }

    public class Listener implements EventListener<Event> {

        private LinkedList<ActionEvent<Event>> passEvent, startEvent, endEvent;

        public Listener() {
            passEvent  = new LinkedList<ActionEvent<Event>>();
            startEvent = new LinkedList<ActionEvent<Event>>();
            endEvent = new LinkedList<ActionEvent<Event>>();
        }

        @Override
        public void trigger(Event event) {
            LinkedList<ActionEvent<Event>> list;
            switch (event.getName()) {
                case "pass": list = passEvent;break;
                case "play": list = startEvent;break;
                case "end" : list = endEvent; break;
                default: return;
            }

            runAll(list, event);
        }

        @Override
        public void add(String name, ActionEvent event) {
            switch (name) {
                case "pass": passEvent.add(event);break;
                case "play": startEvent.add(event);break;
                case "end": endEvent.add(event);break;
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
