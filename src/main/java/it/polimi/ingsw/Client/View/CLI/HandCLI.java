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
     * replaces all cards in hand with the updated hand sent by server
     * @param cardsInUpdatedHand is the new hand sent by server
     */
    public void updateHand(CardCLI[] cardsInUpdatedHand) {
        CardCLI updatedCard;
        for(int i=0; i<3; i++) {
            updatedCard = cardsInUpdatedHand[i];
            if(updatedCard == null) {
                updatedCard = emptyCard;
            }
            hand[i] = updatedCard;
        }
    }

    /**
     * prints out cards in hand
     */
    public void printHand(){
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
