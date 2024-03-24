package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Card;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Deck of cards, can hold only either gold cards or resource cards
 */
public class Deck implements Iterator<Card> {
    /**
     * cards in deck, can be either only resource cards or only gold cards
     */
    private ArrayList<Card> cards;

    /**
     * shuffles cards in deck
     */
    public void shuffle(){

    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration, returns null if cards is empty
     *
     */
    @Override
    public Card next() {
        return null;
    }
}
