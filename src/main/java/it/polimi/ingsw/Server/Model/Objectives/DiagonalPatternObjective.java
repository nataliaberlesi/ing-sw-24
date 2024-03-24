package it.polimi.ingsw.Server.Model.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

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
    private ArrayList<Coordinates> ListOfCoordinatesOfInterest=new ArrayList<>();

    /**
     *
     * @param symbolOfInterest is the symbol that will be used to filter the cards that will be added
     *                         to the list of coordinates
     */
    public DiagonalPatternObjective(Symbol symbolOfInterest) {
        this.symbolOfInterest = symbolOfInterest;
    }


    /**
     * updates class regarding the color and coordinates of a card that is being placed
     *
     * @param cardBackSymbol symbol the card that is being placed
     * @param coordinates    coordinates of the card that is being placed
     */
    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {

    }

    /**
     * Symbol counter is ignored
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculatePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
