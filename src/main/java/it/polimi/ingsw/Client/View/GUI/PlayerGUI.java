package it.polimi.ingsw.Client.View.GUI;

import java.util.ArrayList;

public class PlayerGUI {
    private final String username;
    private BoardGUI board = new BoardGUI();
    private HandGUI hand = new HandGUI();


    public PlayerGUI(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public HandGUI getHand() {
        return hand;
    }

    public BoardGUI getBoard() {
        return board;
    }
}
