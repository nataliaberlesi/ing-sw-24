package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * Objective that assigns points relative to how many corners are covered when card is placed.
 * 2 points are assigned for each covered corner when the card is placed.
 * At least 1 corner and no more than 4 must be covered when card is placed, otherwise a CornerOutOfBoundException
 * exception is thrown.
 *
 */
public class CornerCardObjective implements CardObjective {

    /**
     * points assigned per covered corner
     */
    private final static int POINTS=2;

    /**
     *
     * symbolCounter is ignored in this method
     *
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return 2 points for each corner that is covered when card is placed
     * @throws CornerOutOfBoundException if coveredCorners are less than 1 or more than 4 as such a move is illegal
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) throws CornerOutOfBoundException{
        //a card must cover at least one corner when it is placed, it is illegal to do otherwise
       if(coveredCorners<1){
           throw new CornerOutOfBoundException("IMPOSSIBLE TO COVER LESS THAN 1 CORNER");
       }
       //a corner can't cover more than 4 corners, such a placement should be impossible in game
       if(coveredCorners>4){
           throw new CornerOutOfBoundException("IMPOSSIBLE TO COVER MORE THAN 4 CORNERS");
       }
       //returns two points per corner covered during card placement
       return coveredCorners*POINTS;
    }

    /**
     * exception thrown when an invalid amount of corners is passed in calculatePoints method
     */
    public static class CornerOutOfBoundException extends RuntimeException{
        public CornerOutOfBoundException(String message) {
            super((message));
        }
    }
}
