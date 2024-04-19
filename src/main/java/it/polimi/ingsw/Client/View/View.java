package it.polimi.ingsw.Client.View;

import java.util.Map;

public abstract class View {

    /**
    * Map of available pion colors.
    */
    public static final Map<String, String> AVAILABLE_PION_COLORS = Map.of(
                "y", "YELLOW",
                "b", "BLUE",
                "g", "GREEN",
                "r", "RED");

    /**
     * Regex for valid usernames.
     */
    protected static final String USERNAME_REGEX = "^\\w{4,16}$";

    /**
     * Flag for first player*/
    protected boolean firstPlayer = false;
    /**
     * Chosen number of players for the game.
     */
    protected int numPlayers;
    /**
     * Chosen player username.
     */
    protected String username;
    /**
     * Chosen player pion color
     */
    protected String pionColor;

}
