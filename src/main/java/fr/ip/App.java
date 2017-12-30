package fr.ip;

import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.goose.GoosePlayer;
import fr.ip.model.numeri.NumeriGame;

import java.awt.*;
import java.util.Scanner;

import fr.ip.model.numeri.NumeriPlayer;
import fr.ip.model.util.Facade;
import fr.ip.model.util.Settings;
import fr.ip.view.core.MainFrame;


public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1 : Goose\n2 : Numeri\n3 : GUI test\n4 : Question test\n0 : Exit");

        switch (sc.nextInt()) {
            case 0 : System.exit(0); return;
            case 1 : playInConsole(new GooseGame(15)); break;
            case 2 : playInConsole(new NumeriGame(11)); break;
            case 3 : playGUI();break;
            default: System.exit(1); return;
        }

    }

    public static void playInConsole (Game game) {
        new Facade.CommandLine();
        new Settings();

        if (game instanceof GooseGame) {
            game.addPlayer(new GoosePlayer("j1"));
            game.addPlayer(new GoosePlayer("j2"));
            game.addPlayer(new GoosePlayer("j3"));
        } else if (game instanceof NumeriGame) {
            game.addPlayer(new NumeriPlayer("j1"));
            game.addPlayer(new NumeriPlayer("j2"));
            game.addPlayer(new NumeriPlayer("j3"));
        }

        game.play();
    }

    public static void playGUI () {
        new Facade.Gui();
        new Settings();

        EventQueue.invokeLater(() -> new MainFrame().set("menu"));
    }
}
