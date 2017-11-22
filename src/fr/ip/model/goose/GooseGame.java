package fr.ip.model.goose;

import fr.ip.model.core.Game;
import fr.ip.model.core.Player;

public class GooseGame extends Game {

    private GooseCell first;

    public GooseGame() {
        super();

        addPlayer(new GoosePlayer("j1"));
        addPlayer(new GoosePlayer("j2"));
        addPlayer(new GoosePlayer("j3"));
    }

    protected void setup () {
        // setup cells
        first = new GooseCell();
        for (int i = 0; i < 120; i++)
            new GooseCell();

        for (Player p: ps)
            ((GoosePlayer)p).getPawn().goToCell(first);
    }

    public boolean isEnd () {
        return false;
    }
}
