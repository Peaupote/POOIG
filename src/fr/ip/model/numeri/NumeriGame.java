package fr.ip.model.numeri;

import fr.ip.model.core.Game;

public class NumeriGame extends Game {

    public NumeriGame () {
        super();

        addPlayer(new NumeriPlayer("j1"));
        addPlayer(new NumeriPlayer("j2"));
        addPlayer(new NumeriPlayer("j3"));
    }

    @Override
    protected void setup() {

    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
