package fr.ip.model.numeri;

import fr.ip.model.core.Cell;

public class NumeriCell extends Cell {

    public NumeriCell () {
        super();
        if (id != 1 && id != NumeriGame.LENGTH)
            new SinglePawnCell(true);

    }

}
