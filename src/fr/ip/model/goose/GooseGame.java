package fr.ip.model.goose;

import fr.ip.model.core.Game;
import fr.ip.model.core.Player;

public class GooseGame extends Game {

    private GooseCell first;
    public static final int LENGTH = 15;

    public GooseGame() {
        super();
    }

    public void setup () {
        // setup cells
        first = new GooseCell();
        for (int i = 0; i < LENGTH - 1; i++)
            new GooseCell();

        for (Player p: ps)
            ((GoosePlayer)p).getPawn().goToCell(first);
    }

    public boolean isEnd () {
        for (Player player : this)
            if (((GoosePlayer)player).getPawn().getLocation().id == LENGTH)
                return true;
        return false;
    }

}
