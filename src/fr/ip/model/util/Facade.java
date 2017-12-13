package fr.ip.model.util;

import fr.ip.view.core.MainFrame;

import javax.swing.*;
import java.util.Scanner;

public abstract class Facade {

    private static Facade instance;

    public static class CommandLine extends Facade {

        private static Scanner scanner = new Scanner(System.in);

        public CommandLine () {
            super();
        }

        @Override
        protected void showEntry(Object object) {
            System.out.println(object);
        }

        @Override
        protected String readEntry(String head) {
            showEntry(head);
            return scanner.nextLine();
        }
    }

    public static class Gui extends Facade {

        @Override
        protected void showEntry(Object object) {
            JOptionPane.showMessageDialog(MainFrame.instance, object.toString());
        }

        @Override
        protected String readEntry(String head) {
            return JOptionPane.showInputDialog(MainFrame.instance, head, "Title", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public Facade () {
        if (instance == null)
            instance = this;
    }

    protected abstract void showEntry (Object object);

    protected abstract String readEntry (String head);

    public static void show (Object object) {
        instance.showEntry(object);
    }

    public static String read (String head) {
        return instance.readEntry(head);
    }

    public static String read () {
        return instance.readEntry("");
    }

}
