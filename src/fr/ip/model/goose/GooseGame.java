package fr.ip.model.goose;

import fr.ip.model.core.Game;
import fr.ip.model.core.Player;

public class GooseGame extends Game {

    private GooseCell first;
    public static final int LENGTH = 15;

    public GooseGame() {
        super();

        addPlayer(new GoosePlayer("j1"));
        addPlayer(new GoosePlayer("j2"));
        addPlayer(new GoosePlayer("j3"));
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
        return false;
    }
}
