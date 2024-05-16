package it.polimi.ingsw.Client.View.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Objects;

public class TokenChoicePopUp extends Scene {
    AnchorPane root;
    ArrayList<ImageView> availableTokens = new ArrayList<>();

    public TokenChoicePopUp(ArrayList<String> tokenColors) {
        super(new AnchorPane(),300,250);
        root = (AnchorPane) this.getRoot();
        Label label = new Label("Choose one of the available colors:");
        label.setLayoutY(37);
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
            availableTokens.add(imageView);
        }
        int xOffset = 70; //Base x position for the first token
        int yOffset = 80; //Base y position for the first token
        int xStep = 100; //Horizontal space between tokens
        int yStep = 80; //Vertical space between tokens (when moving to a new row)

        for (int i = 0; i < tokenColors.size(); i++) {
            int x = xOffset + (i%2) * xStep; // % 2 creates two columns
            int y = yOffset + (i/2) * yStep; // / 2 increments row index every 2 tokens
            ImageView imageView = availableTokens.get(i);
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            root.getChildren().add(imageView);
        }
    }

}
