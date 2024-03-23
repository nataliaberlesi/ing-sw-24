package it.polimi.ingsw.Server.Model;

public class Player {

    /**
     * player name is used to identify players, must be unique
     */
    private final String playerName;

    /**
     * first player is used to determine turn order as well as who will play the final turn of the game:
     * the person that plays right before the first player
     */
    private boolean isFirstPlayer;

    /**
     * Each player has a unique color that is a visual indicator of player
     */
    private Color playerColor;

    /**
     * a players hand can hold up to three cards,
     * at the end of each turn players hand will always have exactly three cards,
     * unless cards in the drawing sections have run out.
     */
    private Hand playerHand;

    /**
     *  personal board where cards will be placed by player,
     *  a player can place only on his own board,
     *  every board in the game are contained in table.
     */
    private Board playerBoard;

    /**
     * Contains the board of each player and the drawing sections where a player must draw a card (if possible) at the
     * of his/her turn.
     */
    private GameTable table;

    /**
     * player whose turn is next
     */
    private Player nextPlayer;





    public Player(String playerName) {
        this.playerName = playerName;
    }
}
