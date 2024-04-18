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
     * ara that contains all the cards that can be drawn
     */
    private DrawableArea drawableArea;

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




    public Player(String nickname) {
        this.nickname = nickname;
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


    public String getNickname() {
        return nickname;
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

}
