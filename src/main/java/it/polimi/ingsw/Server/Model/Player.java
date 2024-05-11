package it.polimi.ingsw.Server.Model;

/**
 * each player has a unique name, board, hand and color, but up to 4 players share a table.
 */
public class Player {

    /**
     * player name is used to identify players, must be unique
     */
    private final String username;


    /**
     * Each player has a unique color that is a visual indicator of player
     */
    private Color playerColor;

    /**
     * a players hand can hold up to three cards,
     * at the end of each turn players hand will always have exactly three cards,
     * unless cards in the drawing sections have run out.
     */
    private final Hand playerHand=new Hand();

    /**
     *  personal board where cards will be placed by player,
     *  a player can place only on his own board,
     *  every board in the game are contained in table.
     */
    private Board playerBoard;




    public Player(String username) {
        this.username = username;
    }


    public void setPlayerColor(Color playerColor) {
        if(this.playerColor != null) {
            throw new RuntimeException("Player color already set");
        }
        this.playerColor = playerColor;
    }



    /**
     * initiates player board and adds (not places) starting card
     * @param startingCardID the first card that will be placed
     * @throws RuntimeException if startingCardID is not a starting card ID
     */
    public void placeStartingCard(String startingCardID) throws RuntimeException {
        this.playerBoard=new Board(startingCardID);
    }


    public String getUsername() {
        return username;
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
