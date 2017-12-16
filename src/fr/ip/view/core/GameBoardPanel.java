package fr.ip.view.core;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.core.Player;
import fr.ip.view.core.components.CellButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoardPanel extends ImageBackgroundJPanel {

    protected Game game;
    protected ArrayList<JButton> buttons;
    protected JButton playButton;

    protected class BoardPanel extends ImageBackgroundJPanel {

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

    protected class GameControlPanel extends ImageBackgroundJPanel {

        protected class PlayerLabel extends JLabel {

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

}
