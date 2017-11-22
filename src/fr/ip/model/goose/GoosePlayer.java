package fr.ip.model.goose;

import fr.ip.model.core.Event;
import fr.ip.model.core.Pawn;
import fr.ip.model.core.Player;

import java.util.Random;

public class GoosePlayer extends Player {

    private Pawn pawn;

    public GoosePlayer (String name) {
        super(name);

        try {
            listener().add("pass", (Event event) -> {
                System.out.println("pass");
            });

            listener().add("play", (Event event) -> {
                System.out.println(name + " playing");
                pawn.goToCell(pawn.getLocation().next((new Random()).nextInt(6)));
                System.out.println(pawn.getLocation());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        pawn = new Pawn() {
            @Override
            public Player getPlayer() {
                return GoosePlayer.this;
            }
        };
    }

    public Pawn getPawn() {
        return pawn;
    }
}
