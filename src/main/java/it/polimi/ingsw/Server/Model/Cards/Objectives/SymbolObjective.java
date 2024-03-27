package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * Counts the number of occurrences of a specific symbol on the board and assigns points accordingly
 */
public class SymbolObjective implements CardObjective, Objective {
    /**
     * points assigned for each number of occurrences of a symbol on the board
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
     */
    public SymbolObjective(Symbol symbolOfInterest) {
        this.symbolOfInterest = symbolOfInterest;
        int numberOfOccurrences= 3;
        if(symbolOfInterest.equals(Symbol.SCROLL)||symbolOfInterest.equals(Symbol.INK)||symbolOfInterest.equals(Symbol.FEATHER)){
            numberOfOccurrences=2;
        }
        this.numberOfOccurrences=numberOfOccurrences;
    }


    /**
     * calculates the points when card is placed by calling other method with same name
     *
     * @param symbolCounter  map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return two points for each set of two or three(depending on symbolOfInterest) visible on the board
     * @throws CornerCardObjective.CornerOutOfBoundException if coveredCorners are less than 1 or more than 4 as such a move is illegal
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        //a card must cover at least one corner when it is placed, it is illegal to do otherwise
        if(coveredCorners<1){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER LESS THAN 1 CORNER");
        }
        //a corner can't cover more than 4 corners, such a placement should be impossible in game
        if(coveredCorners>4){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER MORE THAN 4 CORNERS");
        }
        return this.calculatePoints(symbolCounter);
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
     * @return two points for each set of two or three(depending on symbolOfInterest) visible on the board
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter) {
        int numberOfSets=symbolCounter.get(symbolOfInterest)/numberOfOccurrences;
        return POINTS*numberOfSets;
    }
}
