package fr.ip.model.core;

public abstract class Pawn {

    Cell cell;

    public boolean goToCell (Cell c) {
            if (cell != null)
                cell.listener().trigger(new Event.CellEvent("leave", cell, this));
            setCell(c);
            if (cell != null)
                cell.listener().trigger(new Event.CellEvent("enter", cell, this));
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
