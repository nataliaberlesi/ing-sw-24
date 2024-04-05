package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {

    private static Symbol[] frontFacingSymbols={Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM};
    @Test
    void frontFacingCardReturnsFrontFacingCorners(){
        Card resourcecard=new ResourceCard("0",Symbol.MUSHROOM,frontFacingSymbols,null);
        resourcecard.flipCard();
        assertEquals(frontFacingSymbols,resourcecard.getVisibleCorners());
    }

    @Test
    void backFacingCardReturnsBackFacingCorners(){
        Card resourcecard=new ResourceCard("0",Symbol.MUSHROOM,frontFacingSymbols,null);
        assertEquals(resourcecard.getBackCorners(),resourcecard.getVisibleCorners());
    }

    @Test
    void nullCardIdThrowsRuntimeException(){
        assertThrows(RuntimeException.class,()->new ResourceCard(null,Symbol.MUSHROOM,frontFacingSymbols,null));
    }

    @Test
    void wrongBackSymbolThrowsRuntimeException(){
        assertThrows(RuntimeException.class,()->new ResourceCard("0",Symbol.INK,frontFacingSymbols,null));
    }
}