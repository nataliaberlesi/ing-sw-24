package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * PointsCardObjective assigns points when a card is placed, no checks necessary.
 */
public class PointsCardObjective implements CardObjective {

    /**
     * points that will be gained when card is placed
     */
    private final int points;

    /**
     *
     * @param points that will be gained when card is placed
     */
    public PointsCardObjective(int points) {
        this.points = points;
    }


    /**
     * calculates the points when card is placed
     * all parameters are ignored
     * @param symbolCounter  map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return the number of points awarded when card is placed
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }
}
