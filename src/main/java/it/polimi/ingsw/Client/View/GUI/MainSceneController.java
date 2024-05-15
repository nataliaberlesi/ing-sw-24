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

    public static void handleCornerClick(MouseEvent mouseEvent) {
    }
}
