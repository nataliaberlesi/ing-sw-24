package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Cards.OccupiedCoordinateException;
import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;

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
    private final static int POINTS=2;

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
     * if symbols of interest are leafs or butterflies (AKA green or purple cards) then in order to award points this method is called
     * to find descending diagonal patterns
     * @return 2*number of descending diagonal patterns
     */
    private int descendingDiagonalPatternPointCalculator(){

        return 0;
    }

    /**
     * if symbols of interest are mushrooms or wolves (AKA red or blue cards) then in order to award points this method is called
     * to find incrementing diagonal patterns
     * @return 2*number of incrementing diagonal patterns
     */
    private int incrementingDiagonalPatternPointCalculator(){
        return 0;
    }
    /**
     * Symbol counter is ignored
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter) {
        if(symbolOfInterest.equals(Symbol.MUSHROOM) || symbolOfInterest.equals(Symbol.WOLF)){
            return incrementingDiagonalPatternPointCalculator();
        }
        return descendingDiagonalPatternPointCalculator();

    }


}
