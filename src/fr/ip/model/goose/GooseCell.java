package fr.ip.model.goose;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Event;
import fr.ip.model.core.Game;

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
