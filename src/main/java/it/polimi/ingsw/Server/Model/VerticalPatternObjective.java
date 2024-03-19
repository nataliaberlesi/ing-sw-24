package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class VerticalPatternObjective implements Objective{

    private static final int POINTS=3;
    private final Symbol symbolOfInterest;
    private final Symbol outOfLineSymbol;
    private ArrayList<Coordinates> ListOfOutOfLineSymbols=new ArrayList<>();
    private HashMap<Coordinates, Symbol> ListOfSymbolsOfInterest=new HashMap<>();

    protected VerticalPatternObjective(Symbol symbolOfInterest, Symbol outOfLineSymbol) {
        this.symbolOfInterest = symbolOfInterest;
        this.outOfLineSymbol = outOfLineSymbol;
    }

    private boolean checkVertical(Coordinates bottomCoordinates){
        return false;
    }

}
