package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
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
    protected GoldCard(String cardId, Symbol backSymbol, Symbol[] frontCorners, CardObjective cardObjective, ArrayList<Symbol> prerequisites) throws InvalidSymbolException{
        super(cardId, backSymbol, frontCorners, cardObjective);
        if(checkInvalidSymbol(prerequisites)){
            throw new InvalidSymbolException("invalid symbol present in prerequisites");
        }
        this.prerequisites = prerequisites;
    }

    //method might need to be static because I think other classes use it
    /**
     *
     * @return true if an invalid symbol for prerequisites is found, so any symbol different from MUSHROOM, WOLF, BUTTERFLY, LEAF.
     */
    private boolean checkInvalidSymbol(ArrayList<Symbol> prerequisites){
        if(!prerequisites.isEmpty()){
            return (prerequisites.contains(Symbol.INK) || prerequisites.contains(Symbol.BLANK) || prerequisites.contains(Symbol.SCROLL) || prerequisites.contains(Symbol.FEATHER)|| prerequisites.contains(Symbol.FULL));
        }
        return false;
    }

    /**
     *
     * @param symbolCounter map containing number of visible occurrences for each symbol
     * @return true if enough of the right symbols are visible on the board
     */
    @Override
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
        HashMap<Symbol, Integer> symbolCounterCopy=new HashMap<>(symbolCounter);
        for(Symbol cardPrerequisite: prerequisites){
            int counter=symbolCounterCopy.get(cardPrerequisite);
            if(counter<=0){
               return false;
            }
            counter--;
            symbolCounterCopy.put(cardPrerequisite, counter);
        }
        return true;
    }

    public ArrayList<Symbol> getPrerequisites() {
        return prerequisites;
    }
}
