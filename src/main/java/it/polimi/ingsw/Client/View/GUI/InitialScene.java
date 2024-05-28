package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Scene to represent initial scene (Play scene)
 */
public class InitialScene extends Scene {
    /**
     * Root of scene
     */
    private final AnchorPane root;
    /**
     * Clickable image view to go to next scene
     */
    private final ImageView clickable = new ImageView();

    /**
     * Constructor for initial scene without persistence
     * @param viewController ViewControllerGUI instance to control scene
     */
    public InitialScene(ViewControllerGUI viewController) {
        super(new AnchorPane(), 1060.0, 595.0);
        root = (AnchorPane) this.getRoot();
        initializeScene();
        clickable.setOnMouseClicked(event -> viewController.connectPlayer());
    }

    /**
     * Constructor for initial scene in case of persistence
     * @param viewController ViewControllerGUI instance to control scene
     * @param popUpStage pop up to ask player if he intends to continue a game already started
     */
    public InitialScene(ViewControllerGUI viewController, Stage popUpStage) {
        super(new AnchorPane(), 1060.0, 595.0);
        root = (AnchorPane) this.getRoot();
        initializeScene();
        clickable.setOnMouseClicked(event -> new popUpScene(popUpStage, viewController));
    }

    /**
     * Initializes scene's components
     */
    private void initializeScene(){
        ImageView backgroundImage = new ImageView();
        backgroundImage.setFitHeight(600.0);
        backgroundImage.setFitWidth(1060.0);
        backgroundImage.setPickOnBounds(true);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/initialScreen.png"))));

        clickable.setFitHeight(192.0);
        clickable.setFitWidth(307.0);
        clickable.setLayoutX(376.0);
        clickable.setLayoutY(209.0);
        clickable.setOpacity(0);
        clickable.setPickOnBounds(true);
        clickable.setPreserveRatio(true);
        root.getChildren().addAll(backgroundImage, clickable);
    }

    /**
     * Nested class to make pop up scene that asks about continuing an existing game
     */
    private static class popUpScene extends Scene{
        public popUpScene(Stage popUpStage, ViewControllerGUI viewController) {
            super(new AnchorPane(),350,220);
            AnchorPane root = (AnchorPane) this.getRoot();
            Label label = new Label("There is a saved game\nDo you want to continue it?");
            StaticsForGUI.setLabelCharacteristics(label, "System Bold", 18, 72,30);

            Button yesButton = new Button("Yes");
            StaticsForGUI.setButtonCharacteristics(yesButton, "System", 16, 80, 140, false);
            yesButton.setOnMouseClicked(event -> {
                viewController.sendPersistenceMessage(true);
                popUpStage.close();
            });

            Button noButton = new Button("No");
            StaticsForGUI.setButtonCharacteristics(noButton, "System", 16, 225, 140, false);
            noButton.setOnMouseClicked(event -> {
                viewController.sendPersistenceMessage(false);
                popUpStage.close();

            });

            root.getChildren().addAll(label, yesButton, noButton);

            viewController.setSceneOnStage(this, popUpStage);
        }
    }

}
