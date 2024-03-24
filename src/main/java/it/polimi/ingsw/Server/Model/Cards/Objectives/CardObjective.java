package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * CardObjective is the effect that is activated when the card is placed
 * and assigns points accordingly
 */
public interface CardObjective {
    /**
     * calculates the points when card is placed
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return the number of points awarded when card is placed
     */
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners );
}
