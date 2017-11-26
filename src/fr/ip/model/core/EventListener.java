package fr.ip.model.core;

import java.util.LinkedList;

public interface EventListener<T extends Event> {

    void trigger (T event);

    void add (String name, ActionEvent<T> event);

    default void runAll (LinkedList<ActionEvent<T>> list, T event) {

        for (ActionEvent<T> e: list) {
            e.run(event);
            if (!event.isPropagate()) break;
        }
    }

}
