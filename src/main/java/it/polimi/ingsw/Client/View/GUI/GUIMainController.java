package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.Gateway;
import it.polimi.ingsw.Client.Network.MessageHandlerException;
import it.polimi.ingsw.Client.Network.NetworkManager;
import it.polimi.ingsw.Client.View.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing the gui view and acting as main controller for JavaFX application.
 */

public class GUIMainController extends View implements Initializable{

    /**
     * Gateway instance for communicating with server
     * */
    private Gateway gateway;

    /**
     * Network Manager instance for gateway constructor
     * */
    private NetworkManager networkManager;

    private GUISecondaryController guiSecondaryController;

    /**
     * Alert dialog for errors.
     */
    private final Alert errorAlert;

    /**
     * Main application stage.
     */
    private final Stage stage;
    /**
     * Token color ChoiceBox.
     */
    @FXML
    private ChoiceBox<String> tokenColorChoice;

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
        this.tokenColorChoice = new ChoiceBox<>();
        this.playersNumberChoice = new ChoiceBox<>();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);

    }

    /**
     * Method for switching to a new scene in application.
     *
     * @param resourceName fxml file resource to load
     */

    private void switchScene(String resourceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceName));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e){
            throw new ViewException(e.getMessage(), e);
        }
    }

    /** Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */

    @FXML
    private void connectPlayer() throws IOException{
        try {
            this.networkManager = new NetworkManager("localhost", 8600);
        } catch (IOException e){
            this.showErrorAlert("Unable to connect to server", "Server is currently unavailable, please try again soon");
        }
        if (this.networkManager != null) {
            this.gateway = new Gateway(networkManager);
            if (gateway.masterStatus()) {
                switchToCreate();
            } else switchToJoin();
        }
    }

    /**
     * Show game create menu event.
     */

    private void switchToCreate() {
        this.createMode = true;
        switchScene("startMenu.fxml");
    }

    /**
     * Show game join menu event.
     */

    private void switchToJoin() {
        this.createMode = false;
        this.switchScene("startMenu.fxml");
        this.playersNumberLabel.setVisible(false);
        this.playersNumberChoice.setVisible(false);
    }

    /**
     * Confirm create/join event
     */
    @FXML
    private void confirmCreateJoin() {
        if (this.createMode)
            this.createGame();
        else
            this.joinGame();
    }

    /**
     * Controller initialize method.
     *
     * @param url            url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tokenColorChoice.getItems().addAll(View.AVAILABLE_TOKEN_COLORS.values());
        playersNumberChoice.getItems().addAll(2,3,4);
    }

    /**
     * Show error alert message.
     *
     * @param header  header text
     * @param content content text
     */
    private void showErrorAlert(String header, String content) {
        this.errorAlert.setHeaderText(header);
        this.errorAlert.setContentText(content);
        this.errorAlert.showAndWait();
    }

    /**
     * Send master player's information to server to create game in server
     * */
    @Override
    protected void createGame(){
        if (playerGivesCorrectInformation(this.createMode)) {
            this.playersNumber = this.playersNumberChoice.getValue();
            this.username = this.usernameField.getCharacters().toString();
            try {
                this.gateway.createGame(this.playersNumber, this.username);
                waitForStart();
            } catch (IOException e) {
                throw new MessageHandlerException("Unable to create game", e);
            }

        }

    }
    /**
     * Send player's information to server to join a player to an existing game
     * */
    @Override
    protected void joinGame(){
        if (playerGivesCorrectInformation(this.createMode)) {
            this.username = this.usernameField.getCharacters().toString();
            try {
                this.gateway.joinGame(this.username);
                waitForStart();
            } catch (IOException e) {
                throw new MessageHandlerException("Unable to join game", e);
            }
        }
    }

    /** Checks if the information provided by the player is correct
     * @param createMode create vs join flag
     * */

    private boolean playerGivesCorrectInformation(boolean createMode) {
        if (!View.correctUsername(usernameField.getCharacters().toString())){
            this.showErrorAlert("Invalid username", "Username must contain between 4 and 16 alphanumeric characters");
            return false;
        }
        try {
            if (gateway.unavailableUsername(usernameField.getCharacters().toString())){
                this.showErrorAlert("Invalid username", "Username already taken, please select another one");
            }
        } catch (IOException e) {
            throw new MessageHandlerException("Unable to verify availability of username", e);
        }
        if (createMode){
            if (this.usernameField.getCharacters() == null || this.playersNumberChoice.getValue() == null){
                this.showErrorAlert("Empty field", "Please provide all required information");
                return false;
            }
        }
        else {
            if (this.usernameField.getCharacters() == null){
                this.showErrorAlert("Empty field", "Please provide all required information");
                return false;
            }
        }
        return true;

    }

    /**
     * Waits for positive server response to be able to create the secondary controller and display the starting view.
     * In the meantime shows user a loading screen.
     * */
    @Override
    protected void waitForStart() {
        switchScene("loading.fxml");
        if (!checkWaitForStart()){
            try {
                wait(1000); //wait a second before trying again to not overload server with requests
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            waitForStart();
        }
        else {
            stage.close();
            guiSecondaryController = new GUISecondaryController(gateway);
            guiSecondaryController.displayFirstRoundView();
        }
    }

    /**
     * Asks server if game can start
     * */
    protected boolean checkWaitForStart() {
        try {
            return gateway.checkWaitForStart();
        } catch (IOException e) {
            throw new MessageHandlerException("Unable to check wait for start ", e);
        }
    }
}