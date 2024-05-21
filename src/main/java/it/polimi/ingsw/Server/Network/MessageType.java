package it.polimi.ingsw.Server.Network;
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
     *Executes first round logic
     */
    FIRSTROUND,
    /**
     * Initializes second round.
     */
    START_SECONDROUND,
    /**
     * Executes second round logic
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
     * The game is ended
     */
    ENDGAME,
    /**
     * Game abort.
     */
    ABORT,
}