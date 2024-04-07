package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.ResourceCard;

/**
 * This is the players hand, it can never have more than three cards,
 * at the end of each turn the hand must contain three cards.
 */
public class Hand {
    /**
     * The cards that are in a player hand, must be 3 cards at the end of each turn unless there are no more cards to draw
     */
    private final ResourceCard[] cards = new ResourceCard[3];

    /**
     *
     * @param resourceCard that is being drawn and will be added to hand
     * @throws RuntimeException if hand is already full
     */
    public void placeCardInHand(ResourceCard resourceCard) throws RuntimeException{
        for(int i=0; i<3;i++){
            if(cards[i]==null){
                cards[i]=resourceCard;
            }
        }
        throw new RuntimeException("hand is already full");
    }

    /**
     *
     * @param cardIndex is the index of the card that will be removed from hand
     *                  to later be placed on the board
     * @return card that is in cards[cardIndex]
     * @throws RuntimeException if a card that doesn't exist is being taken from hand
     */
    public Card getCardFromHand(int cardIndex) throws RuntimeException{
        ResourceCard cardBeingTakenFromHand=cards[cardIndex];
        if(cardBeingTakenFromHand==null){
            throw new RuntimeException("no card in this position in this hand");
        }
        cards[cardIndex]=null;
        return cardBeingTakenFromHand;
    }
}
