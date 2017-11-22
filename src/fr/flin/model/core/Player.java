package fr.flin.model.core;

import java.util.LinkedList;

public abstract class Player {

    public final String name;
    private Listener listener;

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
