package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.HashMap;

/**
 * All cards in game are composed of four front facing corners, four back facing corners and a symbol in the center of the back side.
 * Cards can either be facing up or facing down and can be flipped.
 * All cards are facing down when initialized .
 * By default, all cards satisfy the prerequisites necessary to be placed (regardless of what symbols are visible on the board),
 * so method that checks prerequisites will always return true.
 * However, this method will be overridden in the GoldCard class.
 */
public abstract class Card {

    /**
     * unique identifier of a card, also used to link it to a specific graphic resource
     */
    private final String cardID;
    /**
     * backSymbol is the symbol that is visible on the back side of the card,
     * it is visible only if the card is facing down,
     * it can't be covered by another card during the game,
     * it is an indicator of the color of the card (MUSHROOM=RED, WOLF=BLUE, LEAFS=GREEN, BUTTERFLY=PURPLE)
     */
    private final Symbol backSymbol;


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
     * @param cardID unique identifier used to associate card to graphic resource
     * @param backSymbol is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each front corner
     * @param backCorners must be an array of 4 Symbols indicating the symbols on each back corner
     * @throws IllegalArgumentException if there is no cardId or if corners are not exactly 4
     */
    protected Card(String cardID, Symbol backSymbol, Symbol[] frontCorners, Symbol[] backCorners) throws IllegalArgumentException{
        if(cardID==null){
            throw new IllegalArgumentException("cardId can't be null");
        }
        if(frontCorners.length!=4 || backCorners.length!=4){
            throw new IllegalArgumentException("corners of a card must always be 4");
        }
        this.cardID = cardID;
        this.backSymbol = backSymbol;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
    }

    /**
     * checks weather there are enough of the correct symbols on the board where so that the card can be placed
     * resource and starting cards automatically pass
     * gold cards require check
     * @return true if there are enough of the right symbols on the board
     */
    public boolean checkPrerequisites(HashMap<Symbol, Integer> symbolCounter){
        return true;
    }


    /**
     *
     * @return the corners on the back of the card, independent of card orientation
     */
    public Symbol[] getBackCorners() {
        return backCorners;
    }

    /**
     *
     * @return the corners on the front of the card, independent of card orientation
     */
    public Symbol[] getFrontCorners() {
        return frontCorners;
    }

    /**
     *
     * @return the center symbol on the back of the card
     */
    public Symbol getBackSymbol() {
        return backSymbol;
    }

    public String getCardID() {
        return cardID;
    }

}
