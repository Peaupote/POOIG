package fr.ip.view.core;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private HashMap<String, SingleView> map;
    private CardLayout cl;
    public static MainFrame instance = null;

    public MainFrame() {
        super();
        if (instance == null) instance = this;
        map = new HashMap<>();
        cl = new CardLayout();

        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(cl);

        addView("menu", new Menu());
        addView("numeri", new NumeriPanel());
        addView("goose", new GoosePanel());
        set("menu");

        JMenuBar bar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem menu = new JMenuItem("Menu"),
                  goose = new JMenuItem("Goose game"),
                  numeri = new JMenuItem("Numeri game");

        game.add(menu);
        game.add(goose);
        game.add(numeri);
        bar.add(game);
        setJMenuBar(bar);

        menu.addActionListener(e -> set("menu"));
        goose.addActionListener(e -> set("goose"));
        numeri.addActionListener(e -> set("numeri"));


        setVisible(true);
    }

    public <T extends Component & SingleView> void addView (String name, T c) {
        getContentPane().add(name, c);
        map.put(name, c);
    }

    public void set (String name) {
        map.get(name).onOpen();
        cl.show(getContentPane(), name);
    }
}
