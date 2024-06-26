package it.polimi.ingsw.Client.View.CLI;

/**
 * class used to print an array of three cards,
 * very useful for printing hands and drawable areas as they are composed of three cards each
 */
public class CardPrinter {
    /**
     *
     * @param cards array of three cards that will be printed
     */
    public static void printExactlyThreeCardsInAnArray(CardCLI[] cards) {
        for(int i=0; i<4; i++) {
            for (CardCLI card : cards) {
                System.out.print(card.getLine(i)+CardIndexCLI.cardLength);
            }
            System.out.print('\n');
        }
    }
}
