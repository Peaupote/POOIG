package fr.ip;

import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.numeri.NumeriGame;


public class Main {

    public static void main(String[] args) {
        Game g = new GooseGame();
        g.play();
    }
}
