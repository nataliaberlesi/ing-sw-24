package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.PointsCardObjective;
import it.polimi.ingsw.Server.Model.Symbol;
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
        Symbol[] frontCornerSymbols ={Symbol.BLANK, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM};
        ResourceCard exampleCard=new ResourceCard("0",Symbol.WOLF,frontCornerSymbols,new PointsCardObjective(2));
        Deck deck=new Deck(new ArrayList<>(Collections.singletonList(exampleCard)));
        assertTrue(deck.hasNext());
    }
}