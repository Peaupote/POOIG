package fr.ip.model.numeri;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Event;

public class NumeriCell extends Cell {

    private boolean empty;

    public NumeriCell () {
        if (id != 1 && id != Cell.size()) {
            empty = true;
            listener().add("enter", (Event.CellEvent e) -> {
                if (!empty) {
                    e.getPawn().goToCell(next(1));
                    e.stopPropagation();
                } else empty = false;
            });

            listener().add("leave" , (Event.CellEvent e) -> empty = false);
        }
    }

}
