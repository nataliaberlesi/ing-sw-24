package it.polimi.ingsw.Server.Model;


import it.polimi.ingsw.Server.Model.Cards.Card;

/**
 * every time a card is placed a new place card is made that saves which card was placed, where it was placed and if it's face up
 *
 * @param placedCard     card that was placed
 * @param cardCoordinates coordinates where card was placed
 * @param isFacingUp      orientation of card that was placed
 */
public record PlacedCard(Card placedCard, Coordinates cardCoordinates, boolean isFacingUp) {

    /**
     * @param placedCard     card that was placed
     * @param cardCoordinates coordinates of where card was placed
     * @param isFacingUp      orientation  of card that was placed
     */
    public PlacedCard {
    }
}
