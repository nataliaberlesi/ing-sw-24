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
    public PointsCardObjective(int points) throws IllegalPointsAssigned{
        if(points>5){
            throw new IllegalPointsAssigned("Card that automatically assign points can't assign more than 5");
        }
        this.points = points;
    }


    /**
     * calculates the points when card is placed
     * all parameters are ignored
     * @param symbolCounter  map containing number of visible occurrences for each symbol
     * @param coveredCorners is the number of corners that are covered by the card when placed
     * @return the number of points awarded when card is placed
     * @throws CornerCardObjective.CornerOutOfBoundException if coveredCorners are less than 1 or more than 4 as such a move is illegal
     */
    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) throws CornerCardObjective.CornerOutOfBoundException {
        //a card must cover at least one corner when it is placed, it is illegal to do otherwise
        if(coveredCorners<1){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER LESS THAN 1 CORNER");
        }
        //a corner can't cover more than 4 corners, such a placement should be impossible in game
        if(coveredCorners>4){
            throw new CornerCardObjective.CornerOutOfBoundException("IMPOSSIBLE TO COVER MORE THAN 4 CORNERS");
        }
        return points;
    }

    public static class IllegalPointsAssigned extends RuntimeException{
        public IllegalPointsAssigned(String message){
            super(message);
        }
    }

    public int getPoints() {
        return points;
    }
}
