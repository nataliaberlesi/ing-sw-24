package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SymbolController {
    /**
     * Golden symbols can only be in the corner of resource and gold cards' front facing side.
     * Some Objectives and CardObjectives count how many of these symbols are visible on the board in order to assign points.
     */
    private final static HashSet<Symbol> goldenSymbols=new HashSet<>(Set.of(Symbol.INK,Symbol.SCROLL,Symbol.FEATHER));
    /**
     * These are the only symbols allowed on the center back of cards, they can also be in the corners.
     * Some Objectives, CardObjectives and prerequisites also use these symbols in order to assign points or determine if a
     * gold card can be placed
     */
    private final static HashSet<Symbol> backSymbols=new HashSet<>(Set.of(Symbol.LEAF,Symbol.WOLF,Symbol.MUSHROOM,Symbol.BUTTERFLY));
    /**
     * These symbols are used to establish weather a corner is visible but empty, or hidden.
     */
    private final static HashSet<Symbol> emptySymbols=new HashSet<>(Set.of(Symbol.BLANK,Symbol.FULL));

    public static boolean isNotBackSymbol(Symbol symbol){
        return (!backSymbols.contains(symbol));
    }


    public static boolean isEmptySymbol(Symbol symbol){
        return (emptySymbols.contains(symbol));
    }

    /**
     *
     * @param symbols is a list of symbols that should only contain backSymbols
     * @return false if at least one symbol is not a back symbol, returns true if list is empty of only contains backSymbols
     */
    public static boolean containsOnlyBackSymbols(ArrayList<Symbol> symbols){
        for(Symbol symbol: symbols){
            if(isNotBackSymbol(symbol)){
                return false;
            }
        }
        return true;
    }


}
