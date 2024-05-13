package it.polimi.ingsw.Server.Model;


/**
 * every time a card is placed a new place card is made that saves which card was placed, where it was placed and if it's face up
 *
 * @param placedCardID     ID of card that was placed
 * @param cardCoordinates coordinates where card was placed
 * @param isFacingUp      orientation of card that was placed
 */
public record PlacedCard(String placedCardID, Coordinates cardCoordinates, boolean isFacingUp) {

    /**
     * @param placedCardID    ID card that was placed
     * @param cardCoordinates coordinates of where card was placed
     * @param isFacingUp      orientation  of card that was placed
     */
    public PlacedCard {
    }
}
