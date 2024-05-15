package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;

public class PlayerGUI {
    private final String username;
    private int score;
    private boolean isFirstPlayer = false;
    private Scene scene;
    public PlayerGUI(String username) {

        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername(){
        return username;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setAsFirstPlayer() {
        isFirstPlayer = true;
    }

}
