package fr.ip.view.core;

import fr.ip.model.goose.GooseGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoosePanel extends ImageBackgroundJPanel implements SingleView {

    GooseGame game;
    ArrayList<JButton> buttons;

    public GoosePanel () {
        setImage("./assets/bkg.png");
    }

    public void onOpen () {
        //game = new GooseGame();
        //game.setup();
        buttons = new ArrayList<>();

        setLayout(new GridLayout(5,5, 30, 30));
        for (int i = 1; i <= 20; i++) {
            JButton button = new CellButton(i + "");
            buttons.add(button);
            add(button);
        }
    }
}
