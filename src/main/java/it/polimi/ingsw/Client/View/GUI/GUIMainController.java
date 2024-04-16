package it.polimi.ingsw.Client.View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GUIMainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}