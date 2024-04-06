package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {

    private static final Symbol[] frontCornerSymbols ={Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM};


    @Test
    void frontFacingCardReturnsFrontFacingCorners(){
        Card resourcecard=new ResourceCard("0",Symbol.MUSHROOM, frontCornerSymbols,null);
        resourcecard.flipCard();
        assertEquals(frontCornerSymbols,resourcecard.getVisibleCorners());
    }

    @Test
    void backFacingCardReturnsBackFacingCorners(){
        Card resourcecard=new ResourceCard("0",Symbol.MUSHROOM, frontCornerSymbols,null);
        assertEquals(resourcecard.getBackCorners(),resourcecard.getVisibleCorners());
    }

    @Test
    void nullCardIdThrowsRuntimeException(){
        assertThrows(IllegalArgumentException.class,()->new ResourceCard(null,Symbol.MUSHROOM, frontCornerSymbols,null));
    }

    @Test
    void wrongBackSymbolThrowsRuntimeException(){
        assertThrows(InvalidSymbolException.class,()->new ResourceCard("0",Symbol.INK, frontCornerSymbols,null));
    }

    @Test
    void tooManyBackCornersThrowsException(){
        Symbol[] tooManyCorners= {Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM, Symbol.WOLF};
        assertThrows(IllegalArgumentException.class,()->new ResourceCard("0",Symbol.MUSHROOM, tooManyCorners,null));
    }

    @Test
    void tooFewBackCornersThrowsException(){
        Symbol[] tooFewCorners= {Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK};
        assertThrows(IllegalArgumentException.class,()->new ResourceCard("0",Symbol.MUSHROOM, tooFewCorners,null));
    }
}