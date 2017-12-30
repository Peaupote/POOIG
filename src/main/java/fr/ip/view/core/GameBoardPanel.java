package fr.ip.view.core;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Game;
import fr.ip.model.core.Player;
import fr.ip.view.core.components.CellButton;
import fr.ip.view.core.components.PrimaryButton;
import fr.ip.view.core.components.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameBoardPanel extends ImageBackgroundJPanel.ImageBackgroundView {

    protected Game game;
    protected ArrayList<CellButton> buttons;
    protected PrimaryButton playButton;

    protected class BoardPanel extends ImageBackgroundJPanel {

        public BoardPanel (int cellOrder) {
            setBackground(ImageBackgroundJPanel.TRANSPARENT);

            for (int i = 1; i <= Cell.size(); i++)
                buttons.add(new CellButton(i + ""));

						int borderSize = (int)Math.ceil(Math.sqrt(buttons.size()));
						switch (cellOrder) {
								case Configuration.Game.CellOrder.COLUMN:
										setLayout(new GridLayout(buttons.size(), 1, 10, 10));
										for (CellButton button: buttons)
												add(button);
								break;
								case Configuration.Game.CellOrder.RECTANGLE:
										setLayout(new GridLayout(borderSize, borderSize, 10, 10));
										for (CellButton button: buttons) add(button);
								break;
								case Configuration.Game.CellOrder.ZIGZAG:
										setLayout(new GridLayout(borderSize, borderSize, 10, 10));

										for (int i = 0; i < borderSize; i++)
												if (i % 2 == 0)
														for (int j = 0; j < borderSize; j++) {
																int index = i * borderSize + j;
																if (index < buttons.size()) add(buttons.get(index));
														}
												else
														for (int j = 0; j <= borderSize; j++) {
																int index = (i + 1) * borderSize - j;
																if (index < buttons.size()) add(buttons.get(index));
														}
								break;
								case Configuration.Game.CellOrder.SPIRAL:
										setLayout(new GridLayout(borderSize, borderSize, 10, 10));
										int direction = 0, i = 0, j = 0, top = 0, bottom = borderSize, left = 0, right = borderSize;
										CellButton[][] grid = new CellButton[borderSize][borderSize];

										Iterator<CellButton> it = buttons.iterator();
										while (it.hasNext()) {
												while (it.hasNext() && left <= j && j < right && top <= i && i < bottom) {
														grid[i][j] = it.next();
														switch (direction) {
																case 0: j++;break;
																case 1: i++;break;
																case 2: j--;break;
																case 3: i--;break;
														}
												}

												switch(direction) {
														case 0: i++;j--;top++;break;
														case 1: i--;j--;right--;break;
														case 2: i--;j++;bottom--;break;
														case 3: i++;j++;left++;break;
												}
												direction = (direction + 1) % 4;
										}

										for (int x = 0; x < grid.length; x++)
												for (int y = 0; y < grid[0].length; y++)
														if (grid[x][y] != null) add(grid[x][y]);
														else add(new JLabel());
								break;
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
            setBackground(ImageBackgroundJPanel.TRANSPARENT);
            for (Player player: game)
                add(new PlayerLabel(player.name));

            playButton = new PrimaryButton("Roll dice");
            add(playButton);
        }
    }

    public GameBoardPanel() {
        super();
    }

}
