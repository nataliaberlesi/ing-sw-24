package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.SymbolController;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.Arrays;

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
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each front corner
     * @param backCorners  must be an array of 4 Symbols indicating the symbols on each back corner
     * @param frontCenterSymbols are the Symbols that are present on some of the starting cards, in the center of the front
     *                           facing side
     * @throws RuntimeException if frontCenterSymbols are 0 or more than 3 or are not MUSHROOMS, LEAFS, BUTTERFLIES, WOLVES, as such a card doesn't exist.
     *                          the same limitation on symbols are applied to the backCorners plus they must all be different
     */
    public StartingCard(String cardId, Symbol[] frontCorners, Symbol[] backCorners, ArrayList<Symbol> frontCenterSymbols) throws RuntimeException{
        super(cardId, null, frontCorners, backCorners);
        if(!SymbolController.containsOnlyBackSymbols(frontCenterSymbols)||!SymbolController.containsOnlyBackSymbols(new ArrayList<>(Arrays.asList(backCorners)))){
            throw new InvalidSymbolException ("invalid symbol was passed in frontCenterSymbols or BackCorners in the StartingCard constructor");
        }
        if(frontCenterSymbols.size()>3 || frontCenterSymbols.isEmpty()){
            throw new IllegalArgumentException("0 or too many symbols where passed as frontCenterSymbols in the StartingCard constructor");
        }
        if(checkRepeatingSymbols(new ArrayList<>(Arrays.asList(backCorners)))|| checkRepeatingSymbols(frontCenterSymbols)){
            throw new IllegalArgumentException("symbols on the back corners or center symbols of starting cards must all be different");
        }
        this.frontCenterSymbols = frontCenterSymbols;
    }


    /**
     *
     * @param symbols are the 4 corners on the back of the starting card
     * @return true if a symbol is repeated, no such card can exist
     */
    private boolean checkRepeatingSymbols(ArrayList<Symbol> symbols){
        int arraySize=symbols.size();
        for(int i=0; i<arraySize; i++){
            for(int j=i+1; j<arraySize; j++){
                if(symbols.get(i)==symbols.get(j)){
                    return true;
                }
            }
        }
        return false;
    }
    public ArrayList<Symbol> getFrontCenterSymbols() {
        return frontCenterSymbols;
    }
}
