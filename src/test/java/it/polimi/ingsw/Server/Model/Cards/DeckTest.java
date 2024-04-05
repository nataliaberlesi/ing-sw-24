package it.polimi.ingsw.Server.Model.Cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    /**
     * if deck doesn't have any cards, hasNext() must return false
     */
    @Test
    void hasNextEmptyDeckReturnFlase() {
        Deck deck=new Deck(new ArrayList<Card>());
        assertFalse(deck.hasNext());
    }

    /**
     * if deck has cards, hasNext() must return true
     */
    @Test
    void hasNextDeckWithCardsReturnTrue(){
        Card exampleCard=new ResourceCard(null,null,null,null);
        Deck deck=new Deck(new ArrayList<Card>(Collections.singletonList(exampleCard)));
        assertTrue(deck.hasNext());
    }
}