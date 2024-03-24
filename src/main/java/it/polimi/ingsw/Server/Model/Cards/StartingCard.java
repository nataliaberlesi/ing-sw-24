package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

/**
 * StartingCards are cards that can only be placed during the first round of each player, no preconditions must be met
 */
public class StartingCard extends Card{

    /**
     * frontCenterSymbols are the symbols on the front of the card, in the center, so they can't be covered
     */
    private final ArrayList<Symbol> frontCenterSymbols;

    /**
     * @param cardId       unique identifier used to associate card to graphic resource
     * @param backSymbol   is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each front corner
     * @param backCorners  must be an array of 4 Symbols indicating the symbols on each back corner
     * @param frontCenterSymbols are the Symbols that are present on some of the starting cards, in the center of the front
     *                           facing side
     */
    protected StartingCard(String cardId, Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, ArrayList<Symbol> frontCenterSymbols) {
        super(cardId, backSymbol, frontCorners, backCorners);
        this.frontCenterSymbols = frontCenterSymbols;
    }

    public ArrayList<Symbol> getFrontCenterSymbols() {
        return frontCenterSymbols;
    }
}
