package fr.ip.view.core;

import fr.ip.model.goose.GoosePlayer;

import java.awt.*;

public class GooseModalBox extends GameModalBox<GoosePlayer> {

    public GooseModalBox(Frame frame, String title, boolean modal) {
        super (frame, title, modal);
    }

    @Override
    protected GoosePlayer construct(String name) {
        return new GoosePlayer(name);
    }
}
