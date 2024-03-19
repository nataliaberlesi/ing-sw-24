package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

/**
 * StartingCards are cards that can only be placed during the first round of each player, no preconditions must be met
 */
public class StartingCard extends Card{


    private final ArrayList<Symbol> frontCenterSymbols;


    /**
     * @param backSymbol   is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each corner
     * @param backCorners  must be an array of 4 Symbols indicating the symbols on each corner
     * @param frontCenterSymbols are the Symbols that are present on some of the starting cards, in the center of the front
     *                           facing side
     */
    protected StartingCard(Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, ArrayList<Symbol> frontCenterSymbols) {
        super(backSymbol, frontCorners, backCorners);
        this.frontCenterSymbols = frontCenterSymbols;
    }
}
