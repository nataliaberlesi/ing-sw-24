package it.polimi.ingsw.Client.View.CLI;


/**
 * hand as shown in CLI
 */
public class HandCLI {
    /**
     * a hand contains three cards at a time
     */
    private final CardCLI[] hand = new CardCLI[3];

    /**
     * used to represent missing card from hand
     */
    private final static CardCLI emptyCard = new CardCLI();

    /**
     * creates a card to represent a missing card in hand
     */
    public HandCLI() {
        for(int i=0; i<3; i++) {
            hand[i] = new CardCLI();
        }
    }

    /**
     *
     * @param cardIndex of card that is being taken from hand, that will be replaced by empty card
     * @return the ID of the card being taken
     */
    public String takeCard(int cardIndex){
        cardIndex--;
        String cardID=hand[cardIndex].getCardID();
        hand[cardIndex] = emptyCard;
        return cardID;
    }

    /**
     *
     * @param card that is being placed in hand, replacing the empty card
     */
    public void putCard(CardCLI card){
        for(int i=0; i<3; i++){
            if(hand[i].getCardID().equals(emptyCard.getCardID())){
                hand[i] = card;
                break;
            }
        }
    }


    /**
     * prints out cards in hand
     */
    public void printHand(){
        System.out.println("HAND:");
        System.out.println(CardIndexCLI.properlySpacedCardIndex);
        CardPrinter.printExactlyThreeCardsInAnArray(hand);
        System.out.println();
    }


    /**
     * flips all cards in hand
     */
    public void flipCardsInHand(){
        for(int i=0; i<3; i++){
            hand[i].flip();
        }
    }


    /**
     * prints the back of the cards in hand
     */
    public void printBackOfHand(){
        flipCardsInHand();
        printHand();
        flipCardsInHand();
    }
}
