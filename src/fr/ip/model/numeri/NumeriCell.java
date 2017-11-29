package fr.ip.model.numeri;

import fr.ip.model.core.Cell;

public class NumeriCell extends Cell {

    public NumeriCell () {
        super();
        if (id != 1 && id != Cell.size() - 1)
            new SinglePawnCell(true);

    }

}
