package it.polimi.ingsw.Server.Network;
/**
 * Enumeration of the different message types.
 */

public enum MessageType {
    /**
     * Network connection.
     */
    CONNECTION,
    /**
     * Game creation.
     */
    CREATE,
    /**
     * Player addition to the game.
     */
    JOIN,
    /**
     * Game initialization.
     */
    START,
    /**
     * Player action execution.
     */
    ACTION,
    /**
     * Final round execution.
     * */
    FINAL_ROUND,
    /**
     * Game abort.
     */
    ABORT,
    /**
     * Error message.
     */
    ERROR

}
