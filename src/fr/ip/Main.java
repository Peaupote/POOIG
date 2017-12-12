package fr.ip;

import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.numeri.NumeriGame;
import java.util.Scanner;
import fr.ip.view.core.MainFrame;
import fr.ip.model.util.SETTINGS;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game g;
        SETTINGS s = new SETTINGS();

        System.out.println("1 : Goose\n2 : Numeri\n3 : GUI test\n4 : Question test\n0 : Exit");

        switch (sc.nextInt()) {
            case 0 : System.exit(0); return;
            case 1 : g = new GooseGame(); break;
            case 2 : g = new NumeriGame(); break;
            case 3 : new MainFrame(); return;
            case 4 : g = new GooseGame();
                break;
            default: System.exit(1); return;
        }

        g.play();

        s.write();

    }
}
