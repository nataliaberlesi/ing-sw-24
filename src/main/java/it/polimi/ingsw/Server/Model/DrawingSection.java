package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Card;
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

public class DrawingSection {
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
    private Card[] drawableCards=new Card[3];

    /**
     *
     * @param deck from which the drawableCards come from,
     *             can't change during game
     */
    public DrawingSection(Deck deck) {
        this.deck = deck;
    }

    /**
     * removes card from array and replaces it
     * if a face up card is drawn (index 1 or 2) it is replaced by the face down card (index 0) after it is flipped
     * if the face down card is drawn or moved then it is replaced by a card in the deck
     * @param cardIndex index of the card that is being drawn, must be < 3
     * @return drawable card at the index that is in input
     */
    public Card drawCard(int cardIndex){
        //Caution, method under construction WIP
        return drawableCards[cardIndex];
    }

}
