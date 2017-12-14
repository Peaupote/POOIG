package fr.ip.view.goose;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.goose.GoosePlayer;
import fr.ip.view.core.GameBoardPanel;
import fr.ip.view.core.MainFrame;
import fr.ip.view.core.SingleView;
import fr.ip.view.goose.GooseModalBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoosePanel extends GameBoardPanel implements SingleView {

    public GoosePanel () {
        setImage("./assets/bkg.png");
    }

    public void onOpen () {
        GooseModalBox modal = new GooseModalBox(MainFrame.instance, "Add players", true);
        GooseModalBox.Output out = modal.showGameModalBox();

        if (out.isCancel()) {
            MainFrame.set("menu");
            return;
        }
        removeAll();

        game = new GooseGame();
        for(GoosePlayer player : out)
            game.addPlayer(player);

        game.setup();
        game.start();
        buttons = new ArrayList<>();

        setLayout(new BorderLayout());
        add(new GameControlPanel(), BorderLayout.EAST);
        add(new BoardPanel(), BorderLayout.CENTER);

        playButton.addActionListener(e -> {
            game.playTurn();
            GoosePlayer p = (GoosePlayer)Game.getInstance().getCurrentPlayer();

            for (int i = 0; i < Cell.size(); i++) {
                JButton btn = buttons.get(i);
                if (btn.getText().equals(p.name)) {
                    btn.setEnabled(true);
                    btn.setText((i + 1) + "");
                }
            }

            JButton btn = buttons.get(p.getPawn().getLocation().id - 1);
            btn.setText(p.name);
            btn.setEnabled(false);
        });

        MainFrame.canRestart(true);
        revalidate();
    }
}
