package it.polimi.ingsw.Server.Model;

/**
 * collection of cards that can be drawn,
 * consisting from a deck and two face up cards,
 * it is possible to draw directly from each of them,
 * face up cards derive from the deck
 */
public class DrawingSection {
    /**
     * every time a face up card is drawn, the top card of the deck will replace it.
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
    public Card drwCard(int cardIndex){
        //Caution, method under construction
        return drawableCards[cardIndex];
    }

}
