package fr.ip.model.numeri;

import fr.ip.model.core.Cell;

public class NumeriCell extends Cell {

    private boolean empty;

    public NumeriCell () {
        super();
        if (id != 1 && id != Cell.size())
            new SinglePawnCell(true);
    }

}
