package it.polimi.ingsw.Client.View.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Objects;

public class PrivateObjectiveChoice extends Scene {
    private AnchorPane root;
    private HashMap<Integer, ImageView> objectiveCards = new HashMap<>();
    private ViewControllerGUI viewControllerGUI;
    public PrivateObjectiveChoice(String[] cardIDs, ViewControllerGUI viewControllerGUI) {
        super(new AnchorPane(),380,220);
        this.viewControllerGUI = viewControllerGUI ;
        root = (AnchorPane) this.getRoot();
        Label label = new Label("Choose your secret objective:");
        label.setLayoutX(87);
        label.setLayoutY(27);
        label.setFont(new Font("System Bold", 16));
        label.setAlignment(Pos.CENTER);
        root.getChildren().add(label);
        addImagesToScene(cardIDs);
    }

    private void addImagesToScene(String[] cardIDs) {
        for(int i = 0; i < cardIDs.length; i++) {
            ImageView objective = new ImageView();
            objective.setFitWidth(160);
            objective.setFitHeight(100);
            String imagePath = String.format("Images/%s.png", cardIDs[i]);
            objective.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath))));
            int finalI = i;
            objective.setOnMouseClicked(event -> handlePrivateObjectiveChoice(finalI, cardIDs[finalI]));
            objectiveCards.put(i, objective);
        }

        for (int i = 0; i < cardIDs.length; i++) {
            ImageView objective = objectiveCards.get(i);
            objective.setLayoutX(31 + i*170);
            objective.setLayoutY(80);
            root.getChildren().add(objective);
        }
    }

    private void handlePrivateObjectiveChoice(int i, String cardID) {
        viewControllerGUI.getMyPlayer().setPrivateObjectiveIndex(i);
        viewControllerGUI.getMyPlayer().setPrivateObjectiveID(cardID);
        MainScene.enableActions = true;
        viewControllerGUI.getMainScene().enableConfirmButtonClick();
        viewControllerGUI.getPopUpStage().close();
    }
}
