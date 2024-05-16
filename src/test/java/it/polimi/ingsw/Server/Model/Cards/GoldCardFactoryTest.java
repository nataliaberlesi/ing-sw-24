package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

class GoldCardFactoryTest {

    @Test
    void GB7inCreateCardReturnsGB7Card(){
        ResourceCard r= GoldCardFactory.makeGoldCard("GB7");
        assertEquals("GB7", r.getCardID());
    }

    @Test
    void makeEveryResourceCardReturnsEveryResourceCard(){
        ArrayList<ResourceCard> er=GoldCardFactory.makeEveryGoldCard();
        assertEquals(40,er.size());
    }

    @Test void makeGoldCardReturnsGoldCard(){
        GoldCardFactory.makeGoldCard("GG0");
        GoldCardFactory.makeGoldCard("GG1");
        GoldCardFactory.makeGoldCard("GG2");
        GoldCardFactory.makeGoldCard("GG3");
        GoldCardFactory.makeGoldCard("GG4");
        GoldCardFactory.makeGoldCard("GG5");
        GoldCardFactory.makeGoldCard("GG6");
        ResourceCard gc=GoldCardFactory.makeGoldCard("GG7");
        assertEquals(new ArrayList<>(Arrays.asList(Symbol.LEAF, Symbol.LEAF, Symbol.LEAF)), gc.getPrerequisites());
    }



}