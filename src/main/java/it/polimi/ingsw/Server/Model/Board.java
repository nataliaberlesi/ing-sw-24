package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    //We will probably also need an ArrayList<Coordinates> that memorizes all of the keys for occupiedCoordinates
    /**
     * map of all coordinates that are already occupied by a card
     */
    private HashMap<Coordinates, Card> occupiedCoordinates = new HashMap<>();

    /**
     * list of all coordinates where cards can't be placed
     */
    private ArrayList<Coordinates> unplaceableCoordinates = new ArrayList<>();

    /**
     * map of all coordinates where cards are allowed to be placed
     */
    private HashMap<Coordinates, ArrayList<Symbol>> placeableCoordinates = new HashMap<>();

    /**
     * map containing number of visible occurrences for each symbol
     */
    private HashMap<Symbol, Integer> visibleSymbolCounter = new HashMap<>();

    /**
     * array of objectives that will assign points at the end of the game
     * the objectives stay the same throughout the game
     * there are only ever 3 objectives in the array
     */
    private final Objective[] objectives;

    /**
     *
     * @param objectives array of objectives that will assign points at the end of the game
     */
    public Board(Objective[] objectives) {
        this.objectives = objectives;
    }

    /**
     * this method removes the symbols that are being covered from the symbolCounter
     * @param coveredCorners list of symbols that are being covered
     */
    private void removeCoveredCorners(ArrayList<Symbol> coveredCorners){}

    /**
     * this method assigns each corner of a card that is being placed to the adequate coordinates
     * @param card card that is being placed
     * @param coordinates location where the card is being placed
     */
    private void placeSymbolsInCoordinates(Card card, Coordinates coordinates){}

    /**
     * this method receives a card and coordinates where it needs to be placed,
     * it checks weather it can be placed and if so it uses helper methods to update
     * the board's attributes
     *
     * @param card is the card that is being placed
     * @param coordinates are the coordinates where the card will be placed
     * @return true if card is placed and attributes are updated
     */
    public boolean placeCard(Card card, Coordinates coordinates){return false;}

}
