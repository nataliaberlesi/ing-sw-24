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
import java.util.ArrayList;
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
    private ChoiceBox<Integer> numberOfPlayersChoiceBox;
    /**
     * Username TextField.
     */
    @FXML
    private TextField usernameField;
    /**
     * Players number Label.
     */
    @FXML
    private Button confirmButton;

    public ViewControllerGUI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
    }

    public void setStage (Stage stage){
        this.stage = stage;
        this.numberOfPlayersChoiceBox = new ChoiceBox<>();
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
            if (!stage.isShowing())
                this.stage.show();
        } catch (IOException e){
            throw new RuntimeException("Error while trying to switch scene");
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
        switchScene("startMenuCreate.fxml");
    }

    /**
     * Show game join menu event.
     */
    @Override
    protected void switchToJoin() {
        switchScene("startMenuJoin.fxml");
    }

    /**
     * Confirm create event
     */
    @FXML
    protected void confirmCreate() {
        Integer numberOfPlayers = this.numberOfPlayersChoiceBox.getValue();
        String username = this.usernameField.getCharacters().toString();
        super.checkParamsAndSendCreate(username, numberOfPlayers);
    }

    /**
     * Confirm join event
     */
    @FXML
    protected void confirmJoin() {
        String username = this.usernameField.getCharacters().toString();
        super.checkParamsAndSendJoin(username);
    }

    /**
     * Controller initialize method.
     *
     * @param url            url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfPlayersChoiceBox.getItems().addAll(2,3,4);
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
        super.username = username;
    }


    @Override
    protected void switchWaitingServerResponse() {
    }


    /**
     * Main view method override for JavaFX main thread.
     */
    @Override
    public void updateView(){
        Platform.runLater(super::updateView);
    }

    @Override
    protected void enablePlaceCard() {

    }

    @Override
    protected void enableDrawCard() {

    }

    @Override
    protected void enableSecondRoundActions() {

    }

    @Override
    protected void enablePlaceStartingCard() {

    }

    @Override
    protected void enableChooseColor() {

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
    }

    @Override
    protected void setAvailableColors() {

    }

    @Override
    protected void setDrawableArea() {

    }

    @Override
    protected void giveInitialCard(String username) {

    }

    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) {
        mainSceneController = new MainSceneController();
        for (String username : playerUsernames){
            PlayerGUI player = new PlayerGUI(username);
            mainSceneController.setStage(this.stage);
            mainSceneController.setScene(new MainScene());
        }
    }


    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhosTurnItIs) {
        return false;
    }

    @Override
    protected void switchToLoading() {
        switchScene("loading.fxml");
    }


}
