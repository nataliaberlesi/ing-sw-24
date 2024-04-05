package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {

    private static final Symbol[] frontFacingSymbols={Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM};

    private static final ArrayList<Symbol> cardPrerequisites= new ArrayList<>(Arrays.asList(Symbol.LEAF, Symbol.LEAF, Symbol.WOLF));
    private static final HashMap<Symbol, Integer> visibleSymbols=new HashMap<Symbol, Integer>(){{
        put(Symbol.LEAF,2);
        put(Symbol.WOLF,2);
    }};
    @Test
    void sufficientVisibleSymbolsReturnsTrue() {
        Card goldCard=new GoldCard("0",Symbol.WOLF,frontFacingSymbols,null,cardPrerequisites);
        assertTrue(goldCard.checkPrerequisites(visibleSymbols));
    }

    @Test
    void insufficientVisibleSymbolsReturnsFalse(){
        Card goldCard=new GoldCard("0",Symbol.WOLF,frontFacingSymbols,null,cardPrerequisites);
        HashMap<Symbol, Integer>insufficientVisibleSymbols=visibleSymbols;
        insufficientVisibleSymbols.put(Symbol.LEAF, 1);
        assertFalse(goldCard.checkPrerequisites(insufficientVisibleSymbols));
    }

    @Test
    void invalidSymbolsInPrerequisiteReturnsFalse(){
        ArrayList<Symbol> invalidCardPrerequisites=cardPrerequisites;
        invalidCardPrerequisites.add(Symbol.INK);
        assertThrows(InvalidSymbolException.class, ()-> new GoldCard("0",Symbol.WOLF,frontFacingSymbols,null,invalidCardPrerequisites));
    }
}