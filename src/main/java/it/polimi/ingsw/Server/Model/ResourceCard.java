package it.polimi.ingsw.Server.Model;

import sun.jvm.hotspot.debugger.cdbg.Sym;

import java.util.HashMap;

/**
 * Resource is a Card that can be placed during the game after the first round
 * and can always be placed in placeable coordinate
 */
public class ResourceCard extends Card {

    /**
     * objective is the objective that will be activated once the card is placed giving the player that places it points
     * according to the cardObjective
     */
    private final CardObjective cardObjective;

    /**
     * @param backSymbol   is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each corner
     * @param backCorners  must be an array of 4 Symbols indicating the symbols on each corner
     * @param cardObjective dictates how may point will be gathered when card is placed
     */
    public ResourceCard(Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, CardObjective cardObjective) {}


    public CardObjective getCardObjective() {
        return cardObjective;
    }


}
