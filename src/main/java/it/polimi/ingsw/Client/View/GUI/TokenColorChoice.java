package it.polimi.ingsw.Client.View.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Scene to give player the possibility of choosing their color token
 */
public class TokenColorChoice extends Scene {
    /**
     * Root of scene
     */
    private final AnchorPane root;
    /**
     * Map to associate the index to the colored token appearing in pop up
     */
    private HashMap<String, ImageView> availableTokens = new HashMap<>();
    /**
     * Instance of ViewControllerGUI to control scene
     */
    private final ViewControllerGUI viewControllerGUI;

    /**
     * Constructor of scene
     * @param tokenColors available color tokens to be put in scene
     * @param viewControllerGUI ViewControllerGUI instance
     */
    public TokenColorChoice(ArrayList<String> tokenColors, ViewControllerGUI viewControllerGUI) {
        super(new AnchorPane(),300,250);
        this.viewControllerGUI = viewControllerGUI ;
        root = (AnchorPane) this.getRoot();
        Label label = new Label("Choose one of the available colors:");
        label.setLayoutX(37);
        label.setLayoutY(27);
        label.setFont(new Font("System Bold", 16));
        label.setAlignment(Pos.CENTER);
        root.getChildren().add(label);
        addTokensToPopUpScene(tokenColors);
    }

    /**
     * Adds token images to pop up scene
     * @param tokenColors available token colors to be added to scene
     */
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

    /**
     * Event handler for choosing a color token
     * @param color chosen color token
     */
    public void handleTokenColorChoice(String color) {
        viewControllerGUI.getMyPlayer().setColor(color);
        StaticsForGUI.enableActions = true;
        viewControllerGUI.getMainScene().enableConfirmButtonClick();
        viewControllerGUI.getPopUpStage().close();
    }

}
