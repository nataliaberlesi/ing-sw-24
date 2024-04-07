package it.polimi.ingsw.Server.Model;

/**
 * each player has a unique name, board, hand and color, but up to 4 players share a table.
 */
public class Player {

    /**
     * player name is used to identify players, must be unique
     */
    private final String nickname;

    /**
     * first player is used to determine turn order as well as who will play the final turn of the game:
     * the person that plays right before the first player
     */
    private boolean isFirstPlayer=false;

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


    public Player(String nickname) {
        this.nickname = nickname;
    }

    public void setFirstPlayer() {
        isFirstPlayer = true;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setTable(GameTable table) {
        this.table = table;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public GameTable getTable() {
        return table;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }
}
