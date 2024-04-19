package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.SymbolController;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GoldCard is a Card that can be placed during the game after the first round
 * but requires certain conditions to be met on the board in order to place it
 */
public class GoldCard extends ResourceCard{
    /**
     * list that contains the necessary symbols that have to be on the board in order to be able to place the card
     */
    private final ArrayList<Symbol> prerequisites;

    /**
     * @param cardId        unique identifier used to associate card to graphic resource
     * @param backSymbol    is a Symbol that is on the back of the card
     * @param frontCorners  must be an array of 4 Symbols indicating the symbols on each front corner
     * @param cardObjective is the objective that will be activated once the card is placed
     * @param prerequisites is the list with the symbols necessary to be on the board in order to be able to place the card,
     *                      it can be empty, but not null.
     */
    public GoldCard(String cardId, Symbol backSymbol, Symbol[] frontCorners, CardObjective cardObjective, ArrayList<Symbol> prerequisites) throws InvalidSymbolException{
        super(cardId, backSymbol, frontCorners, cardObjective);
        if(!SymbolController.containsOnlyBackSymbols(prerequisites) || prerequisites.isEmpty()){
            throw new InvalidSymbolException("invalid symbol present in prerequisites");
        }
        this.prerequisites = prerequisites;
    }


    /**
     *
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return true if enough of the right symbols are visible on the board
     */
    @Override
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
            HashMap<Symbol, Integer> symbolCounterCopy = new HashMap<>(symbolCounter);
            for (Symbol cardPrerequisite : prerequisites) {
                if (symbolCounterCopy.get(cardPrerequisite)==null||symbolCounterCopy.get(cardPrerequisite)==0) {
                    return false;
                }
                int counter = symbolCounterCopy.get(cardPrerequisite)-1;
                symbolCounterCopy.put(cardPrerequisite, counter);
            }
        return true;
    }


}
