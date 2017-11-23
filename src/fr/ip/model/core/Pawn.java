package fr.ip.model.core;

public abstract class Pawn {

    Cell cell;

    public boolean goToCell (Cell c) {
        try {
            if (cell != null)
                cell.listener().trigger(new Event.CellEvent("leave", cell, this));
            setCell(c);
            if (cell != null)
                cell.listener().trigger(new Event.CellEvent("enter", cell, this));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
