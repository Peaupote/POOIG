package fr.ip.model.goose;

import fr.ip.model.core.Cell;

public class GooseCell extends Cell {

    public GooseCell () {
        super();
        if (id == 5) new TrapCell(this);


    }



}
