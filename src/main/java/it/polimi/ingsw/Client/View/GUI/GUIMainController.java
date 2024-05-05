package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageHandlerException;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageType;
import it.polimi.ingsw.Client.View.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class representing the gui view and acting as main controller for JavaFX application.
 */

public class GUIMainController extends View implements Initializable{

    /**
     * Main application stage.
     */
    private final Stage stage;

    /**
     * Secondary controller for main game scene.
     */
    private final GUISecondaryController secondaryController;

    /**
     * Alert dialog for errors.
     */
    private final Alert errorAlert;

    /**
     * Token color ChoiceBox.
     */
    private final ChoiceBox<String> tokenColorChoice;

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

    public GUIMainController(MessageParser messageParser, MessageDispatcher messageDispatcher, Stage stage) {
        super(messageParser, messageDispatcher);
        this.stage = stage;
        this.tokenColorChoice = new ChoiceBox<>();
        this.playersNumberChoice = new ChoiceBox<>();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        this.secondaryController = new GUISecondaryController(messageParser, messageDispatcher, this.stage);

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

    /**
     * Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */

    @FXML
    private void connectPlayer() throws IOException{
        if (messageParser.masterStatus()) {
            switchToCreate();
        } else switchToJoin();
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
                this.messageDispatcher.createGame(this.playersNumber, this.username);
                this.previousMessageType = MessageType.CREATE;
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
                this.messageDispatcher.joinGame(this.username);
                this.previousMessageType = MessageType.JOIN;
            } catch (IOException e) {
                throw new MessageHandlerException("Unable to join game", e);
            }
        }
    }

    /**
     * Calls the start of the game when all players are connected
     * */
    @Override
    protected void startGame() {
        this.messageDispatcher.startGame(this.username);
    }

    /**
     * Starts showing the game to players
     * */
    @Override
    protected void startShow() {
        this.secondaryController.openMainScene();
        this.secondaryController.displayGame(this.username);
        this.previousMessageType = MessageType.START;
    }

    /**
     * Updates game show
     * */
    @Override
    protected void updateGame() {
        this.secondaryController.displayGame(this.username);
    }

    /**
     * Popup to show abort message
     * @param message abort message
     * */
    @Override
    protected void showAbort(String message) {
        this.showErrorAlert(message, "Please wait to join a new game");
    }

    /**
     * Popup to show error message
     * @param message error message
     */
    @Override
    protected void showError(String message) {
        this.showErrorAlert(message, "Please retry");
    }

    /**
     * Display endgame and winners and close game
     * @param winners winners list
     * */
    @Override
    protected void closeGame(List<String> winners) {
        this.secondaryController.displayEndgame(winners);
    }

    @Override
    protected void enableActions() {
    }

    @Override
    protected void waitTurn() {

    }

    @Override
    protected void returnToMainMenu() {

    }

    /**
     * Main view method override for JavaFX main thread.
     */
    @Override
    public void updateView(){
        Platform.runLater(super::updateView);
    }

    /**
     * Method called to start the view.
     * Implemented only in CLI
     * @param args arguments
     */
    @Override
    public void main(String[] args) {
        //not used for GUI
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
            if (messageParser.unavailableUsername()){
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
     * Shows user a loading screen while waiting to start the game.
     * */
    @Override
    protected void waitForStart() {
        this.switchScene("loading.fxml");
    }
}