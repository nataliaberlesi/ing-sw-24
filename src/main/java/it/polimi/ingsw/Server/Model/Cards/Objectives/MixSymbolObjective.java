package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

/**
 * objective that assigns 3 points per set of 3 different Symbols (SCROLL, INK, FEATHER)
 * visible on the board of the player.
 */
public class MixSymbolObjective implements Objective{
    /**
     * Points per set of 3 different Symbols (SCROLL, INK, FEATHER)
     * visible on the board of the player.
     */
    private static final int POINTS=3;
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
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return number of point earned
     */
    @Override
    public int calculatePoints(ArrayList<Symbol> symbolCounter) {
        return 0;
    }
}
