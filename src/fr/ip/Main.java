package fr.ip;

import fr.ip.model.core.Game;
import fr.ip.model.goose.GooseGame;
import fr.ip.model.numeri.NumeriGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import fr.ip.view.core.MainFrame;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1 : Goose\n2 : Numeri\n3 : GUI test\n0 : Exit");

        switch (sc.nextInt()) {
            case 0 : System.exit(0); return;
            case 1 : playInConsole(new GooseGame()); break;
            case 2 : playInConsole(new NumeriGame()); break;
            case 3 : playGUI();break;
            default: System.exit(1); return;
        }

    }

    public static void playInConsole (Game game) {
        game.play();
    }

    public static void playGUI () {
        EventQueue.invokeLater(() -> new MainFrame());
    }
}
