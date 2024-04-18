package it.polimi.ingsw.Server.Model.Cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class GoldCardFactoryTest {

    @Test
    void GB7inCreateCardReturnsGB7Card(){
        ResourceCard r= GoldCardFactory.makeGoldCard("GB7");
        assertEquals("GB7", r.getCardId());
    }

    @Test
    void makeEveryResourceCardReturnsEveryResourceCard(){
        ArrayList<ResourceCard> er=GoldCardFactory.makeEveryGoldCard();
        assertEquals(40,er.size());
    }

}