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
     * @param replacementCard card replacing the first card which is facing down
     * @return card being drawn (facing up)
     * @throws IllegalArgumentException if index out of bounds of replacement card is null or if it's not a resource card
     */
    public CardCLI drawResourceCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalIndex(cardIndex);
        checkLegalCard(replacementCard,'R');
        return getCardCLI(cardIndex, replacementCard, resourceCards);
    }


    /**
     *
     * @param cardIndex index of card being drawn
     * @param replacementCard card replacing the first card which is facing down
     * @return card being drawn (facing up)
     * @throws IllegalArgumentException if index out of bounds of replacement card is null or if it's not a gold card
     */
    public CardCLI drawGoldCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalIndex(cardIndex);
        checkLegalCard(replacementCard, 'G');
        return getCardCLI(cardIndex, replacementCard, goldCards);
    }


    /**
     *
     * @param cardIndex index of card being drawn
     * @param replacementCard card replacing the first card which is facing down
     * @param drawnCards array containing the card that is being drawn
     * @return card being drawn (facing up)
     */
    private CardCLI getCardCLI(int cardIndex, CardCLI replacementCard, CardCLI[] drawnCards) {
        cardIndex--;
        CardCLI drawnCard= drawnCards[cardIndex];
        CardCLI faceDownCard= drawnCards[0];
        faceDownCard.flip();
        if(replacementCard.isFaceUp()){
            replacementCard.flip();
        }
        drawnCards[cardIndex]=faceDownCard;
        drawnCards[0]=replacementCard;
        return drawnCard;
    }

    /**
     *
     * @param cardIndex index of card being drawn
     * @throws IllegalArgumentException if index is bigger than 3 or smaller than 1
     */
    private void checkLegalIndex(int cardIndex) throws IllegalArgumentException{
        if(cardIndex<1||cardIndex>3){
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
            if(card.getCardID().charAt(0)!='E') {
                throw new IllegalArgumentException("Card does not have the correct type");
            }
        }
    }

    private void printDrawableCards(CardCLI[] drawableCards) {
        CardPrinter.printExactlyThreeCardsInAnArray(drawableCards);
        System.out.println();
    }

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

    public void putGoldCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalIndex(cardIndex);
        checkLegalCard(replacementCard,'G');
        putCard(cardIndex, replacementCard, goldCards);
    }

    public void putResourceCard(int cardIndex, CardCLI replacementCard) throws IllegalArgumentException{
        checkLegalIndex(cardIndex);
        checkLegalCard(replacementCard,'R');
        putCard(cardIndex, replacementCard, resourceCards);
    }

    private void putCard(int cardIndex, CardCLI replacementCard, CardCLI[] drawableCards) {
        cardIndex--;
        if(cardIndex==0){
            if(replacementCard.isFaceUp()){
                replacementCard.flip();
            }
        }
        else if(!replacementCard.isFaceUp()){
            replacementCard.flip();
        }
        drawableCards[cardIndex]=replacementCard;
    }

}
