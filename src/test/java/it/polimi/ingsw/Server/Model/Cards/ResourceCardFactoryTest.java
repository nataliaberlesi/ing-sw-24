package it.polimi.ingsw.Server.Model.Cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;


class ResourceCardFactoryTest {

    @Test
    void RB7inCreateCardReturnsRB7Card(){
        ResourceCard r= ResourceCardFactory.makeResourceCard("RB7");
        assertEquals("RB7", r.getCardId());
    }

    @Test
    void makeEveryResourceCardReturnsEveryResourceCard(){
        ArrayList<ResourceCard> er=ResourceCardFactory.makeEveryResourceCard();
        assertEquals(40,er.size());
    }
}