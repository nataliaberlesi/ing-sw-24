package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Objectives.CardObjective;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GoldCard is a Card that can be placed during the game after the first round
 * but requires certain conditions to be met on the board in order to place it
 */
public class GoldCard extends ResourceCard{
    /**
     * list that contains the necessary symbols to be on the board in order to be able to place the card
     */
    private final ArrayList<Symbol> prerequisites;

    /**
     * @param backSymbol    is a Symbol that is on the back of the card
     * @param frontCorners  must be an array of 4 Symbols indicating the symbols on each corner
     * @param backCorners   must be an array of 4 Symbols indicating the symbols on each corner
     * @param cardObjective dictates how may point will be gathered when card is placed
     * @param prerequisites is the list with the symbols necessary to be on the board in order to be able to place the card
     */
    public GoldCard(Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, CardObjective cardObjective, ArrayList<Symbol> prerequisites) {
        super(backSymbol, frontCorners, backCorners, cardObjective);
        this.prerequisites = prerequisites;
    }

    /**
     *
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return true if enough of the right symbols are visible on the board
     */
    @Override
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
        //Caution, area under construction... WIP
        return false;
    }
}
