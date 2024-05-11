package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainSceneController {
    private Stage stage;
    private MainScene scene;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(MainScene scene) {
        this.scene = scene;
    }

    public void handleDrawableAreaClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        CardGUI clickedCard = (CardGUI) clickedImageView.getUserData();
        Node parent = clickedImageView.getParent();
        if(parent instanceof VBox container){
            if (container == scene.getResourceCardsDrawableArea()){
                //Put resource card in hand if it's your turn
            } else if (container == scene.getGoldCardsDrawableArea()) {
                //Put gold card in hand
            }
        }
    }
    public void handleHandAreaClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        CardGUI clickedCard = (CardGUI) clickedImageView.getUserData();
        Node parent = clickedImageView.getParent();
        if(parent instanceof HBox container){
            if (container == scene.getHandCardsDrawableArea()) {
                //PlaceCard on board if it's your turn
            }
        }
    }
}
