package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Deck of cards, can hold only either gold cards or resource cards
 */
public class Deck implements Iterator<Card> {
    /**
     * cards in deck, can be either only resource cards or only gold cards
     */
    private final ArrayList<Card> cards;

    /**
     * shuffles cards in deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
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
        return !cards.isEmpty();
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Returns the next card in the deck,
     * if deck is empty returns null
     *
     * @return the next element in the iteration, returns null if cards is empty
     *
     */
    @Override
    public Card next() {
        if(this.hasNext()){
            // getting card that is being drawn
            Card cardBeingDrawn= cards.get(0);
            //removing card from deck
            cards.remove(0);
            //returning card that is being drawn
            return cardBeingDrawn;
        }
        //if deck is empty then it returns null
        return null;
    }
}
