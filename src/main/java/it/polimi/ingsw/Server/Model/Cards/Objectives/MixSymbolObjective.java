package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

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
     * this class ignores all updates
     *
     * @param cardBackSymbol symbol the card that is being placed
     * @param coordinates    coordinates of the card that is being placed
     */
    @Override
    public void updateObjective(Symbol cardBackSymbol, Coordinates coordinates) {

    }

    /**
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return 3 points per set of 3 different Symbols (SCROLL, INK, FEATHER) visible on the board of the player.
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter) {
        int numberOfScrolls=symbolCounter.get(Symbol.SCROLL),
                numberOfFeathers=symbolCounter.get(Symbol.FEATHER),
                numberOfInk= symbolCounter.get(Symbol.INK);
        int numberOfsetsOfThree=Math.min(Math.min(numberOfFeathers,numberOfInk),numberOfScrolls);
        return POINTS*numberOfsetsOfThree;
    }



}
