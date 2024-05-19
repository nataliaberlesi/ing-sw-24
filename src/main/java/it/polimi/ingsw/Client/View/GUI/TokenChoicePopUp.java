package it.polimi.ingsw.Client.View.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TokenChoicePopUp extends Scene {
    private AnchorPane root;
    Stage popUpStage;
    private HashMap<String, ImageView> availableTokens = new HashMap<>();
    private MainScene mainScene;

    public TokenChoicePopUp(ArrayList<String> tokenColors, MainScene mainScene) {
        super(new AnchorPane(),300,250);
        this.mainScene = mainScene;
        root = (AnchorPane) this.getRoot();
        Label label = new Label("Choose one of the available colors:");
        label.setLayoutX(37);
        label.setLayoutY(27);
        label.setFont(new Font("System Bold", 16));
        label.setAlignment(Pos.CENTER);
        root.getChildren().add(label);
        addTokensToPopUpScene(tokenColors);
    }

    private void addTokensToPopUpScene(ArrayList<String> tokenColors){
        for (String token: tokenColors){
            ImageView imageView = new ImageView();
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            String imagePath = String.format("Images/Tokens/%s.png", token);
            imageView.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath))));
            imageView.setOnMouseClicked(event -> handleTokenColorChoice(token));
            availableTokens.put(token, imageView);
        }
        int xOffset = 70; //Base x position for the first token
        int yOffset = 80; //Base y position for the first token
        int xStep = 100; //Horizontal space between tokens
        int yStep = 80; //Vertical space between tokens (when moving to a new row)

        for (int i = 0; i < tokenColors.size(); i++) {
            int x = xOffset + (i%2) * xStep; // % 2 creates two columns
            int y = yOffset + (i/2) * yStep; // / 2 increments row index every 2 tokens
            ImageView imageView = availableTokens.get(tokenColors.get(i));
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            root.getChildren().add(imageView);
        }
    }

    public void handleTokenColorChoice(String color) {
        ViewControllerGUI viewControllerGUI = mainScene.getViewControllerGUI();
        viewControllerGUI.getMessageDispatcher().firstRound(viewControllerGUI.getMyPlayer().getUsername(), mainScene.getChosenCard().isFaceUp(), color);
        popUpStage.close();

    }

    protected void setPopUpStage(){
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        popUpStage.setFullScreen(false);
        popUpStage.setTitle("Codex Naturalis");
        popUpStage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
    }
}
