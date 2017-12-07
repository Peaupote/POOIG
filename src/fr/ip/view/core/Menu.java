package fr.ip.view.core;

import javax.swing.*;
import java.awt.*;

public class Menu extends ImageBackgroundJPanel implements SingleView {

    JButton numeri, goose;

    public Menu () {
        setImage("./assets/bkg.png");

        numeri = new MenuButton("Play Numeri");
        goose = new MenuButton("Play Goose");

        goose.addActionListener(e -> MainFrame.instance.set("goose"));
        numeri.addActionListener(e -> MainFrame.instance.set("numeri"));

        setLayout(new GridLayout(3, 1));
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(new Color(0,0,0,0));
        JLabel title = new JLabel("Numeri & Goose game");
        title.setForeground(Color.white);
        title.setFont(new Font("Helvetica", Font.BOLD, 40));
        labelPanel.add(title);
        add(labelPanel);

        JPanel buttons = new JPanel(new GridLayout(1,2, 50, 0)),
                left = new JPanel(), right = new JPanel();
        left.setBackground(new Color(0,0,0,0));
        right.setBackground(new Color(0,0,0,0));
        buttons.setBackground(new Color(0,0,0,0));
        left.add(goose);
        right.add(numeri);
        buttons.add(left);
        buttons.add(right);
        add(buttons);

        add(Box.createVerticalStrut(100));
    }

    @Override
    public void onOpen() {

    }
}