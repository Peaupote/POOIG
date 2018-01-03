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

        public int numberOfPlayers, numberOfCells, cellOrder;

        public Game (int numberOfCells, int numberOfPlayers, int cellOrder) {
            this.numberOfCells = numberOfCells;
            this.numberOfPlayers = numberOfPlayers;
            this.cellOrder = cellOrder;
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

        public int endMode;
        public boolean cohabits, questions;
        
        public Goose (int nC, int nP, int cO, int endMode, boolean cohabits, boolean questions) {
            super (nC, nP, cO);
            this.endMode   = endMode;
            this.cohabits  = cohabits;
            this.questions = questions;
        }

    }

    public static class Numeri extends Game {
        
        public boolean align, replay;

        public Numeri (int nC, int nP, int cO, boolean align, boolean replay) {
            super(nC, nP, cO);
            this.align  = align;
            this.replay = replay;
        }

    }

    public final Goose goose;
    public final Numeri numeri;

    public Configuration () {
        this.goose  = new Goose(15, 5, 3, 0, false, true);
        this.numeri = new Numeri(40, 5, 3, false, false);
    }

}
