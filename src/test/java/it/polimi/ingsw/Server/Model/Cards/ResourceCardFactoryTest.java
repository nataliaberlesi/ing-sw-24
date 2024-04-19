package it.polimi.ingsw.Server.Model.Cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;


class ResourceCardFactoryTest {

    @Test
    void RB7inCreateCardReturnsRB7Card(){
        ResourceCard r= ResourceCardFactory.makeResourceCard("RB7");
        assertEquals("RB7", r.getCardID());
    }

    @Test
    void makeEveryResourceCardReturnsEveryResourceCard(){
        ArrayList<String> er=ResourceCardFactory.makeEveryResourceCardID();
        assertEquals(40,er.size());
    }
}