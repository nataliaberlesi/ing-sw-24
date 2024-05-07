package it.polimi.ingsw.Client.View.CLI;

public class CardPrinter {
    public static void printExactlyThreeCardsInAnArray(CardCLI[] cards) {
        for(int i=0; i<4; i++) {
            for (CardCLI card : cards) {
                System.out.print(card.getLine()+"         ");
            }
            System.out.print('\n');
        }
    }
}
