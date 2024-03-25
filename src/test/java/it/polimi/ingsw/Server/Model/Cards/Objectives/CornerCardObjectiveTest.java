package it.polimi.ingsw.Server.Model.Cards.Objectives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerCardObjectiveTest {
    /**
     * calculatePoints must return 2 if 1 corner is covered
     */
    @Test
    void oneCoveredCornersReturnsTwoInCalculatePoints() {
        CardObjective cornerCardObjective=new CornerCardObjective();
        int coveredCorners=1;
        assertEquals(2, cornerCardObjective.calculatePoints(null, coveredCorners));
    }
    /**
     * calculatePoints must return 6 if 3 corners are covered
     */
    @Test
    void threeCoveredCornersReturnsSixInCalculatePoints() {
        CardObjective cornerCardObjective=new CornerCardObjective();
        int coveredCorners=3;
        assertEquals(6, cornerCardObjective.calculatePoints(null, coveredCorners));
    }

    /**
     * must throw CornerOutOfBoundException if covered corners are less than 1 as that move is illegal
     */
    @Test
    void zeroCoveredCornersThrowsRuntimeException() {
        CardObjective cornerCardObjective = new CornerCardObjective();
        int coveredCorners = 0;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> cornerCardObjective.calculatePoints(null, coveredCorners));

    }

    /**
     * must throw CornerOutOfBoundException if covered corners more than 4 as that move is illegal
     */
    @Test
    void fiveCoveredCornersThrowsRuntimeException() {
        CardObjective cornerCardObjective = new CornerCardObjective();
        int coveredCorners = 5;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> cornerCardObjective.calculatePoints(null, coveredCorners));
    }

}