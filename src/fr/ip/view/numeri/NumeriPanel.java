package fr.ip.view.numeri;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.core.Pawn;
import fr.ip.model.numeri.NumeriGame;
import fr.ip.model.numeri.NumeriPlayer;
import fr.ip.view.core.GameBoardPanel;
import fr.ip.view.core.MainFrame;
import fr.ip.view.core.SingleView;
import fr.ip.view.core.components.CellButton;
import fr.ip.view.numeri.NumeriModalBox;

import java.awt.*;
import java.util.ArrayList;

public class NumeriPanel extends GameBoardPanel implements SingleView {

    public NumeriPanel () {
        setImage("./assets/bkg.png");
    }

    public void onOpen () {
        NumeriModalBox modal = new NumeriModalBox(MainFrame.instance, "Add players", true);
        NumeriModalBox.Output out = modal.showGameModalBox();

        if (out.isCancel()) {
            MainFrame.set("menu");
            return;
        }
        removeAll();

        game = new NumeriGame();
        for(NumeriPlayer player : out)
            game.addPlayer(player);

        game.setup();
        game.start();
        buttons = new ArrayList<>();

        setLayout(new BorderLayout());
        add(new GameControlPanel(), BorderLayout.EAST);
        add(new BoardPanel(), BorderLayout.CENTER);

        playButton.addActionListener(e -> {
            game.playTurn();
            NumeriPlayer player = (NumeriPlayer) Game.getInstance().getCurrentPlayer();

            for (Pawn pawn : player.pawns()) {
                for (CellButton button : buttons) button.remove(pawn);
                buttons.get(pawn.getLocation().id - 1).add(pawn);
            }

        });

        MainFrame.canRestart(true);
        revalidate();
    }

}
