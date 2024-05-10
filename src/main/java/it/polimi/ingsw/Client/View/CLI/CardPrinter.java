package it.polimi.ingsw.Client.View.CLI;

/**
 * class used to print an array of three cards
 */
public class CardPrinter {
    /**
     *
     * @param cards array of three cards that will be printed
     */
    public static void printExactlyThreeCardsInAnArray(CardCLI[] cards) {
        for(int i=0; i<4; i++) {
            for (CardCLI card : cards) {
                System.out.print(card.getLine()+CardIndexCLI.cardLength);
            }
            System.out.print('\n');
        }
    }
}
