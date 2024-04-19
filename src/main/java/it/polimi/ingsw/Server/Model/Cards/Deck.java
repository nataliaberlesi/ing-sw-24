package it.polimi.ingsw.Server.Model.Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Deck of cards, can hold only either gold cards or resource cards
 */
public class Deck implements Iterator<String> {

    /**
     * cards in deck, can be either only resource cards or only gold cards
     */
    private final ArrayList<String> resourceCards;

    /**
     * shuffles cards in deck
     */
    public void shuffle(){
        Collections.shuffle(resourceCards);
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
        return !resourceCards.isEmpty();
    }

    /**
     *
     * @param cards that compose the deck
     */
    public Deck(ArrayList<String> cards) {
        this.resourceCards = cards;
    }

    /**
     * Returns the next card in the deck,
     * if deck is empty returns null
     *
     * @return the next element in the iteration, returns null if cards is empty
     *
     */
    public String next() {
        if(this.hasNext()){
            // getting card that is being drawn
            String cardBeingDrawn= resourceCards.getFirst();
            //removing card from deck
            resourceCards.removeFirst();
            //returning card that is being drawn
            return cardBeingDrawn;
        }
        //if deck is empty then it returns null
        return null;
    }

}
