package it.polimi.ingsw.Client.View.GUI;

import java.util.ArrayList;

public class PlayerGUI {
    private final String username;
    private int score = 0;
    private boolean isFirstPlayer = false;
    private final MainScene mainScene;
    private TokenChoicePopUp tokenChoicePopUpScene;
    public PlayerGUI(String username) {
        this.mainScene = new MainScene();
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

    public MainScene getMainScene() {
        return mainScene;
    }

    public void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        this.tokenChoicePopUpScene = new TokenChoicePopUp(tokenColors);
    }

    public TokenChoicePopUp getTokenChoicePopUpScene() {
        return tokenChoicePopUpScene;
    }
}
