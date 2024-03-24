package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Symbol;

/**
 * Resource is a Card that can be placed during the game after the first round
 * and can always be placed in placeable coordinates
 */
public class ResourceCard extends Card {

    /**
     * objective is the objective that will be activated once the card is placed giving the player that places it points
     * according to the cardObjective
     */
    private final CardObjective cardObjective;

    /**
     * @param cardId       unique identifier used to associate card to graphic resource
     * @param backSymbol   is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each front corner
     * @param backCorners  must be an array of 4 Symbols indicating the symbols on each back corner
     * @param cardObjective is the objective that will be activated once the card is placed
     */
    protected ResourceCard(String cardId, Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, CardObjective cardObjective) {
        super(cardId, backSymbol, frontCorners, backCorners);
        this.cardObjective = cardObjective;
    }


    public CardObjective getCardObjective() {
        return cardObjective;
    }
}
