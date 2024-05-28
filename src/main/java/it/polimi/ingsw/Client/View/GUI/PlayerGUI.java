package it.polimi.ingsw.Client.View.GUI;

/**
 * Class to represent a player in GUI
 */
public class PlayerGUI {
    /**
     * Player's username
     */
    private final String username;
    /**
     * Player's board
     */
    private BoardGUI board = new BoardGUI();
    /**
     * Player's hand
     */
    private HandGUI hand = new HandGUI();
    /**
     * Player's color
     */
    private String color;
    /**
     * Player's chosen private objective index in pop up scene
     */
    private int privateObjectiveIndex;
    /**
     * Player's chosen private objective ID in pop up scene
     */
    private String privateObjectiveID;
    /**
     * Indicator if player is current player or not
     */
    private boolean isCurrentPlayer = false;

    /**
     * Constructor of PlayerGUI
     * @param username player's username
     */
    public PlayerGUI(String username) {
        this.username = username;
    }

    /**
     * Getter for player's username
     * @return player's username
     */
    protected String getUsername(){
        return username;
    }

    /**
     * Getter for player's hand
     * @return player's hand
     */
    protected HandGUI getHand() {
        return hand;
    }

    /**
     * Setter for player's board
     * @param board board to be set to player
     */
    protected void setBoard(BoardGUI board) {
        this.board = board;
    }

    /**
     * Setter for player's hand
     * @param hand hand to be set to player
     */
    protected void setHand(HandGUI hand) {
        this.hand = hand;
    }

    /**
     * Setter for player's color
     * @param color color to be set to player
     */
    protected void setColor(String color) {
        this.color = color;
    }

    /**
     * Setter for player's color
     * @return color player's color
     */
    protected String getColor() {
        return color;
    }

    /**
     * Setter for chosen private objective's index
     * @param index chosen private objective's index
     */
    protected void setPrivateObjectiveIndex(int index) {
        this.privateObjectiveIndex = index;
    }

    /**
     * Getter for chosen private objective's index
     * @return chosen private objective's index
     */
    protected int getPrivateObjectiveIndex() {
        return privateObjectiveIndex;
    }

    /**
     * Setter for private objective's ID
     * @param privateObjectiveID private objective's ID
     */
    protected void setPrivateObjectiveID(String privateObjectiveID) {
        this.privateObjectiveID = privateObjectiveID;
    }

    /**
     * Getter for private objective's ID
     * @return privateObjectiveID private objective's ID
     */
    protected String getPrivateObjectiveID() {
        return privateObjectiveID;
    }

    /**
     * Getter for player's board
     * @return player's board
     */
    protected BoardGUI getBoard() {
        return board;
    }

    /**
     * Indicates if player is current player
     * @return true is player is current player, false otherwise
     */
    protected boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * Setter for current player
     * @param currentPlayer true is player is current player, false otherwise
     */
    protected void setCurrentPlayer(boolean currentPlayer) {
        this.isCurrentPlayer = currentPlayer;
    }

}
