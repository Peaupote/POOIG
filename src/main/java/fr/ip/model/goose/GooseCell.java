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
        if (id != 1 && id != GooseGame.getSize() && id != 5)
            new SinglePawnCell(false);

        if (id == 5) new TrapCell();
        if (id == 6 || id == 9) new CounterCell(2);
        if (id % 3 == 0) new JumpCell(5);
        if (id == 8) listener().add("enter", (Event.CellEvent e) -> {
            Facade.show(new Message("Play again"));
            Game.getInstance().playAgain();
            e.stopPropagation();
        });
        if (id == 10)
            new QuestionCell(
                    "Say true.",
                    (String s) -> s.equals("true"),
                    (Event.CellEvent event) -> Facade.show(new Message("Correct answer")),
                    (Event.CellEvent event) -> Facade.show(new Message("It's a fail loser !")));
    }
}
