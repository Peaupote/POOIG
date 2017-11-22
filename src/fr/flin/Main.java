package fr.flin;

import fr.flin.model.core.Game;
import fr.flin.model.goose.GooseGame;


public class Main {

    public static void main(String[] args) {
        Game g = new GooseGame();
        g.play();
    }
}
