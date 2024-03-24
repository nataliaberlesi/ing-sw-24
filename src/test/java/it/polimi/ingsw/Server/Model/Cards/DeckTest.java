package it.polimi.ingsw.Server.Model.Cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    /**
     * testing that if deck has no cards returns false
     */
    @Test
    void hasNext() {
        Deck deck=new Deck(new ArrayList<Card>());
        assertFalse(deck.hasNext());
    }
}