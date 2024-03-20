package it.polimi.ingsw.Server.Model;

import java.util.HashMap;

/**
 * the card abstract class is extended by all cards that are in play
 * throughout the game
 */
public abstract class Card {

    /**
     * backSymbol is the symbol that is visible on the back side of the card,
     * it is visible only is the card is facing down, it can't be covered by another card during the game,
     * it is an indicator of the color of the card (MUSHROOM=RED, WOLF=BLUE, LEAFS=GREEN, BUTTERFLY=PURPLE)
     */
    private final Symbol backSymbol;

    /**
     * isFacingUp indicates the orientation of the card, false if card is facing down,
     * used to verify which corners are visible
     * cards are facing up when initialized
     */
    private boolean isFacingUp = true;
    /**
     * frontCorners are the corners visible on the front
     * must be 4
     * corners are ordered starting from the top right corner moving counterclockwise:
     *             1--------0
     *             |        |
     *             2--------3
     */
    private final Symbol[] frontCorners;
    /**
     * backCorners are the corners visible on the back
     * must be 4
     * corners are ordered starting from the top right corner moving counterclockwise:
     *             1--------0
     *             |        |
     *             2--------3
     */
    private final Symbol[] backCorners;

    /**
     *
     * @param backSymbol is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each corner
     * @param backCorners must be an array of 4 Symbols indicating the symbols on each corner
     */
    protected Card(Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners, Symbol backSymbol1, Symbol[] frontCorners1, Symbol[] backCorners1) {
        this.backSymbol = backSymbol1;
        this.frontCorners = frontCorners1;
        this.backCorners = backCorners1;
    }
    /**
     * method checks weather there are enough of the right symbols are on the board where its placed
     * resource cards automatically pass
     * gold cards require check
     * @return true if there are enough of the right symbols on the board
     */
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
        return true;
    }

    public void flipCard(){
        isFacingUp=!isFacingUp;
    }

    public Symbol[] getVisibleCorners(){
        if(isFacingUp){
            return frontCorners;
        }
        return backCorners;
    }

    public Symbol[] getBackCorners() {
        return backCorners;
    }

    public Symbol[] getFrontCorners() {
        return frontCorners;
    }

    public Symbol getBackSymbol() {
        return backSymbol;
    }

    public boolean isFacingeUp() {
        return isFacingUp;
    }


}
