package fr.ip.model.core;

public class Event {

    public final String name;

    public Event(String name) {
        this.name = name;
    }

    public static class CellEvent extends Event {

        private final Cell target;
        private final Pawn pawn;

        public CellEvent(String name, Cell target, Pawn pawn) {
            super(name);
            this.target = target;
            this.pawn = pawn;
        }

        public Pawn getPawn() {
            return pawn;
        }

        public Cell getTarget() {
            return target;
        }
    }

    public static class EventType<T> extends Event {

        private final T element;

        public EventType (String name, T element) {
            super(name);
            this.element = element;
        }

        public T getElement() {
            return element;
        }
    }

    public String getName() { return name; }

}
