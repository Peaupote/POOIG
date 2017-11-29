package fr.ip.model.goose;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Event;

public class GooseCell extends Cell {

    public GooseCell () {
        super();
        if (id != 1 && id != GooseGame.LENGTH)
            new SinglePawnCase(false);

        if (id == 5) new TrapCell();
        if (id == 6) new CounterCell(2);
        if (id == 7) new JumpCell(GooseGame.LENGTH);
        if (id % 3 == 0)
            new QuestionCell(
                    (String s) -> s.equals("true"),
                    (Event.CellEvent event) -> System.out.println("Correct answer"),
                    (Event.CellEvent event) -> System.out.println("It's a fail loser !"));
    }



}
