package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public class SymbolObjetiove implements CardObjective ,Objective{
    private final Symbol symbolOfInterest;
    private final int numberOfOccurrences;

    public SymbolObjetiove(Symbol symbolOfInterest, int numberOfOccurrences) {
        this.symbolOfInterest = symbolOfInterest;
        this.numberOfOccurrences = numberOfOccurrences;
    }

    @Override
    public int calculatePoints(ArrayList<Symbol> symbolCounter, int coveredCorners) {
        return 0;
    }

    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {

    }

    @Override
    public int calculateePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
