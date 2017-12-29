package fr.ip.view.core.components;

public class Configuration {

    public static class Global {}

    public static class Game {

        public final static String[] options = {"Column", "Rectangle", "Zigzag", "Spiral"};

        public final int numberOfPlayers, numberOfCells, cellOrder;

        public Game (int numberOfCells, int numberOfPlayers, int cellOrder) {
            this.numberOfCells = numberOfCells;
            this.numberOfPlayers = numberOfPlayers;
            this.cellOrder = cellOrder;
        }

    }

    public final Game goose, numeri;

    public Configuration (Game goose, Game numeri) {
        this.goose = goose;
        this.numeri = numeri;
    }

}
