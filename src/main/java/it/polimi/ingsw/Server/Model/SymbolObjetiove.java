package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolObjetiove implements CardObjective ,Objective{
    private final Symbol symbolOfInterest;
    private final int numberOfOccurrences;

    public SymbolObjetiove(Symbol symbolOfInterest, int numberOfOccurrences) {
        this.symbolOfInterest = symbolOfInterest;
        this.numberOfOccurrences = numberOfOccurrences;
    }



    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {
    }

    @Override
    public int calculateePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }

    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }
}
