package it.polimi.ingsw.Client.View.GUI;

public class PlayerGUI {
    private final String username;
    private BoardGUI board = new BoardGUI();
    private HandGUI hand = new HandGUI();
    private String color;
    private int privateObjectiveIndex;

    private String privateObjectiveID;
    private boolean isCurrentPlayer = false;

    public PlayerGUI(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public HandGUI getHand() {
        return hand;
    }

    public void setBoard(BoardGUI board) {
        this.board = board;
    }

    public void setHand(HandGUI hand) {
        this.hand = hand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setPrivateObjectiveIndex(int index) {
        this.privateObjectiveIndex = index;
    }
    public int getPrivateObjectiveIndex() {
        return privateObjectiveIndex;
    }

    public void setPrivateObjectiveID(String privateObjectiveID) {
        this.privateObjectiveID = privateObjectiveID;
    }

    public String getPrivateObjectiveID() {
        return privateObjectiveID;
    }

    public BoardGUI getBoard() {
        return board;
    }
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }
    public void setCurrentPlayer(boolean currentPlayer) {
        this.isCurrentPlayer = currentPlayer;
    }
}
