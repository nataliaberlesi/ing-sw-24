package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

/** The Board acts like a dynamic matrix,
 * each time that a card is placed it creates the necessary coordinates
 * to indicate where the next cards can be placed and where they can't
 */
public class Board {

    //We will probably also need an ArrayList<Coordinates> that memorizes all the keys for occupiedCoordinates

    /**
     * map of all coordinates that are already occupied by a card
     */
    private HashMap<Coordinates, Card> occupiedCoordinates = new HashMap<>();

    /**
     * list of all coordinates where cards can't be placed
     */
    private ArrayList<Coordinates> unplaceableCoordinates = new ArrayList<>();

    /**
     * map of all coordinates where cards are allowed to be placed (initially = (0,0))
     */
    private HashMap<Coordinates, ArrayList<Symbol>> placeableCoordinates = new HashMap<>();

    /**
     * map containing the number of visible occurrences for each symbol
     * Useful for objectives that assign points based on the number of symbols present in the Board
     */
    private HashMap<Symbol, Integer> visibleSymbolCounter = new HashMap<>();

    /**
     * array of objectives that will assign points at the end of the game
     * the objectives stay the same throughout the game
     * there are only ever 3 objectives in the array
     * (2 common objectives for all players and a personal one for each player)
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
     * removes the symbols that are being covered from the symbolCounter
     * each time a player places a card on the board
     * @param coveredCorners list of symbols that are being covered
     */
    private void removeCoveredCorners(ArrayList<Symbol> coveredCorners){}

    /**
     * associates each corner (symbol) of the card that is being placed in the Board
     * to the adequate coordinates it points to
     * if a card's corned is FULL ("hidden"), it adds the corresponding pointed coordinates to the unplaceableCoordinates hashmap
     * if a card's corner is "visible", it adds its symbol and pointed coordinates to the placeableCoordinates hashmap
     * @param card card that is being placed
     * @param coordinates location where the card is being placed
     */
    private void placeSymbolsInCoordinates(Card card, Coordinates coordinates){}

    /**
     * receives a card and coordinates where it needs to be placed,
     * checks weather it can be placed and if so uses helper methods to update
     * the board's attributes and places the card in the specified coordinates
     *
     * @param card is the card that is being placed
     * @param coordinates are the coordinates where the card will be placed
     * @return true if receives as an input valid coordinates and helper functions don't raise exceptions,
     * false if invalid coordinates are passed
     */

    public boolean placeCard(Card card, Coordinates coordinates){return false;}

}
