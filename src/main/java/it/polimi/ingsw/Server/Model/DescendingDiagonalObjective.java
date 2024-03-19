package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DescendingDiagonalObjective implements Objective{

    private final Symbol symbolOfInterest;
    private HashMap<Coordinates, Symbol> ListOfSymbolsOfInterest=new HashMap<>();

    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {

    }

    @Override
    public int calculateePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
