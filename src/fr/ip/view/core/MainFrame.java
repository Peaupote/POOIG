package fr.ip.view.core;
import javax.swing.*;

public class MainFrame extends JFrame{
    public MainFrame() {
        super();
        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane();
        setLocation(100, 200);
        setVisible(true);
    }

    public class MainComponent extends JComponent {

    }
}
