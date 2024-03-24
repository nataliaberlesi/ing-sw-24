package it.polimi.ingsw.Server.Model.Cards.Objectives;

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
       if(coveredCorners<1){
           throw new CornerOutOfBoundException("IMPOSSIBLE TO COVER LESS THAN 1 CORNER");
       }
       if(coveredCorners>4){
           throw new CornerOutOfBoundException("IMPOSSIBLE TO COVER MORE THAN 4 CORNERS");
       }
       return coveredCorners*POINTS;
    }

    public static class CornerOutOfBoundException extends RuntimeException{
        public CornerOutOfBoundException(String message) {
            super((message));
        }
    }
}
