package fr.flin.model.core;

@FunctionalInterface
public interface ActionEvent<T extends Event> {

    void run (T event);

}
