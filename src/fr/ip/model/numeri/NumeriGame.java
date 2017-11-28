package fr.ip.model.numeri;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.core.Pawn;
import fr.ip.model.core.Player;

public class NumeriGame extends Game {

    public NumeriGame () {
        super();

        addPlayer(new NumeriPlayer("j1"));
        addPlayer(new NumeriPlayer("j2"));
        addPlayer(new NumeriPlayer("j3"));
    }

    @Override
    protected void setup() {
        NumeriCell first = new NumeriCell();
        for (int i = 0; i < 10; i++)
            new NumeriCell();

        for (Player p: ps)
            for(Pawn pawn: ((NumeriPlayer)p).pawns())
                pawn.goToCell(first);
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
