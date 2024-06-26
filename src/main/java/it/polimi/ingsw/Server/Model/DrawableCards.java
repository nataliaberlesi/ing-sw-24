package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Deck;

/**
 * collection of cards that can be drawn,
 * consisting from a deck, a face down card and two face up cards,
 * it is possible to draw directly from each of the cards.
 * When a face up card is drawn it is replaced by the face down card after it has been flipped;
 * when a face down card is drawn or when it replaces a face up card, it is replaced by a card from the deck that will also
 * be face down.
 * The cards that can be drawn are placed in an array,
 * the face down card is always in position 0, while the face up cards are in positions 1 and 2.
 */

 //WIP: 2 decks or 2 objects (one for gold cards and another for common cards)?

public class DrawableCards {
    /**
     * every time the face down card is drawn or moved, it is replaced by a card in the deck.
     *
     */
    private final Deck deck;

    /**
     * array of cards that can be drawn
     * the first card is always shown as face down
     * the second and third are face up
     * when a face up card is drawn it is replaced by the face down card after it has been flipped
     * when the face down card is drawn or moved it is replaced my a card from the deck
     */
    private final String[] drawableCards=new String[3];


    /**
     * method returns cardID of card corresponding ot index, but won't remove card from drawableCards
     * @param cardIndex is the index of card that will return cardID
     * @return cardID corresponding to the index
     * @throws IllegalArgumentException if index greater than 2 or smaller than 0 is passed
     */
    public String seeCard(int cardIndex) throws IllegalArgumentException{
        if(cardIndex<0 || cardIndex>2){
            throw new IndexOutOfBoundException(cardIndex+" is not a valid index of card in drawing section");
        }
        return drawableCards[cardIndex];
    }

    /**
     *
     * @param deck from which the drawableCards come from, takes the first three cards from deck and inserts them in the array of drawable cards
     *
     */
    public DrawableCards(Deck deck) throws IllegalArgumentException{
        if(deck==null||!deck.hasNext()){
            throw new IllegalArgumentException("DrawableCards can't be initialized with empty deck");
        }
        this.deck = deck;
        for(int i=0; i<drawableCards.length; i++){
            drawableCards[i]=this.deck.next();
        }
    }

    /**
     * removes card from array and replaces it
     * if a face up card is drawn (index 1 or 2) it is replaced by the face down card (index 0) after it is flipped
     * if the face down card is drawn or moved then it is replaced by a card in the deck
     * @param cardIndex index of the card that is being drawn, must be < 3
     * @return drawable card at the index that is in input
     */
    public String drawCard(int cardIndex) throws IndexOutOfBoundException{
        if(cardIndex<0 || cardIndex>2){
            throw new IndexOutOfBoundException(cardIndex+" is not a valid index of card in drawing section");
        }
        String cardBeingDrawn=drawableCards[cardIndex];
        if(cardIndex!=0) {
            drawableCards[cardIndex] = drawableCards[0];
        }
        drawableCards[0]=deck.next();
        return cardBeingDrawn;
    }

    /**
     * Exception thrown when an index smaller than 0 or bigger than 2
     */
    public static class IndexOutOfBoundException extends RuntimeException{
        public IndexOutOfBoundException(String message) {
            super((message));
        }
    }

    public boolean deckIsEmpty(){
        return this.drawableCards[0]==null;
    }

    public boolean isEmpty(){
        return (this.drawableCards[0]==null && this.drawableCards[1]==null && this.drawableCards[2]==null);
    }

}
