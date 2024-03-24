package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Card;

/**
 * This is the players hand, it can never have more than three cards,
 * at the end of each turn the hand must contain three cards.
 */
public class Hand {
    /**
     * The cards that are in a player hand, must be 3 cards at the end of each turn unless there are no more cards to draw
     */
    private Card[] cards = new Card[3];

    /**
     *
     * @param card that is being drawn and will be added to cards
     */
    public void placeCardInHand(Card card){}

    /**
     *
     * @param cardIndex is the index of the card that will be removed from hand
     *                  to later be placed on the board
     * @return card that is in cards[cardIndex]
     */
    public Card getCardFromHand(int cardIndex){
        //Caution, method under construction... WIP
        return cards[cardIndex];
    }
}
