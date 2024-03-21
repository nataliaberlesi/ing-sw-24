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
     * card on top, must always be viewed as facing down
     */
    private Card topCard;

    /**
     * helper function that replaces a top card once it is drawn
     */
    private void replaceTopCard(){

    }

    /**
     *
     * @return top card
     */
    public Card drawTopCard(){
        //Caution, method under construction
        return topCard;
    }
}
