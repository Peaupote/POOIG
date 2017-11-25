package fr.ip.model.goose;

import fr.ip.model.core.Cell;
import fr.ip.model.core.Event;
import fr.ip.model.core.Pawn;
import fr.ip.model.core.Player;

public class GoosePlayer extends Player {

    private Pawn pawn;

    public GoosePlayer (String name) {
        super(name);

        pawn = new Pawn() {
            @Override
            public Player getPlayer() {
                return GoosePlayer.this;
            }
        };

        try {
            listener().add("play", new EndIfOnLastCell(pawn));

            listener().add("pass", (Event event) -> {
                Cell c = pawn.getLocation();
                try {
                    c.listener().trigger(new Event.CellEvent("stay", c, pawn));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            listener().add("play", (Event event) -> {
                System.out.println(name + " playing");
                System.out.println(pawn.getLocation());
                System.out.println("-------------");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Pawn getPawn() {
        return pawn;
    }
}
