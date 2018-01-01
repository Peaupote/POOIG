package fr.ip.model.numeri;

import fr.ip.model.core.*;
import fr.ip.model.util.Facade;
import fr.ip.model.util.Message;

import javax.swing.*;
import java.util.*;
import java.awt.Graphics;

public class NumeriPlayer extends Player {

    private NumeriPawn[] pawns;

    public class NumeriPawn extends Pawn {

        public final int id;

        public NumeriPawn(Player p, int id) {
            super(p);
            this.id = id;
        }

        @Override
        public String toString() {
            return NumeriPlayer.this.toString() + "(" + id + ")";
        }

        
        public void draw(Graphics graphics, int x, int y) {
            super.draw(graphics, x, y);
            graphics.drawString(id + "", x + 25, y + 60);
        }
    }

    public NumeriPlayer (String name) {
        super (name);

        pawns = new NumeriPawn[6];
        for (int i = 0; i < 6; i++)
            pawns[i] = new NumeriPawn(this, i + 1);

        listener().add("play", (Event event) -> {
            int number = new Random().nextInt(5) + 1;
            int[] ids;

            while (true) {
                String ans = Facade.read(this + "\nYou made " + number + "\nSelect your pawns: ");
                if (ans != null) {
                    ids = Arrays.stream(ans.split(","))
                            .map(Integer::parseInt)
                            .mapToInt(Integer::intValue)
                            .distinct()
                            .toArray();
                    if (Arrays.stream(ids).sum() == number) break;
                }
                Facade.show(new Message("Wrong combinaison", JOptionPane.ERROR_MESSAGE));
            }

            for (int id: ids)
                pawns[id - 1].goToCell(pawns[id - 1].getLocation().next(1));

            String show = "";
            for (Pawn pawn: pawns)
                show += pawn.getLocation() + "\n";
            Facade.show(new Message(show, JOptionPane.INFORMATION_MESSAGE));
        });

    }

    public NumeriPawn[] pawns () {
        return pawns;
    }

}
