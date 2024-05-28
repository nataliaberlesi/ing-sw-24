package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import java.util.Objects;

/**
 * Start menu scene to choose username and number of players for the game
 */
public class StartMenuScene extends Scene {
    /**
     * Username field to select username
     */
    private final TextField usernameField;
    /**
     * ChoiceBox to select number of players for the game
     */
    private final ChoiceBox<Integer> numberOfPlayersChoiceBox;

    /**
     * Constructor for StartMenuScene
     * @param viewController ViewControllerGUI instance to control scene
     * @param confirmActionMethod string to represent if player is creating or joining game and behave accordingly
     */
    public StartMenuScene(ViewControllerGUI viewController, String confirmActionMethod) {
        super(new AnchorPane(), 1060, 595);
        AnchorPane root = (AnchorPane) this.getRoot();

        ImageView background = new ImageView(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/initialScreenOriginal.jpg"))));
        background.setFitWidth(1060);
        background.setFitHeight(600);
        background.setOpacity(0.4);
        background.setPickOnBounds(true);
        background.setPreserveRatio(true);

        Label usernameLabel = new Label("Username");
        StaticsForGUI.setLabelCharacteristics(usernameLabel, "Lao MN Bold", 36, 438, 121);

        Label numberOfPlayersLabel = new Label("Number of players");
        StaticsForGUI.setLabelCharacteristics(numberOfPlayersLabel, "Lao MN Bold", 36, 366, 270);

        Button confirmButton = new Button("Confirm");
        StaticsForGUI.setButtonCharacteristics(confirmButton, "Lao MN Bold", 24, 852, 485, false);

        usernameField = new TextField();
        usernameField.setAlignment(javafx.geometry.Pos.CENTER);
        usernameField.setLayoutX(410);
        usernameField.setLayoutY(178);
        usernameField.setPrefHeight(59);
        usernameField.setPrefWidth(238);
        usernameField.setFont(new Font("Lao MN Bold", 24));

        numberOfPlayersChoiceBox = new ChoiceBox<>();
        numberOfPlayersChoiceBox.setLayoutX(487);
        numberOfPlayersChoiceBox.setLayoutY(330);
        numberOfPlayersChoiceBox.setPrefHeight(51);
        numberOfPlayersChoiceBox.setPrefWidth(81);
        numberOfPlayersChoiceBox.getItems().addAll(2, 3, 4);
        numberOfPlayersChoiceBox.setStyle("-fx-font-size: 18px; -fx-font-family: 'Lao MN Bold';");

        //Makes player join game only choosing username
        if ("join".equals(confirmActionMethod)) {
            confirmButton.setOnAction(event -> viewController.confirmJoin(usernameField.getCharacters().toString().toUpperCase()));
            numberOfPlayersChoiceBox.setVisible(false);
            numberOfPlayersLabel.setVisible(false);
        } else { //Makes player create game, choosing username and number of players
            confirmButton.setOnAction(event -> viewController.confirmCreate(usernameField.getCharacters().toString().toUpperCase(), numberOfPlayersChoiceBox.getValue()));
        }

        root.getChildren().addAll(background, usernameLabel, numberOfPlayersLabel, usernameField, confirmButton, numberOfPlayersChoiceBox);
    }

}
