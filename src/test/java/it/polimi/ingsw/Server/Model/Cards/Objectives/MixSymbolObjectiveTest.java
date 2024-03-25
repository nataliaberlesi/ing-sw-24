package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MixSymbolObjectiveTest {
    private final MixSymbolObjective mso= new MixSymbolObjective();

    private HashMap<Symbol,Integer> fillHashMapThatWillEarnPoints(){
        HashMap<Symbol,Integer> symbolCounter=new HashMap<>();
        symbolCounter.put(Symbol.INK,4);
        symbolCounter.put(Symbol.FEATHER,5);
        symbolCounter.put(Symbol.SCROLL,9);
        return symbolCounter;
    }
    private HashMap<Symbol,Integer> fillHashMapThatWontEarnPoints(){
        HashMap<Symbol,Integer> symbolCounter=new HashMap<>();
        symbolCounter.put(Symbol.INK,5);
        symbolCounter.put(Symbol.FEATHER,2);
        symbolCounter.put(Symbol.SCROLL,0);
        symbolCounter.put(Symbol.WOLF,9);
        symbolCounter.put(Symbol.MUSHROOM,9);
        return symbolCounter;
    }

    @Test
    void fourSetsOfThreeShouldReturnTwelve(){
        HashMap<Symbol,Integer> symbolCounter=fillHashMapThatWillEarnPoints();
        assertEquals(12,mso.calculatePoints(symbolCounter));
    }
    @Test
    void zeroSetsOfThreeShouldReturnZero(){
        HashMap<Symbol,Integer> symbolCounter=fillHashMapThatWontEarnPoints();
        assertEquals(0,mso.calculatePoints(symbolCounter));
    }
}