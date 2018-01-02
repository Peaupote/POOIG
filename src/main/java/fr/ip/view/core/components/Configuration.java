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

    public static class Goose extends Game {

        public static class EndMode {
            
            public static final String[] modes = {
                "Terminer quand sur la dernière case",
                "Terminer quand dépasse la dernière case"
            };  

            public static final int 
              ON_LAST   = 0,
              OVER_LAST = 1;

        }

        private int endMode;
        private boolean cohabits, questions;
        
        public Goose (int nC, int nP, int cO, int endMode, boolean cohabits, boolean questions) {
            super (nC, nP, cO);
            this.endMode   = endMode;
            this.cohabits  = cohabits;
            this.questions = questions;
        }

        public void setEndMode (int endMode) {
            this.endMode = Math.min(0, Math.max(endMode, EndMode.modes.length));
        }

        public void setCohabits (boolean c) {
            cohabits = c;
        }

        public void askQuestions (boolean ask) {
            questions = ask;
        }
        
        public int getEndMode() { return endMode; }

        public boolean canCohabits () { return cohabits; }

        public boolean isQuestion () { return questions; };
    }

    public final Goose goose;
    public final Game numeri;

    public Configuration () {
        this.goose  = new Goose(15, 5, 3, 0, false, true);
        this.numeri = new Game(40, 5, 3);
    }

}
