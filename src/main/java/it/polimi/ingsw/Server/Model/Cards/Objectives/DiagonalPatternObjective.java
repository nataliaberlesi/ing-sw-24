package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.*;
import it.polimi.ingsw.Server.Model.Cards.OccupiedCoordinateException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  objective that assigns 2 points per diagonal pattern of cards of the same color
 *  on the player's board.
 *  If the objective looks for green or purple cards, only descending diagonal patterns assign points,
 *  red and blu cards only assign points for incrementing diagonal pattern.
 */
public class DiagonalPatternObjective implements Objective {

    /**
     * number of points assigned for each time descending diagonal pattern of a certain color is found
     *
     */
    private final static int points =2;

    /**
     * symbolOfInterest indicates the backSymbol of the cards that won't be filtered out in the updateObjective method
     * the back symbol is an indicator of the card's color
     */
    private final Symbol symbolOfInterest;

    /**
     * contains the list of all coordinates present in the board of the cards that contain the symbol of interest
     */
    private final ArrayList<Coordinates> listOfCoordinatesOfInterest=new ArrayList<>();

    /**
     *
     * @param symbolOfInterest is the symbol that will be used to filter the cards that will be added
     *                         to the list of coordinates
     * @throws InvalidSymbolException if a symbol that can't be on the center back of a card is passed
     */
    public DiagonalPatternObjective(Symbol symbolOfInterest) throws InvalidSymbolException {
        if(validSymbol(symbolOfInterest)) {
            this.symbolOfInterest = symbolOfInterest;
        }
        else {
            throw new InvalidSymbolException("invalid symbol passed in diagonal objective");
        }
    }

    /**
     *
     * @param symbol of the cards that will be checked to see if they form a diagonal pattern
     * @return true if a valid symbol (AKA a symbol that can be on the center back of a card) is passed
     */
    private boolean validSymbol(Symbol symbol) {
        return symbol.equals(Symbol.WOLF) || symbol.equals(Symbol.BUTTERFLY) || symbol.equals(Symbol.MUSHROOM) || symbol.equals(Symbol.LEAF);
    }
    /**
     *
     * @param coordinates of card that has been placed
     * @return true if coordinates are already saved, meaning there is already a card in that spot.
     */
    private boolean occupied(Coordinates coordinates){
        for(Coordinates coordinatesOfInterest: listOfCoordinatesOfInterest){
            if(coordinates.equals(coordinatesOfInterest)){
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
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) throws OccupiedCoordinateException{
        if(cardBackSymbol==this.symbolOfInterest){
            if(occupied(coordinates)){
                throw new OccupiedCoordinateException("Placing two cards in same coordinates, found in diagonal objective");
            }
            this.listOfCoordinatesOfInterest.add(coordinates);
        }
    }

    /**
     * if symbols of interest are leafs or butterflies (AKA green or purple cards) then method assigns points for every descending diagonal pattern,
     * if symbols of interest are wolf or mushroom (AKA red or blue cards) then method assigns points for every incrementing diagonal pattern
     * @return 2*number of diagonal patterns
     */
    private int diagonalPatternPointCalculator(int cornerNumber){
        int occurences=0;
        Coordinates currentCoordinates, rightCoordinates1, rightCoordinates2;
        for(int i=0; i<listOfCoordinatesOfInterest.size();i++){
            currentCoordinates=listOfCoordinatesOfInterest.get(i);
            rightCoordinates1=CornerCoordinatesCalculator.cornerCoordinates(currentCoordinates, cornerNumber);
            if(listOfCoordinatesOfInterest.contains(rightCoordinates1)){
                rightCoordinates2=CornerCoordinatesCalculator.cornerCoordinates(rightCoordinates1, cornerNumber);
                if(listOfCoordinatesOfInterest.contains(rightCoordinates2)){
                    occurences++;
                    listOfCoordinatesOfInterest.remove(currentCoordinates);
                    listOfCoordinatesOfInterest.remove(rightCoordinates1);
                    listOfCoordinatesOfInterest.remove(rightCoordinates2);
                    i--;
                }
            }
        }
        return points *occurences;
    }


    /**
     * Symbol counter is ignored
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter) {
        // Sorting list to make sure maximum points are assigned
        listOfCoordinatesOfInterest.sort(new CoordinatesComparator());
        // type of pattern to look for is determined by symbol of interest.
        if(symbolOfInterest.equals(Symbol.MUSHROOM) || symbolOfInterest.equals(Symbol.WOLF)){
            return diagonalPatternPointCalculator(0);
        }
        return diagonalPatternPointCalculator(3);
    }


    public Symbol getSymbol() {
        return symbolOfInterest;
    }
}
