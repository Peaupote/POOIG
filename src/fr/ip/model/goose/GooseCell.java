package fr.ip.model.goose;

import fr.ip.model.core.Cell;

public class GooseCell extends Cell {

    public GooseCell () {
        super();
        try {
            SinglePawnCase singlePawnCase = new SinglePawnCase();
            if (id != 1 && id != GooseGame.LENGTH) {
                listener().add("enter", singlePawnCase.enter);
                listener().add("leave", singlePawnCase.leave);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
