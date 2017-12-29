package fr.ip.view.core.components;

import fr.ip.model.core.Pawn;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class CellButton extends JButton {

    private LinkedList<Pawn> pawns;

    public CellButton (String name) {
        super(name);
        pawns = new LinkedList<>();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        String s = "";
        for (Pawn pawn: pawns)
            s += pawn + " ";
        graphics.drawString(s, getWidth() / 2, getHeight() / 2 + 30 );
    }

    public void add (Pawn pawn) {
        pawns.add(pawn);
        setEnabled(false);
    }

    public void remove (Pawn pawn) {
        pawns.remove(pawn);
        if (pawns.size() == 0)
            setEnabled(true);
    }

}
