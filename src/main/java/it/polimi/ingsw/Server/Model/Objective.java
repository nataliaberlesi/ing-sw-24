package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public interface Objective {
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates);

    public int calculateePoints(ArrayList<Symbol> symbolCounter);
}
