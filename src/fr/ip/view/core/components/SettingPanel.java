package fr.ip.view.core.components;

import fr.ip.view.core.ImageBackgroundJPanel;
import fr.ip.view.core.SingleView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SettingPanel extends ImageBackgroundJPanel implements SingleView {

    private class GlobalPane extends JPanel {
        GlobalPane () {
            add(new JLabel("Hello"));
        }
    }

    private class BoardSettingPane extends JPanel implements ChangeListener {

        SpinnerNumberModel playerCount, cellCount;
        ArrayList<JButton> cells;
        JPanel buttonPane;
        OptionPanel opt;

        BoardSettingPane (OptionPanel opt) {
            super (new GridLayout(4, 1));
            this.opt = opt;
            cells = new ArrayList<>();
            playerCount = new SpinnerNumberModel(5,1,15,1);
            cellCount = new SpinnerNumberModel(15,2,100,1);
            JSpinner maxPlayer = new JSpinner(playerCount),
                    cellNumber = new JSpinner(cellCount);

            JPanel playerPane = new JPanel();
            playerPane.add(new JLabel("Maximum number of players"));
            playerPane.add(maxPlayer);
            add(playerPane);

            JPanel cellPane = new JPanel();
            cellPane.add(new JLabel("Number of cells"));
            cellPane.add(cellNumber);
            add(cellPane);

            buttonPane = new JPanel();
            stateChanged(null);
            add(buttonPane);

            cellCount.addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            buttonPane.removeAll();
            buttonPane.repaint();
            buttonPane.revalidate();
            int borderSize = (int)Math.sqrt(cellCount.getNumber().doubleValue());
            buttonPane.setLayout(new GridLayout(borderSize, borderSize, 10, 10));
            for (int i = 1; i <= cellCount.getNumber().intValue(); i++) {
                JButton btn = new JButton(i + "");
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

        private void addComponents() {
            add(btnName);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            removeAll();
            repaint();
            revalidate();

            id = Integer.parseInt(((JButton)actionEvent.getSource()).getText());
            setComponents();
            addComponents();

            repaint();
            revalidate();
        }

        void setComponents () {
            btnName = new JLabel("Button " + id);
        }
    }

    public SettingPanel() {
        super(new GridLayout(1,1));
        setImage("./assets/bkg.png");

        JPanel goose = new JPanel(new BorderLayout());
        OptionPanel gooseOptionPanel = new OptionPanel();
        BoardSettingPane goosePane = new BoardSettingPane(gooseOptionPanel);
        goose.add(goosePane, BorderLayout.WEST);
        goose.add(gooseOptionPanel, BorderLayout.CENTER);

        BoardSettingPane numeriPane = new BoardSettingPane(null);
        GlobalPane globalPane = new GlobalPane();

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Global settings", null, globalPane, "General settings");
        pane.addTab("Goose settings", null, goose, "Set cells for goose game");
        pane.addTab("Numeri settings", null, numeriPane, "Set cells for numeri game");

        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(pane);
    }

    @Override
    public void onOpen() {
    }
}
