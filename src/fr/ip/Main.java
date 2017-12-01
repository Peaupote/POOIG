package fr.ip;

import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.numeri.NumeriGame;
import java.util.Scanner;
import fr.ip.view.core.MainFrame;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game g;

        System.out.println("1 : Goose\n2 : Numeri\n3 : GUI test\n0 : Exit");

        switch (sc.nextInt()) {
            case 0 : System.exit(0); return;
            case 1 : g = new GooseGame(); break;
            case 2 : g = new NumeriGame(); break;
            case 3 : new MainFrame(); return;
            default: System.exit(1); return;
        }

        g.play();
    }
}
