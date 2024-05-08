package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Card;

/**
 * every time a card is placed a new place card is made that saves which card was placed, where it was placed and if it's face up
 */
public class PlacedCard {

    /**
     * card that was placed
     */
    private final Card palcedCard;
    /**
     * coordinates where card was placed
     */
    private final Coordinates cardCoordinates;
    /**
     * orientation of card that was placed
     */
    private final boolean isFacingUp;

    /**
     *
     * @param palcedCard card that was placed
     * @param cardCoordinates coordinates of where card was placed
     * @param isFacingUp orientation  of card that was placed
     */
    public PlacedCard(Card palcedCard, Coordinates cardCoordinates, boolean isFacingUp) {
        this.palcedCard = palcedCard;
        this.cardCoordinates = cardCoordinates;
        this.isFacingUp = isFacingUp;
    }

    public Card getPalcedCard() {
        return palcedCard;
    }

    public Coordinates getCardCoordinates() {
        return cardCoordinates;
    }

    public boolean isFacingUp() {
        return isFacingUp;
    }
}
