package fr.ip.model.core;

@FunctionalInterface
public interface ActionEvent<T extends Event> {

    void run (T event);

}
