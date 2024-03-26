package it.polimi.ingsw.Server.Model.Cards.Objectives;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PointsCardObjectiveTest {

    @Test
    void zeroPointObjectiveReturnsZero(){
        CardObjective pco=new PointsCardObjective(0);
        assertEquals(0,pco.calculatePoints(new HashMap<>(), 4));
    }
    @Test
    void fivePointObjectiveReturnsFive(){
        CardObjective pco=new PointsCardObjective(5);
        assertEquals(5,pco.calculatePoints(new HashMap<>(), 4));
    }

    /**
     * must throw CornerOutOfBoundException if covered corners are less than 1 as that move is illegal
     */
    @Test
    void zeroCoveredCornersThrowsRuntimeException() {
        CardObjective pco=new PointsCardObjective(5);
        int coveredCorners = 0;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> pco.calculatePoints(null, coveredCorners));

    }

    /**
     * must throw CornerOutOfBoundException if covered corners more than 4 as that move is illegal
     */
    @Test
    void fiveCoveredCornersThrowsRuntimeException() {
        CardObjective pco=new PointsCardObjective(5);
        int coveredCorners = 5;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> pco.calculatePoints(null, coveredCorners));
    }

    /**
     * Card that automatically assign points can't assign more than 5, else it should throw a runtime exception
     */
    @Test
    void sixPointsInConstructorThrowsRuntimeException() {
        assertThrows(PointsCardObjective.IllegalPointsAssigned.class, () -> new PointsCardObjective(6));
    }


}