package fr.flin.model.core;

public abstract class Pawn {

    Cell cell;

    public boolean goToCell (Cell c) {
        if (cell != null) cell.move(this, c);
        setCell(c);
        return true;
    }

    public boolean setCell (Cell c) {
        cell = c;
        return true;
    }

    public abstract Player getPlayer ();

    public Cell getLocation () {
        return cell;
    }

    public String toString () {
        return "pawn " + cell.id;
    }

}
