package it.polimi.ingsw.Client.View.CLI;

/**
 * drawable area as seen from CLI
 */
public class DrawableAreaCLI {

    private final CardCLI[] resourceCards=new CardCLI[3];
    private final CardCLI[] goldCards=new CardCLI[3];


    /**
     *
     * @param cardIndex index of card being drawn
     * @throws IllegalArgumentException if index is bigger than 3 or smaller than 1
     */
    private void checkLegalIndex(int cardIndex) throws IllegalArgumentException{
        if(cardIndex<0||cardIndex>3){
            throw new ArrayIndexOutOfBoundsException("Card index out of bounds");
        }
    }

    /**
     *
     * @param card card that is being inserted in resourceCards ort goldCards
     * @param typeOfCard R means it is resource card, G means it's a gold card
     * @throws IllegalArgumentException if card doesn't correspond to given type, or if card is null
     */
    private void checkLegalCard(CardCLI card, char typeOfCard) throws IllegalArgumentException{
        if(card==null){
            throw new IllegalArgumentException("Card cannot be null");
        }
        if(card.getCardID().charAt(0)!=typeOfCard){
            if(card.getCardID().charAt(0)!='E') { // 'E' is ID of emptyCard
                throw new IllegalArgumentException("Card does not have the correct type");
            }
        }
    }


    /**
     * prints out set of cards that players can draw
     * @param drawableCards cards that can be drawn, can be of two types, ether gold or resource cards
     */
    private void printDrawableCards(CardCLI[] drawableCards) {
        CardPrinter.printExactlyThreeCardsInAnArray(drawableCards);
        System.out.println();
    }

    /**
     * prints all cards that can be drawn by players
     */
    public void printDrawableArea(){
        System.out.println("You can draw these cards:\n");
        System.out.println("RESOURCE CARDS:");
        System.out.println(CardIndexCLI.properlySpacedCardIndex);
        printDrawableCards(resourceCards);
        System.out.print('\n');
        System.out.println("GOLD CARDS:");
        System.out.println(CardIndexCLI.properlySpacedCardIndex);
        printDrawableCards(goldCards);
    }

    /**
     * inserts gold card in drawable area in the position indicated by the index
     * @param cardIndex index of where card will be placed in drawable area
     * @param replacementCard card taking spot indicated by the index
     * @throws IllegalArgumentException if index or type of card is illegal
     */
    public void putGoldCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalityAndPutCard(cardIndex, replacementCard, 'G', goldCards);
    }

    /**
     * inserts resource card in drawable area
     * @param cardIndex index of where card will be placed in drawable area
     * @param replacementCard card taking spot indicated by the index
     * @throws IllegalArgumentException if index or type of card is illegal
     */
    public void putResourceCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalityAndPutCard(cardIndex, replacementCard, 'R', resourceCards);
    }

    /**
     * checks whether index is legal and if the type of the replacement card matches with the drawable section it is being inserted into
     * @param cardIndex index of where card will be placed in drawable area
     * @param replacementCard card taking spot indicated by the index
     * @param cardType can either be gold or resource and each must be assigned to their own drawable section
     * @param drawableCards array of cards that can be drawn, all the same type
     */
    private void checkLegalityAndPutCard(int cardIndex, CardCLI replacementCard, char cardType, CardCLI[] drawableCards){
        if (replacementCard == null) {
            replacementCard = new CardCLI();
        }
        checkLegalIndex(cardIndex);
        checkLegalCard(replacementCard, cardType);
        putCard(cardIndex, replacementCard, drawableCards);
    }

    /**
     * inserts the replacement card in the spot indicated by the index,
     * if the card is being put at index 0 it will be face down, else it will be face up
     * @param cardIndex of where card will be placed in drawable area
     * @param replacementCard card taking spot indicated by the index
     * @param drawableCards array of cards that can be drawn that are of the same type as the card being inserted
     */
    private void putCard(int cardIndex, CardCLI replacementCard, CardCLI[] drawableCards) {
        if(cardIndex==0){
            replacementCard.makeFaceDown();
        }
        else {
            replacementCard.makeFaceUp();
        }
        drawableCards[cardIndex]=replacementCard;
    }

}
