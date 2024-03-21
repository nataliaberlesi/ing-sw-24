package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

/**
 * Deck of cards, can hold only either gold cards or resource cards
 */
public class Deck {
    /**
     * cards in deck, can be either only resource cards or only gold cards
     */
    private ArrayList<Card> cards;
    /**
     *
     * @return a card from the deck
     */
    public Card draw(){
        //Caution, method under construction WIP
        return cards.get(0);
    }
}
