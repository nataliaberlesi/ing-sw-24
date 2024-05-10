package it.polimi.ingsw.Client.View.CLI;

/**
 * a players hand a drawable cards are printed the same way, this class holds the string that numbers each card, so that the user
 * can communicate which card he is intending to use.
 * This class also has a string that is the same length of a card, but comprises empty spaces, this is used to ensure proper spacing between cards
 * when printed
 */
public class CardIndexCLI {
    /**
     * empty string of the same length of card
     */
    public final static String cardLength = String.format("%9s", "");
    /**
     * empty string of the same length of half a card
     */
    public final static String halfCardLength=String.format("%4s", "");
    /**
     * string that contains properly spaced indexes for cards in hand or in drawing area
     */
    public final static String properlySpacedCardIndex=halfCardLength+"1"+cardLength+halfCardLength+halfCardLength+"2"+cardLength+halfCardLength+halfCardLength+"3";

}

