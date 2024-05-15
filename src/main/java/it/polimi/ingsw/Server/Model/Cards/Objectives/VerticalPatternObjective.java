package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Assigns 3 points for each occurrence of a vertical pattern on the board at the end of a game
 */
public class VerticalPatternObjective implements Objective {

    /**
     * number of points assigned for each occurrence of a vertical pattern
     */
    private static final int points = 3;

    /**
     * symbol of the cards that form the vertical column of the pattern
     */
    private final Symbol symbolOfInterest;

    /**
     * symbol of the card that deviates from the column
     */
    private final Symbol outOfLineSymbol;

    /**
     * list of the coordinates of the cards that deviate from the column
     */
    private final ArrayList<Coordinates> listOfOutOfLineSymbols = new ArrayList<>();

    /**
     * map of the cards that form the vertical column in the pattern
     */
    // can be substituted with HashSet, must override HashCode() in Coordinates first
    private final ArrayList<Coordinates> listOfVerticalSymbols = new ArrayList<>();

    /**
     *
     * @param symbolOfInterest symbol of the cards that form the vertical column of the pattern
     */
    public VerticalPatternObjective(Symbol symbolOfInterest) throws InvalidSymbolException {
        this.symbolOfInterest = symbolOfInterest;
        switch (symbolOfInterest){
            case WOLF -> this.outOfLineSymbol=Symbol.MUSHROOM;
            case LEAF -> this.outOfLineSymbol=Symbol.BUTTERFLY;
            case MUSHROOM -> this.outOfLineSymbol=Symbol.LEAF;
            case BUTTERFLY -> this.outOfLineSymbol=Symbol.WOLF;
            default -> throw new InvalidSymbolException(symbolOfInterest +" can't be on the center back of a card");
        }
    }

    /**
     * checks weather there is a vertical pattern and deletes the coordinates from the hashmap if found
     * @param bottomCoordinates coordinates of the card on the bottom of the vertical column
     * @return true if pattern is found
     */
    private boolean checkVertical(Coordinates bottomCoordinates){
        Coordinates topCoordinates;
        if(listOfVerticalSymbols.contains(bottomCoordinates)){
            topCoordinates=new Coordinates(bottomCoordinates.getX(),bottomCoordinates.getY()+2);
            if(listOfVerticalSymbols.contains(topCoordinates)){
                listOfVerticalSymbols.remove(topCoordinates);
                listOfVerticalSymbols.remove(bottomCoordinates);
                return true;
            }
        }
        return false;
    }

    /**
     * updates class regarding the color and coordinates of a card that is being placed
     *
     * @param cardBackSymbol symbol the card that is being placed
     * @param coordinates    coordinates of the card that is being placed
     */
    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {
            if(cardBackSymbol== symbolOfInterest){
                listOfVerticalSymbols.add(coordinates);
            }
            else if(cardBackSymbol==outOfLineSymbol){
                listOfOutOfLineSymbols.add(coordinates);
            }
    }

    /**
     *        M
     *        M
     *          L
     *    3 points
     * @return number of patterns like the one shown above
     */
    private int calculateRedVerticalPatternOccurrences(){
        int numberOfOccurrences=0;
        for(Coordinates currentCo: listOfOutOfLineSymbols){
            Coordinates bottomOfColumn= CornerCoordinatesCalculator.cornerCoordinates(currentCo, 1);
            if(checkVertical(bottomOfColumn)){
                numberOfOccurrences++;
            }
        }
        return numberOfOccurrences;
    }

    /**
     *        L
     *        L
     *      B
     *    3 points
     * @return number of patterns like the one shown above
     */
    private int calculateGreenVerticalPatternOccurrences(){
        int numberOfOccurrences=0;
        for(Coordinates currentCo: listOfOutOfLineSymbols){
            Coordinates bottomOfColumn= CornerCoordinatesCalculator.cornerCoordinates(currentCo, 0);
            if(checkVertical(bottomOfColumn)){
                numberOfOccurrences++;
            }
        }
        return numberOfOccurrences;
    }

    /**
     *          M
     *        W
     *        W
     *    3 points
     * @return number of patterns like the one shown above
     */
    private int calculateBlueVerticalPatternOccurrences(){
        int numberOfOccurrences=0;
        for(Coordinates currentCo: listOfOutOfLineSymbols){
            Coordinates bottomOfColumn= CornerCoordinatesCalculator.cornerCoordinatesShiftedDown(currentCo,"left");
            if(checkVertical(bottomOfColumn)){
                numberOfOccurrences++;
            }
        }
        return numberOfOccurrences;
    }

    /**
     *      W
     *        B
     *        B
     *    3 points
     * @return number of patterns like the one shown above
     */
    private int calculatePurpleVerticalPatternOccurrences(){
        int numberOfOccurrences=0;
        for(Coordinates currentCo: listOfOutOfLineSymbols){
            Coordinates bottomOfColumn= CornerCoordinatesCalculator.cornerCoordinatesShiftedDown(currentCo,"right");
            if(checkVertical(bottomOfColumn)){
                numberOfOccurrences++;
            }
        }
        return numberOfOccurrences;
    }

    /**
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter) {
        listOfOutOfLineSymbols.sort(new CoordinatesComparator());
        int numberOfOccurrences;
        switch (symbolOfInterest){
            case WOLF -> numberOfOccurrences=calculateBlueVerticalPatternOccurrences();
            case LEAF -> numberOfOccurrences=calculateGreenVerticalPatternOccurrences();
            case MUSHROOM -> numberOfOccurrences=calculateRedVerticalPatternOccurrences();
            case BUTTERFLY -> numberOfOccurrences=calculatePurpleVerticalPatternOccurrences();
            //it should be impossible to call this exception
            default -> throw new InvalidSymbolException(symbolOfInterest +" can't be on the center back of a card");
        }
        return points *numberOfOccurrences;
    }

    public Symbol getSymbol() {
        return symbolOfInterest;
    }
}
