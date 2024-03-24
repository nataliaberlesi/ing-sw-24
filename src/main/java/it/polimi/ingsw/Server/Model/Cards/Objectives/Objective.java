package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

/**
 * Objective allows updates on the board situation and calculates points at the end of the game
 */
public interface Objective {
    /**
     * updates class regarding the color and coordinates of a card that is being placed
     * @param cardBackSymbol symbol the card that is being placed
     * @param coordinates coordinates of the card that is being placed
     */
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates);

    /**
     *
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    public int calculatePoints(ArrayList<Symbol> symbolCounter);
}
