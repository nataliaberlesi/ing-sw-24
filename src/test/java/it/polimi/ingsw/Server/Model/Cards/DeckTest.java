package it.polimi.ingsw.Server.Model.Cards;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    /**
     * if deck doesn't have any cards, hasNext() must return false
     */
    @Test
    void hasNextEmptyDeckReturnFlase() {
        Deck deck=new Deck(new ArrayList<>());
        assertFalse(deck.hasNext());
    }

    /**
     * if deck has cards, hasNext() must return true
     */
    @Test
    void hasNextDeckWithCardsReturnTrue(){
        Deck deck=new Deck(new ArrayList<>(Collections.singletonList("BB3")));
        assertTrue(deck.hasNext());
    }
}