package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Resource is a Card that can be placed during the game after the first round
 * but requires certain conditions to me met on the board in order to place
 */
public class GoldCard extends ResourceCard{
    /**
     * prerequisite is the list with the symbols necessary to be on the board in order to be able to place the card
     */
    private final ArrayList<Symbol> prerequisites;

    /**
     * @param backSymbol    is a Symbol that is on the back of the card
     * @param frontCorners  must be an array of 4 Symbols indicating the symbols on each corner
     * @param backCorners   must be an array of 4 Symbols indicating the symbols on each corner
     * @param cardObjective dictates how may point will be gathered when card is placed
     * @param prerequisites is the list of Symbols that are necessary to have on the board in order to place the card
     */
    protected GoldCard(Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, CardObjective cardObjective, ArrayList<Symbol> prerequisites) {
    }
    @Override
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
        //Caution, area under construction...
        return false;
    }
}
