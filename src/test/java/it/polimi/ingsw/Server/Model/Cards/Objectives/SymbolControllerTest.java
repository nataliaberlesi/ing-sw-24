package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SymbolControllerTest {
    private final static ArrayList<Symbol> listOfBackSymbols=new ArrayList<>(Arrays.asList(Symbol.LEAF, Symbol.BUTTERFLY, Symbol.WOLF, Symbol.MUSHROOM));

    @Test
    void emptySymbolArrayReturnsTrueWhenCheckingForBackSymbols(){
        assertTrue(SymbolController.containsOnlyBackSymbols(new ArrayList<>()));
    }

    @Test
    void listOfBackSymbolsReturnsTrueWhenCheckingForBackSymbols(){
        assertTrue(SymbolController.containsOnlyBackSymbols(listOfBackSymbols));
    }

    @Test
    void listContainingEmptySymbolsReturnsFalseWhenCheckingForBackSymbols(){
        ArrayList<Symbol> listContainingEmptySymbols=new ArrayList<>(listOfBackSymbols);
        listContainingEmptySymbols.add(Symbol.FULL);
        assertFalse(SymbolController.containsOnlyBackSymbols(listContainingEmptySymbols));
    }

    @Test
    void listContainingGOldenSymbolsReturnsFalseWhenCheckingForBackSymbols(){
        ArrayList<Symbol> listContainingGoldenSymbols=new ArrayList<>(listOfBackSymbols);
        listContainingGoldenSymbols.add(Symbol.INK);
        assertFalse(SymbolController.containsOnlyBackSymbols(listContainingGoldenSymbols));
    }





}