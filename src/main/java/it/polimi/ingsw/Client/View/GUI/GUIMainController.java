package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.View.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing the gui view, and acting as main controller for JavaFX application.
 */

public class GUIMainController extends View implements Initializable{

    /**
     * Main application stage.
     */
    private final Stage stage;
    /**
     * Pion color ChoiceBox.
     */
    @FXML
    private ChoiceBox<String> pionColorChoice;

    /**
     * Players number ChoiceBox.
     */
    @FXML
    private ChoiceBox<Integer> playersNumberChoice;
    /**
     * Create vs join flag.
     */
    private boolean createMode;
    /**
     * Username TextField.
     */
    @FXML
    private TextField usernameField;
    /**
     * Players number Label.
     */
    @FXML
    private Label playersNumberLabel;

    public GUIMainController(Stage stage) {
        this.stage = stage;
        this.pionColorChoice = new ChoiceBox<>();
        this.playersNumberChoice = new ChoiceBox<>();
    }

    /**
     * Method for switching to a new scene in application.
     *
     * @param resourceName fxml file resource to load
     */

    private void switchScene(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceName));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.sizeToScene();
        this.stage.hide();
        this.stage.show();
    }

    /**
     * Show game create menu event.
     */
    @FXML
    private void switchToCreate() throws IOException {
        this.pionColor = null;
        this.createMode = true;
        switchScene("startMenu.fxml");
    }

    /**
     * Show game join menu event.
     */
    @FXML
    private void switchToJoin() throws IOException {
        this.pionColor = null;
        this.createMode = false;
        this.switchScene("startMenu.fxml");
        this.playersNumberLabel.setVisible(false);
        this.playersNumberChoice.setVisible(false);
    }

    /**
     * Confirm create/join event, WIP
     */
    @FXML
    private void confirmCreateJoin() {
//        if (this.createMode)
//            this.createGame();
//        else
//            this.joinGame();
    }

    /**
     * Controller initialize method.
     *
     * @param url            url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pionColorChoice.getItems().addAll(View.AVAILABLE_PION_COLORS.values());
        playersNumberChoice.getItems().addAll(2,3,4);
    }



}