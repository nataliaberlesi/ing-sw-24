package it.polimi.ingsw.Server.Model;



/**
 * This is the players hand, it can never have more than three cards,
 * at the end of each turn the hand must contain three cards.
 */
public class Hand {
    /**
     * The cards that are in a player hand, must be 3 cards at the end of each turn unless there are no more cards to draw
     */
    private final String[] cards = new String[3];



    /**
     *
     * @param resourceCard that is being drawn and will be added to hand
     * @throws RuntimeException if hand is already full
     */
    public void placeCardInHand(String resourceCard) throws RuntimeException{
        for(int i=0; i<3;i++){
            if(cards[i]==null){
                cards[i]=resourceCard;
                return;
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
    public String getCardFromHand(int cardIndex) throws RuntimeException{
        String cardBeingTakenFromHand=showCardInHand(cardIndex);
        cards[cardIndex]=null;
        return cardBeingTakenFromHand;
    }

    /**
     *
     *
     * @param cardIndex index of card that will be returned without being removed from hand
     * @return card corresponding to index
     * @throws RuntimeException if index given doesn't correspond to existing card
     */
    public String showCardInHand(int cardIndex) throws RuntimeException{
        return cards[cardIndex];
    }
}
