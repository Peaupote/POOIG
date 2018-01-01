package fr.ip.view.core.components;

public class Configuration {

		public static final Configuration configuration = new Configuration ();

    public static class Global {}

    public static class Game {

				public static class CellOrder {

						public final static String[] options = {"Column", "Rectangle", "Zigzag", "Spiral"};

						public static final int COLUMN    = 0,
									 									RECTANGLE = 1,
																		ZIGZAG    = 2,
																		SPIRAL    = 3;

				}

        private int numberOfPlayers, numberOfCells, cellOrder;

        public Game (int numberOfCells, int numberOfPlayers, int cellOrder) {
            this.numberOfCells = numberOfCells;
            this.numberOfPlayers = numberOfPlayers;
            this.cellOrder = cellOrder;
        }

				public void setNumberOfPlayers (int n) {
						numberOfPlayers = Math.max(0, n);
				}

				public void setNumberOfCells (int n) {
						numberOfCells = Math.max(0, n);
				}

				public void setCellOrder (int n) {
						cellOrder = Math.min(CellOrder.options.length - 1, Math.max(0, n));
				}

				public int getNumberOfPlayers () {
						return numberOfPlayers;
				}

				public int getNumberOfCells () {
						return numberOfCells;
				}

				public int getCellOrder () {
						return cellOrder;
				}

    }

    public final Game goose, numeri;

    public Configuration () {
        this.goose = new Game(15, 5, 3);
        this.numeri = new Game(40, 5, 3);
    }

}
