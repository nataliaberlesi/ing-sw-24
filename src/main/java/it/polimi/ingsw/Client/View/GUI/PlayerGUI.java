package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;

import java.util.ArrayList;

public class PlayerGUI {
    private final String username;
    private int score = 0;
    private String color;
    private final MainScene mainScene;
    private TokenChoicePopUp tokenChoicePopUpScene;
    public PlayerGUI(String username) {
        this.mainScene = new MainScene();
        this.username = username;
    }

    public PlayerGUI(String username, ViewControllerGUI viewControllerGUI) {
        this.mainScene = new MainScene(viewControllerGUI);
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

    public MainScene getMainScene() {
        return mainScene;
    }

    public void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        this.tokenChoicePopUpScene = new TokenChoicePopUp(tokenColors, mainScene);
    }

    public TokenChoicePopUp getTokenChoicePopUpScene() {
        return tokenChoicePopUpScene;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
