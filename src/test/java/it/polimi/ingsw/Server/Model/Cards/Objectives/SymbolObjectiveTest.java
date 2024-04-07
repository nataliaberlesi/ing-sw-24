package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SymbolObjectiveTest {
    private HashMap<Symbol,Integer> fillHashMapThatWillEarnTwoPointsForInk(){
        HashMap<Symbol,Integer> symbolCounter=new HashMap<>();
        symbolCounter.put(Symbol.INK,5);
        symbolCounter.put(Symbol.FEATHER,5);
        symbolCounter.put(Symbol.SCROLL,9);
        return symbolCounter;
    }
    private HashMap<Symbol,Integer> fillHashMapThatWontEarnPointsForButterfly(){
        HashMap<Symbol,Integer> symbolCounter=new HashMap<>();
        symbolCounter.put(Symbol.INK,5);
        symbolCounter.put(Symbol.FEATHER,1);
        symbolCounter.put(Symbol.BUTTERFLY,2);
        symbolCounter.put(Symbol.WOLF,9);
        symbolCounter.put(Symbol.MUSHROOM,6);
        return symbolCounter;
    }
    private HashMap<Symbol,Integer> fillHashMapThatWontEarnPointsForInk(){
        HashMap<Symbol,Integer> symbolCounter=new HashMap<>();
        symbolCounter.put(Symbol.INK,0);
        symbolCounter.put(Symbol.FEATHER,1);
        symbolCounter.put(Symbol.BUTTERFLY,2);
        symbolCounter.put(Symbol.WOLF,9);
        symbolCounter.put(Symbol.MUSHROOM,6);
        return symbolCounter;
    }
    @Test
    void fiveInkSymbolsReturnsFIve(){
        CardObjective so=new SymbolObjective(Symbol.INK);
        assertEquals(5,so.calculatePoints(fillHashMapThatWillEarnTwoPointsForInk(), 4));
    }

    @Test
    void zeroInkReturnsZero(){
        CardObjective so=new SymbolObjective(Symbol.INK);
        assertEquals(0,so.calculatePoints(fillHashMapThatWontEarnPointsForInk(), 4));
    }
    @Test
    void twoSetsOfSymbolsReturnsFourMushroom(){
        Objective so=new SymbolObjective(Symbol.MUSHROOM);
        assertEquals(4,so.calculatePoints(fillHashMapThatWontEarnPointsForButterfly()));
    }

    @Test
    void zeroSetsOfSymbolsReturnsZeroFeather(){
        Objective so=new SymbolObjective(Symbol.FEATHER);
        assertEquals(0,so.calculatePoints(fillHashMapThatWontEarnPointsForButterfly()));
    }
    /**
     * must throw CornerOutOfBoundException if covered corners are less than 1 as that move is illegal
     */
    @Test
    void zeroCoveredCornersThrowsRuntimeException() {
        CardObjective so=new SymbolObjective(Symbol.INK);
        int coveredCorners = 0;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> so.calculatePoints(null, coveredCorners));

    }

    /**
     * must throw CornerOutOfBoundException if covered corners more than 4 as that move is illegal
     */
    @Test
    void fiveCoveredCornersThrowsRuntimeException() {
        CardObjective so=new SymbolObjective(Symbol.INK);
        int coveredCorners = 5;
        assertThrows(CornerCardObjective.CornerOutOfBoundException.class, () -> so.calculatePoints(null, coveredCorners));
    }

    @Test
    void emptySymbolWillThrowExceptionInConstructor(){
        assertThrows(InvalidSymbolException.class, () -> new SymbolObjective(Symbol.BLANK));
    }


}