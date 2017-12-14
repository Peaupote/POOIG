package fr.ip.view.numeri;

import fr.ip.model.numeri.NumeriPlayer;
import fr.ip.view.core.GameModalBox;

import java.awt.*;

public class NumeriModalBox extends GameModalBox<NumeriPlayer> {

    public NumeriModalBox(Frame frame, String title, boolean modal) {
        super (frame, title, modal);
    }

    @Override
    protected NumeriPlayer construct(String name) {
        return new NumeriPlayer(name);
    }
}
