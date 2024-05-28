package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Objects;

/**
 * Scene to give player the possibility of choosing their private objective
 */
public class PrivateObjectiveChoiceScene extends Scene {
    /**
     * Root of scene
     */
    private final AnchorPane root;
    /**
     * Map to associate the index to the objective appearing in pop up
     */
    private final HashMap<Integer, ImageView> objectiveCards = new HashMap<>();
    /**
     * Instance of ViewControllerGUI to control scene
     */
    private final ViewControllerGUI viewControllerGUI;

    /**
     * Constructor of scene
     * @param cardIDs ID's of private objectives cards to choose from
     * @param viewControllerGUI viewControllerGUI
     */
    public PrivateObjectiveChoiceScene(String[] cardIDs, ViewControllerGUI viewControllerGUI) {
        super(new AnchorPane(),380,220);
        this.viewControllerGUI = viewControllerGUI ;
        root = (AnchorPane) this.getRoot();
        Label label = new Label("Choose your secret objective:");
        StaticsForGUI.setLabelCharacteristics(label,"System Bold", 16,87,27);
        root.getChildren().add(label);
        addImagesToScene(cardIDs);
    }

    /**
     * Adds images of private objectives to choose to scene
     * @param cardIDs private objectives to choose from
     */
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

    /**
     * Handler for private objective's choice
     * @param i index of chosen private objective in scene
     * @param cardID ID of chosen private objective
     */
    private void handlePrivateObjectiveChoice(int i, String cardID) {
        viewControllerGUI.getMyPlayer().setPrivateObjectiveIndex(i);
        viewControllerGUI.getMyPlayer().setPrivateObjectiveID(cardID);
        StaticsForGUI.enableActions = true;
        viewControllerGUI.getMainScene().enableConfirmButtonClick();
        viewControllerGUI.getPopUpStage().close();
    }
}
