package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;
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

public class ViewControllerGUI extends ViewController implements Initializable{

    /**
     * Main application stage.
     */
    private Stage stage;

    /**
     * Alert dialog for errors.
     */
    private Alert errorAlert;

    private MainSceneController mainSceneController;

    /**
     * Players number ChoiceBox.
     */
    @FXML
    private ChoiceBox<Integer> playersNumberChoice;
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

    public ViewControllerGUI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
        //this.stage = stage;
    }

    public void setStage (Stage stage){
        this.stage = stage;
        this.playersNumberChoice = new ChoiceBox<>();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
    }
    public Stage getStage(){
        return stage;
    }

    /**
     * Method for switching to a new FXML scene in application.
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
    protected void connectPlayer() {
        super.connectPlayer();
    }

    /**
     * Show game create menu event.
     */
    @Override
    protected void switchToCreate() {
        switchScene("startMenu.fxml");
    }

    /**
     * Show game join menu event.
     */
    @Override
    protected void switchToJoin() {
        this.switchScene("startMenu.fxml");
        this.playersNumberLabel.setVisible(false);
        this.playersNumberChoice.setVisible(false);
    }

    /**
     * Confirm create/join event
     */
    @FXML
    protected void confirmCreateJoin() {
        Integer numberOfPlayers = this.playersNumberChoice.getValue();
        String username = this.usernameField.getCharacters().toString();
        super.checkParamsAndSendCreateOrJoinMessage(username, numberOfPlayers);

    }

    /**
     * Controller initialize method.
     *
     * @param url            url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playersNumberChoice.getItems().addAll(2,3,4);
    }

    /**
     * Show error alert message.
     *
     * @param header  header text
     * @param content content text
     */
    @Override
    protected void showErrorAlert(String header, String content) {
        this.errorAlert.setHeaderText(header);
        this.errorAlert.setContentText(content);
        this.errorAlert.showAndWait();
    }


    @Override
    protected void createMyPlayer(String username) {

    }

    /**
     * Calls the start of the game when all players are connected
     * */
    @Override
    protected void startGame() {
        //this.messageDispatcher.startGame(this.username);
    }

    /**
     * Starts showing the game to players
     * */
    @Override
    protected void startShow() {
        //this.mainSceneController.openMainScene();
        //this.mainSceneController.displayGame(this.username);
    }

    /**
     * Updates game show
     * */
    @Override
    protected void updateGame() {
        //this.mainSceneController.displayGame(this.username);
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
    protected void displayWinners(List<String> winners) {
        //this.mainSceneController.displayEndgame(winners);
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

    @Override
    protected void connectScene() {
        switchScene("initialScreen.fxml");
    }

    @Override
    protected void enableFirstRoundActions() {

    }

    @Override
    protected void showScene() {
        mainSceneController = new MainSceneController();
        mainSceneController.setStage(this.stage);
        mainSceneController.setScene(new MainScene());
    }

    @Override
    protected void setAvailableColors() {

    }

    @Override
    protected void setDrawableArea() {

    }

    @Override
    protected void giveInitialCards() {

    }

    @Override
    protected void addPlayers() {

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

    @Override
    protected void switchToLoading() {
        switchScene("loading.fxml");
    }


}