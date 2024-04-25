package it.polimi.ingsw.Client.View;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Chosen number of players for the game.
     */
    protected int playersNumber;
    /**
     * Chosen player username.
     */
    protected String username;
    /**
     * Chosen player pion color
     */
    protected String pionColor;

    protected static boolean correctUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Method called to create the game.
     */
    protected abstract void createGame();

    /**
     * Method called to join the game.
     */
    protected abstract void joinGame();

    /**
     * Runtime exception for errors within any view class.
     */

    protected abstract void waitForStart() throws IOException;
    public static class ViewException extends RuntimeException {
        /**
         * ViewException constructor with message.
         *
         * @param message message to be shown
         */
        public ViewException(String message) {
            super(message);
        }

        /**
         * ViewException constructor with message and cause.
         *
         * @param message message to be shown
         * @param cause   cause of the exception
         */
        public ViewException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
