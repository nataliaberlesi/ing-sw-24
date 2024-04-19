package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.*;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * makes objective IDs and also objective corresponding to a specific ID
 */
public class ObjectiveFactory {

    private final static HashMap<String, Supplier<Objective>> objectiveMaker=new HashMap<>(){{
        put("O00",()->new DiagonalPatternObjective(Symbol.MUSHROOM));
        put("O01",()->new DiagonalPatternObjective(Symbol.LEAF));
        put("O02",()->new DiagonalPatternObjective(Symbol.WOLF));
        put("O03",()->new DiagonalPatternObjective(Symbol.BUTTERFLY));
        put("O04",()->new VerticalPatternObjective(Symbol.MUSHROOM));
        put("O05",()->new VerticalPatternObjective(Symbol.LEAF));
        put("O06",()->new VerticalPatternObjective(Symbol.WOLF));
        put("O07",()->new VerticalPatternObjective(Symbol.BUTTERFLY));
        put("O08",()->new SymbolObjective(Symbol.MUSHROOM));
        put("O09",()->new SymbolObjective(Symbol.LEAF));
        put("O10",()->new SymbolObjective(Symbol.WOLF));
        put("O11",()->new SymbolObjective(Symbol.BUTTERFLY));
        put("O12", MixSymbolObjective::new);
        put("O13",()->new SymbolObjective(Symbol.SCROLL));
        put("O14",()->new SymbolObjective(Symbol.INK));
        put("O15",()->new SymbolObjective(Symbol.FEATHER));
    }};

    /**
     *
     * @return all the IDs of objectives
     */
    public static ArrayList<String> makeEveryObjectiveID(){
        String objectivePrefix="O";
        ArrayList<String> everyObjectiveID=new ArrayList<>();
        for(int i=0; i<10; i++){
            everyObjectiveID.add(objectivePrefix+0+i);
        }
        for(int i=10; i<16; i++){
            everyObjectiveID.add(objectivePrefix+i);
        }
        return everyObjectiveID;
    }


    /**
     *
     * @param objectiveID is the ID of the objective that will be initiated
     * @return objective corresponding to ID
     */
    public static Objective makeObjective(String objectiveID) throws IllegalArgumentException{
        Supplier<Objective> objectiveSupplier= objectiveMaker.get(objectiveID);
        if(objectiveSupplier!=null){
            return objectiveSupplier.get();
        }
        else{
            throw new IllegalArgumentException(objectiveID+" Card not found");
        }
    }

}
