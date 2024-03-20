package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Counts the number of occurrences of a specific symbol on the board and assigns points accordingly
 */
public class SymbolObjetiove implements CardObjective ,Objective{
    /**
     * points assigned for each number of occurences of a symbol on the board
     */
    private static final int POINTS=2;
    /**
     * symbol that will be counted
     */
    private final Symbol symbolOfInterest;
    /**
     * number of symbols equal to symbolOfInterest that are visible on the board
     */
    private final int numberOfOccurrences;

    /**
     *
     * @param symbolOfInterest symbol that will be counted
     * @param numberOfOccurrences number of symbols equal to symbolOfInterest that are visible on the board
     */
    public SymbolObjetiove(Symbol symbolOfInterest, int numberOfOccurrences) {
        this.symbolOfInterest = symbolOfInterest;
        this.numberOfOccurrences = numberOfOccurrences;
    }


    /**
     * calculates the points when card is placed by calling other method with same name
     *
     * @param symbolCounter  map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return the number of points awarded when card is placed
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }

    /**
     * this method will be ignored
     *
     * @param cardBackSymbol symbol the card that is being placed
     * @param coordinates    coordinates of the card that is being placed
     */
    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {

    }

    /**
     * it will count the number of occurrences of symbolOfInterest on the board and assign points accordingly
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculateePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
