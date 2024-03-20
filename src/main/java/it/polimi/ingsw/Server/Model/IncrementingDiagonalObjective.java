package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class IncrementingDiagonalObjective implements Objective{

    /**
     * number of points assigned for each time descending diagonal pattern of a certain color is found
     *
     */
    private final static int POINTS=2;

    /**
     * symbolOfInterest indicates the backSymbol of the cards that will be checked
     * the back symbol is indicator of the color of the card
     */
    private final Symbol symbolOfInterest;

    /**
     * list of coordinates that house cards of the color of interest
     */
    private ArrayList<Coordinates> ListOfCoordinatessOfInterest=new ArrayList<>();

    /**
     *
     * @param symbolOfInterest indicates the backSymbol of the cards that will be checked
     */
    public IncrementingDiagonalObjective(Symbol symbolOfInterest) {
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
     * symbolCounter is ignored
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculateePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
