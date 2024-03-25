package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Cards.OccupiedCoordinateException;
import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalPatternObjectiveTest {

    private static final ArrayList<Coordinates> descendingCoordinates=new ArrayList<>(Arrays.asList(new Coordinates(), new Coordinates(1,-1),new Coordinates(2,-2),new Coordinates(3,-3), new Coordinates(4,-4)));
    private static final ArrayList<Coordinates> incrementingCoordinates=new ArrayList<>(Arrays.asList(new Coordinates(), new Coordinates(1,1),new Coordinates(2,2),new Coordinates(3,3),new Coordinates(4,4)));
    private DiagonalPatternObjective autoFillDescendingPattern(Symbol symbolOfInterest){
        DiagonalPatternObjective dpo=new DiagonalPatternObjective(symbolOfInterest);
        for(Coordinates coordinates: descendingCoordinates){
            dpo.updateObjective(symbolOfInterest, coordinates);
        }
        return dpo;
    }

    private DiagonalPatternObjective autoFillIncrementingPattern(Symbol symbolOfInterest){
        DiagonalPatternObjective dpo=new DiagonalPatternObjective(symbolOfInterest);
        for(Coordinates coordinates: incrementingCoordinates){
            dpo.updateObjective(symbolOfInterest, coordinates);
        }
        return dpo;
    }

    /**
     *  L
     *    L
     *      L
     *        L
     *          L   = 2 points
     */
    @Test
    void calculatePointsDescendingPatternGreen() {
        Objective descendingPattern= this.autoFillDescendingPattern(Symbol.LEAF);
        assertEquals(2,descendingPattern.calculatePoints(null));
    }

    /**
     *  B
     *    B
     *      B
     *        B
     *          B   = 2 points
     */
    @Test
    void calculatePointsDescendingPatternPurple() {
        Objective descendingPattern= this.autoFillDescendingPattern(Symbol.BUTTERFLY);
        assertEquals(2,descendingPattern.calculatePoints(null));
    }

    /**
     *          M     = 2 points
     *        M
     *      M
     *    M
     *  M
     */
    @Test
    void calculatePointsIncrementingPatternRed() {
        Objective incrementingPattern= this.autoFillIncrementingPattern(Symbol.MUSHROOM);
        assertEquals(2,incrementingPattern.calculatePoints(null));
    }

    /**
     *          W   = 2 points
     *        W
     *      W
     *    W
     *  W
     */
    @Test
    void calculatePointsIncrementingPatternBlue() {
        Objective incrementingPattern= this.autoFillIncrementingPattern(Symbol.WOLF);
        assertEquals(2,incrementingPattern.calculatePoints(null));
    }

    /**
     *            M   = 4 points
     *          M
     *        M
     *      M
     *    M
     *  M
     *
     */
    @Test
    void calculatePointsExtendedIncrementingPatternRed() {
        Objective incrementingPattern= this.autoFillIncrementingPattern(Symbol.MUSHROOM);
        incrementingPattern.updateObjective(Symbol.MUSHROOM, new Coordinates(5,5));
        assertEquals(4,incrementingPattern.calculatePoints(null));
    }

    /**
     *  L
     *    L
     *      L
     *        L
     *          L
     *            L   = 4 points
     */
    @Test
    void calculatePointsExtendedDescendingPatternGreen() {
        Objective descendingPattern= this.autoFillDescendingPattern(Symbol.LEAF);
        descendingPattern.updateObjective(Symbol.LEAF, new Coordinates(5,-5));
        assertEquals(4,descendingPattern.calculatePoints(null));
    }
    /**
     *          M           = 2 points
     *        M     W
     *      M     W
     *    M     W
     *  M     W
     *      W
     *    W
     */
    @Test
    void calculatePointsHeterogeneousIncrementingPoints(){
        Objective incrementingPattern= this.autoFillIncrementingPattern(Symbol.MUSHROOM);
        incrementingPattern.updateObjective(Symbol.WOLF,new Coordinates(0,2));
        incrementingPattern.updateObjective(Symbol.WOLF,new Coordinates(1,3));
        incrementingPattern.updateObjective(Symbol.WOLF,new Coordinates(2,4));
        incrementingPattern.updateObjective(Symbol.WOLF,new Coordinates(3,5));
        incrementingPattern.updateObjective(Symbol.WOLF,new Coordinates(4,6));
        assertEquals(2,incrementingPattern.calculatePoints(null));
    }
    /**
     * if the same symbol and coordinates are passed twice, meaning that two cards where placed in the same spot,
     * updateObjective should throw RuntimeException
     * in this example the coordinates [0,0] are passed twice
     *
     */
    @Test
    void updateObjectiveCoordinatesConflict() {
        Objective diagonalObjective = this.autoFillDescendingPattern(Symbol.LEAF);
        assertThrows(OccupiedCoordinateException.class, () -> diagonalObjective.updateObjective(Symbol.LEAF, new Coordinates()));
    }

    /**
     * if a symbol that can't be on the center back of a card is passed in constructor, then it must throw a runtimeException
     */
    @Test
    void constructorInvalidParameterThrowException() {
        assertThrows(InvalidSymbolException.class, () -> new DiagonalPatternObjective(Symbol.SCROLL));
    }

}