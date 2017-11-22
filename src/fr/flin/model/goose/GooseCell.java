package fr.flin.model.goose;

import fr.flin.model.core.Cell;
import fr.flin.model.core.Event;
import fr.flin.model.core.Game;

public class GooseCell extends Cell {

    public GooseCell () {
        super();
        try {
            listener().add("enter", (Event.CellEvent event) -> {
                if (event.getTarget().id % 2 != id % 2)
                    System.out.println("change");
                if (id >= 100) Game.getInstance().removePlayer();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
