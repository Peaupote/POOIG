package fr.ip.view.core;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private HashMap<String, SingleView> map;
    private CardLayout cl;
    public static MainFrame instance = null;
    private String current;
    private final JMenuItem restart;

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

        JMenuBar bar = new JMenuBar();
        JMenu game = new JMenu("Game");
        restart = new JMenuItem("Restart");
        JMenuItem menu = new JMenuItem("Menu"),
                  goose = new JMenuItem("Goose game"),
                  numeri = new JMenuItem("Numeri game");

        game.add(restart);
        game.add(menu);
        game.add(goose);
        game.add(numeri);
        bar.add(game);
        setJMenuBar(bar);

        restart.setEnabled(false);
        restart.addActionListener(e -> {
            String tmp = current;
            set("menu");
            set(tmp);
        });
        menu.addActionListener(e -> set("menu"));
        goose.addActionListener(e -> set("goose"));
        numeri.addActionListener(e -> set("numeri"));


        setVisible(true);
    }

    public static void canRestart (boolean can) {
        instance.restart.setEnabled(can);
    }

    public static <T extends Component & SingleView> void addView (String name, T c) {
        instance.getContentPane().add(name, c);
        instance.map.put(name, c);
    }

    public static void set (String name) {
        instance.current = name;
        instance.setTitle("Game - " + name);
        instance.map.get(name).onOpen();
        instance.cl.show(instance.getContentPane(), name);
    }
}
