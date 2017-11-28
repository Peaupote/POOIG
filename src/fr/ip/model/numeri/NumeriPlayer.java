package fr.ip.model.numeri;

import fr.ip.model.core.Event;
import fr.ip.model.core.Pawn;
import fr.ip.model.core.Player;

import java.util.*;

public class NumeriPlayer extends Player {

    private NumeriPawn[] pawns;

    private class NumeriPawn extends Pawn {

        public final int id;

        public NumeriPawn(int id) {
            this.id = id;
        }

        @Override
        public Player getPlayer() {
            return NumeriPlayer.this;
        }
    }

    public NumeriPlayer (String name) {
        super (name);

        pawns = new NumeriPawn[6];
        for (int i = 0; i < 6; i++)
            pawns[i] = new NumeriPawn(i);

        listener().add("play", (Event event) -> {
            System.out.println(this);
            int number = new Random().nextInt(5) + 1;
            System.out.println("You made " + number);
            System.out.println("Select your pawns: ");
            int[] ids;

            while (true) {
                ids = Arrays.stream(new Scanner(System.in).nextLine().split(","))
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue)
                        .distinct()
                        .toArray();
                if (Arrays.stream(ids).sum() == number) break;
                System.out.println("Wrong combinaison");
            }

            for (int id: ids)
                pawns[id - 1].goToCell(pawns[id].getLocation().next(1));

            System.out.println(name + " playing");
            for (Pawn pawn: pawns)
                System.out.println(pawn.getLocation());
            System.out.println("-------------");

        });
    }

    public Pawn[] pawns () {
        return pawns;
    }

}
