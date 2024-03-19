package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Coordinates, Card> occupiedCoordinates = new HashMap<>();

    private ArrayList<Coordinates> unplaceableCoordinates = new ArrayList<>();

    private HashMap<Coordinates, ArrayList<Symbol>> placeableCoordinates = new HashMap<>();

    private HashMap<Symbol, Integer> visibleSymbolCounter = new HashMap<>();

    private Objective[] objectives;
    private void removeCoveredCorners(ArrayList<Symbol> coveredCorners){}
    private void placeSymbolsInCoordinates(Card card, Coordinates coordinates){}
    public boolean placeCard(Card card, Coordinates coordinates){return false;}

}
