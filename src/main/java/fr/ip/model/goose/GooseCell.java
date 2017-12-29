package fr.ip.model.goose;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Event;
import fr.ip.model.core.Game;
import fr.ip.model.util.Facade;
import fr.ip.model.util.Message;

import javax.swing.*;

public class GooseCell extends Cell {

    public GooseCell () {
        super();
        if (id != 1 && id != GooseGame.LENGTH)
            new SinglePawnCell(false);

        if (id == 5) new TrapCell();
        if (id == 6) new CounterCell(2);
        if (id == 7) new JumpCell(GooseGame.LENGTH);
        if (id % 4 == 0) listener().add("enter", (Event.CellEvent e) -> {
            Facade.show(new Message("Play again"));
            Game.getInstance().playAgain();
            e.stopPropagation();
        });
        if (id == 10)
            new QuestionCell(
                    (String s) -> s.equals("true"),
                    (Event.CellEvent event) -> Facade.show(new Message("Correct answer")),
                    (Event.CellEvent event) -> Facade.show(new Message("It's a fail loser !")));
    }
}
