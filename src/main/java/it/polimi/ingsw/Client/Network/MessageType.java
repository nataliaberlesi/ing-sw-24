package it.polimi.ingsw.Client.Network;

/**
 * Enumeration of the different message types.
 */

public enum MessageType {
    PERSISTENCE,
    CONTINUE,
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
     * First round
     */
    FIRSTROUND,
    /**
     * Initializes second round.
     */
    START_SECONDROUND,
    /**
     * Second round
     */
    SECONDROUND,
    /**
     * A player placed a card.
     */
    ACTION_PLACECARD,
    /**
     * A player drown a card.
     */
    ACTION_DRAWCARD,
    /**
     * Final round
     */
    ENDGAME,
    /**
     * Game chat
     */
    CHAT,
    /**
     * Game abort.
     */
    ABORT,

}
