package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class InitialScene extends Scene {
    private final AnchorPane root;
    private final ImageView clickable = new ImageView();

    public InitialScene(ViewControllerGUI viewController) {
        super(new AnchorPane(), 1060.0, 595.0);
        root = (AnchorPane) this.getRoot();
        initializeScene();
        clickable.setOnMouseClicked(event -> viewController.connectPlayer());
    }
    public InitialScene(ViewControllerGUI viewController, Stage popUpStage) {
        super(new AnchorPane(), 1060.0, 595.0);
        root = (AnchorPane) this.getRoot();
        initializeScene();
        clickable.setOnMouseClicked(event -> new popUpScene(popUpStage, viewController));
    }
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

    private static class popUpScene extends Scene{
        public popUpScene(Stage popUpStage, ViewControllerGUI viewController) {
            super(new AnchorPane(),380,220);
            AnchorPane root = (AnchorPane) this.getRoot();
            Label label = new Label("There is a saved game\nDo you want to continue it?");
            StaticsForGUI.setLabelCharacteristics(label, "System Bold", 18, 87,27);

            Button yesButton = new Button("Yes");
            yesButton.setLayoutX(95);
            yesButton.setLayoutY(150);
            yesButton.setOnMouseClicked(event -> {
                viewController.getPlayerAnswerAndSendPersistenceMessage(true);
                popUpStage.close();
            });

            Button noButton = new Button("No");
            noButton.setLayoutX(285);
            noButton.setLayoutY(150);
            noButton.setOnMouseClicked(event -> {
                viewController.getPlayerAnswerAndSendPersistenceMessage(false);
                popUpStage.close();

            });

            root.getChildren().addAll(label, yesButton, noButton);

            popUpStage.setScene(this);
            popUpStage.hide();
            popUpStage.show();
        }
    }

}
