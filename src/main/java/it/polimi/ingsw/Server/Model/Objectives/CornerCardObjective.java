package it.polimi.ingsw.Server.Model.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * Objective that assigns points relative to how many corners are covered when card is placed
 *
 */
public class CornerCardObjective implements CardObjective {
    /**
     * points assigned per covered corner
     */
    private final static int points=2;

    /**
     *
     * symbolCounter is ignored in this method
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return 2 points for each corner that is covered when card is placed
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }
}
