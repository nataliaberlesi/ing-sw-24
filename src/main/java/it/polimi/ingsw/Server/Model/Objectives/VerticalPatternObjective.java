package it.polimi.ingsw.Server.Model.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Objective;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Assigns 3 points for each occurrence of a vertical pattern on the board at the end of a game
 */
public abstract class VerticalPatternObjective implements Objective {

    /**
     * number of points assigned for each occurrence of a vertical pattern
     */
    private static final int POINTS = 3;

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
    private ArrayList<Coordinates> ListOfOutOfLineSymbols = new ArrayList<>();

    /**
     * map of the cards that form the vertical column in the pattern
     */
    private HashMap<Coordinates, Symbol> ListOfSymbolsOfInterest = new HashMap<>();

    /**
     *
     * @param symbolOfInterest symbol of the cards that form the vertical column of the pattern
     * @param outOfLineSymbol symbol of the card that deviates from the column
     */
    protected VerticalPatternObjective(Symbol symbolOfInterest, Symbol outOfLineSymbol) {
        this.symbolOfInterest = symbolOfInterest;
        this.outOfLineSymbol = outOfLineSymbol;
    }

    /**
     * checks weather there is a vertical pattern and deletes the coordinates from the hashmap if found
     * @param bottomCoordinates coordinates of the card on the bottom of the vertical column
     * @return true if pattern is found
     */
    private boolean checkVertical(Coordinates bottomCoordinates){
        return false;
    }

}
