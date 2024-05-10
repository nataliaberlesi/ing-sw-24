package it.polimi.ingsw.Client.Network;

/**
 * Enumeration of the different message types.
 */

public enum MessageType {
    /**
     * Game connection.
     */
    CONNECT,
    /**
     * Game creation.
     */
    CREATE,
    /**
     * Player addition to the game.
     */
    JOIN,
    /**
     * Initializes first round.
     */
    START_FIRSTROUND,
    /**
     *
     */
    FIRSTROUND,
    /**
     * Initializes second round.
     */
    START_SECONDROUND,
    /**
     * Initializes player actions.
     */
    START_ACTION,
    /**
     * A player placed a card.
     */
    ACTION_PLACECARD,
    /**
     * A player drown a card.
     */
    ACTION_DRAWCARD,
    /**
     * Game abort.
     */
    ABORT,
    /**
     * Error message.
     */
    ERROR

}
