package fr.ip.view.core;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EndPanel extends ImageBackgroundJPanel implements SingleView {

    private String target = "goose";
    private JLabel win;

    public EndPanel() {
        super(new BorderLayout(30, 30));
        setImage("./assets/bkg.png");

        win = new JLabel("J1 won !");
        win.setFont(new Font("Helvetica", Font.BOLD, 30));
        win.setForeground(Color.WHITE);
        add(win, BorderLayout.NORTH);

        Icon gif = new ImageIcon("./assets/winner.gif");
        add(new JLabel(gif), BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton menuButton = new JButton("Menu"),
                again      = new JButton("Play again");
        footer.add(menuButton);
        footer.add(again);
        add(footer, BorderLayout.SOUTH);

        menuButton.addActionListener(e -> MainFrame.set("menu"));
        again.addActionListener(e -> MainFrame.set(target));
    }

    @Override
    public void onOpen(HashMap<String, Object> map) {
        target = map.get("target").toString();
        win.setText(map.get("win").toString() + " won !");

        MainFrame.canRestart(false);
    }
}
