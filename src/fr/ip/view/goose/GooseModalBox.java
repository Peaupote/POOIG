package fr.ip.view.goose;

import fr.ip.model.goose.GoosePlayer;
import fr.ip.view.core.GameModalBox;

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
