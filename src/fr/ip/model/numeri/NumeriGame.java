package fr.ip.model.numeri;

import fr.ip.model.core.Game;
import fr.ip.model.core.Pawn;
import fr.ip.model.core.Player;

public class NumeriGame extends Game {

    public static final int LENGTH = 10;

    public NumeriGame () {
        super();
    }

    @Override
    public void setup() {
        NumeriCell first = new NumeriCell();
        for (int i = 0; i < LENGTH; i++)
            new NumeriCell();

        for (Player p: ps)
            for(Pawn pawn: ((NumeriPlayer)p).pawns())
                pawn.goToCell(first);
    }

    @Override
    public boolean isEnd() {
        // bad implementation, to rework
        int c = 0;
        for (Player player : this)
            for (Pawn p : ((NumeriPlayer)player).pawns())
                if (p.getLocation().id > LENGTH - 3) {
                    c++;
                    if (c == 3) return true;
                }
        return false;
    }
}
