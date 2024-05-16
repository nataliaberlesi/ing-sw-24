package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;

import java.util.ArrayList;

public class PlayerGUI {
    private final String username;
    private int score;
    private boolean isFirstPlayer = false;
    private final MainScene scene;
    private TokenChoicePopUp tokenChoicePopUpScene;
    public PlayerGUI(String username) {
        this.scene = new MainScene();
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

    public MainScene getScene() {
        return scene;
    }

    public void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        this.tokenChoicePopUpScene = new TokenChoicePopUp(tokenColors);
    }

    public TokenChoicePopUp getTokenChoicePopUpScene() {
        return tokenChoicePopUpScene;
    }
}
