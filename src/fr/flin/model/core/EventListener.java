package fr.flin.model.core;

public interface EventListener<T extends Event> {

    void trigger (T event) throws Exception;

    void add (String name, ActionEvent<T> event) throws Exception;

}
