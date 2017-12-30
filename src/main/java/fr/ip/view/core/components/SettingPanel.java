package fr.ip.view.core.components;

import fr.ip.view.core.ImageBackgroundJPanel;
import fr.ip.view.core.SingleView;
import fr.ip.view.core.components.PrimaryButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingPanel extends ImageBackgroundJPanel implements SingleView {

    private class GlobalPane extends JPanel {
        GlobalPane () {
            add(new JLabel("Hello"));
        }
    }

    private class BoardSettingPane extends JPanel implements ChangeListener {

        SpinnerNumberModel playerCount, cellCount;
        JComboBox cellOrder;
        ArrayList<PrimaryButton> cells;
        JPanel buttonPane;
        OptionPanel opt;

        BoardSettingPane (Configuration.Game game, OptionPanel opt) {
            super (new GridLayout(5, 1));
            this.opt = opt;
            cells = new ArrayList<>();
            playerCount = new SpinnerNumberModel(game.getNumberOfPlayers(),1,15,1);
            cellCount = new SpinnerNumberModel(game.getNumberOfCells(),2,100,1);
            JSpinner maxPlayer = new JSpinner(playerCount),
                    cellNumber = new JSpinner(cellCount);

            JPanel playerPane = new JPanel();
            playerPane.add(new JLabel("Maximum number of players"));
            playerPane.add(maxPlayer);
            add(playerPane);

            JPanel comboPanel = new JPanel();
            comboPanel.add(new JLabel("Cell order"));
            cellOrder = new JComboBox(Configuration.Game.CellOrder.options);
            cellOrder.setSelectedIndex(game.getCellOrder());
            comboPanel.add(cellOrder);
            add(comboPanel);

            JPanel cellPane = new JPanel();
            cellPane.add(new JLabel("Number of cells"));
            cellPane.add(cellNumber);
            add(cellPane);

            buttonPane = new JPanel();
            stateChanged(null);
            add(buttonPane);

						playerCount.addChangeListener(e -> game.setNumberOfPlayers(playerCount.getNumber().intValue()));
						cellOrder.addActionListener(e -> game.setCellOrder(cellOrder.getSelectedIndex()));
            cellCount.addChangeListener(this);
						cellCount.addChangeListener(e -> game.setNumberOfCells(cellCount.getNumber().intValue()));
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            buttonPane.removeAll();
            buttonPane.repaint();
            buttonPane.revalidate();
            int borderSize = (int)Math.sqrt(cellCount.getNumber().doubleValue());
            buttonPane.setLayout(new GridLayout(borderSize, borderSize, 10, 10));
            for (int i = 1; i <= cellCount.getNumber().intValue(); i++) {
                PrimaryButton btn = new PrimaryButton(i + "");
                cells.add(btn);
                buttonPane.add(btn);
                btn.addActionListener(opt);
            }
            buttonPane.repaint();
            buttonPane.revalidate();
         }
    }

    private class OptionPanel extends JPanel implements ActionListener {

        JLabel btnName;
        int id;

        OptionPanel() {
            btnName = new JLabel("Select a cell");
            addComponents();
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            removeAll();
            repaint();
            revalidate();

            onActionPerformed(actionEvent);
            setComponents();
            addComponents();

            repaint();
            revalidate();
        }

        protected void addComponents() {
            add(btnName);
        }

        protected void setComponents () {
            btnName = new JLabel("Cell " + id);
        }

        protected void onActionPerformed(ActionEvent actionEvent) {
            id = Integer.parseInt(((PrimaryButton)actionEvent.getSource()).getText());
        }
    }

    private class GooseOptionPanel extends OptionPanel {
    }

    private BoardSettingPane goosePane, numeriPane;

    public SettingPanel() {
        super(new GridLayout(1,1));
        setImage("./assets/bkg.png");

        JPanel goose = new JPanel(new BorderLayout());
        OptionPanel gooseOptionPanel = new GooseOptionPanel();
        goosePane = new BoardSettingPane(Configuration.configuration.goose, gooseOptionPanel);
        goose.add(goosePane, BorderLayout.WEST);
        goose.add(gooseOptionPanel, BorderLayout.CENTER);

				JPanel numeri = new JPanel(new BorderLayout());
				OptionPanel numeriOptionPanel = new OptionPanel ();
        numeriPane = new BoardSettingPane(Configuration.configuration.numeri, numeriOptionPanel);
				numeri.add(numeriPane, BorderLayout.WEST);
				numeri.add(numeriOptionPanel, BorderLayout.CENTER);

        GlobalPane globalPane = new GlobalPane();

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Global settings", null, globalPane, "General settings");
        pane.addTab("Goose settings", null, goose, "Set cells for goose game");
        pane.addTab("Numeri settings", null, numeri, "Set cells for numeri game");

        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(pane);
    }

    @Override
    public void onOpen(HashMap<String, Object> map) {
    }

		@Override
		public void onClose () {}
}
