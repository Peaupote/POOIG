package fr.ip.view.core;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.core.Player;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.goose.GoosePlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoosePanel extends ImageBackgroundJPanel implements SingleView {

    private GooseGame game;
    private ArrayList<JButton> buttons;
    private JButton playButton;
    private boolean hasStart;

    public GoosePanel () {
        setImage("./assets/bkg.png");
    }

    private class BoardPanel extends ImageBackgroundJPanel {

        public BoardPanel () {
            setLayout(new GridLayout(5,5, 30, 30));
            setBackground(new Color(0,0,0,0));

            for (int i = 1; i <= Cell.size(); i++) {
                JButton button = new CellButton(i + "");
                buttons.add(button);
                add(button);
            }
        }
    }

    private class GameControlPanel extends ImageBackgroundJPanel {

        private class PlayerLabel extends JLabel {

            public PlayerLabel(String text) {
                super(text);
                setHorizontalAlignment(SwingConstants.CENTER);
                setVerticalAlignment(SwingConstants.CENTER);
                setForeground(Color.WHITE);
                setFont(new Font("Helvetica", Font.PLAIN, 20));
            }
        }

        public GameControlPanel () {
            setLayout(new GridLayout( game.size() + 1, 1, 0, 10));
            setBackground(new Color(0,0,0,0));
            for (Player player: game)
                add(new PlayerLabel(player.name));

            playButton = new JButton("Roll dice");
            add(playButton);
        }
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

        revalidate();
    }
}
