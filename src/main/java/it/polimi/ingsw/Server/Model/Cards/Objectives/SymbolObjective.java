package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * Counts the number of occurrences of a specific symbol on the board and assigns points accordingly
 */
public class SymbolObjective implements CardObjective, Objective {

    private final String objectiveID;

    /**
     * points assigned for each number of occurrences of a symbol on the board
     */
    private final int points;
    /**
     * symbol that will be counted
     */
    private final Symbol symbolOfInterest;
    /**
     * number of symbols equal to symbolOfInterest that are visible on the board
     */
    private final int numberOfOccurrences;

    /**
     * constructor used for objective
     * @param symbolOfInterest symbol that will be counted
     * @throws InvalidSymbolException if a FULL or BLANK symbol is passed, since no cards that count those symbols to assign points exists in the game
     */
    public SymbolObjective(String objectiveID, Symbol symbolOfInterest) throws InvalidSymbolException{
        this.points=2;
        this.objectiveID = objectiveID;
        if(SymbolController.isEmptySymbol(symbolOfInterest)){
            throw new InvalidSymbolException(symbolOfInterest+" can't be used for SymbolObjective");
        }
        this.symbolOfInterest = symbolOfInterest;
        int numberOfOccurrences= 3;
        if(symbolOfInterest.equals(Symbol.SCROLL)||symbolOfInterest.equals(Symbol.INK)||symbolOfInterest.equals(Symbol.FEATHER)){
            numberOfOccurrences=2;
        }
        this.numberOfOccurrences=numberOfOccurrences;
    }

    /**
     * constructor used for CardObjective
     * @param symbolOfInterest symbol that will be counted
     * @throws InvalidSymbolException if symbol is not a feather, ink or scroll symbol, as those are the only symbols that earn points for gold cards
     */
    public SymbolObjective(Symbol symbolOfInterest) throws InvalidSymbolException{
        this.points=1;
        this.objectiveID="";
        if(SymbolController.isEmptySymbol(symbolOfInterest) || SymbolController.isNotGoldenSymbol(symbolOfInterest)){
            throw new InvalidSymbolException(symbolOfInterest+" can't be used for SymbolObjective");
        }
        this.symbolOfInterest = symbolOfInterest;
        this.numberOfOccurrences= 1;
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
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) throws RuntimeException{
        //a card must cover at least one corner when it is placed, it is illegal to do otherwise
        if(coveredCorners<1){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER LESS THAN 1 CORNER");
        }
        //a corner can't cover more than 4 corners, such a placement should be impossible in game
        if(coveredCorners>4){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER MORE THAN 4 CORNERS");
        }
        if(SymbolController.isNotGoldenSymbol(symbolOfInterest)){
            throw new InvalidSymbolException(symbolOfInterest+" can't be a symbol of interest of a golden card");
        }
        int pointsEarnedByCard=0;
        if(symbolCounter.get(symbolOfInterest)!=null){
            pointsEarnedByCard=symbolCounter.get(symbolOfInterest);
        }
        return pointsEarnedByCard;
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
        int numberOfVisibleVisibleSymbols=0;
        if(symbolCounter.get(symbolOfInterest)!=null){
            numberOfVisibleVisibleSymbols=symbolCounter.get(symbolOfInterest);
        }
        int numberOfSets=numberOfVisibleVisibleSymbols/numberOfOccurrences;
        return points*numberOfSets;
    }

    public Symbol getSymbol() {
        return symbolOfInterest;
    }
}
