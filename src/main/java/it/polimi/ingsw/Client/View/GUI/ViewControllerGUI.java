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
import javafx.scene.image.Image;
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
    private Stage mainStage;

    /**
     * PopUp application stage.
     */
    private final Stage popUpStage = new Stage();

    /**
     * Alert dialog for errors.
     */
    private Alert errorAlert;

    /**
     * Instance of player to represent my player
     */
    private PlayerGUI myPlayer;

    /**
     * Array list of players to represent players in game
     */
    private final ArrayList<PlayerGUI> playersInGame = new ArrayList<>();

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

    public ViewControllerGUI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
        popUpStage.setResizable(false);
        popUpStage.setFullScreen(false);
        popUpStage.setTitle("Codex Naturalis");
        popUpStage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
    }

    protected void setMainStage(Stage mainStage){
        this.mainStage = mainStage;
    }

    protected void setComponents(){
        this.numberOfPlayersChoiceBox = new ChoiceBox<>();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
    }

    public Stage getMainStage(){
        return mainStage;
    }

    /**
     * Method for switching to a new FXML scene in application.
     *
     * @param resourceName fxml file resource to load
     */

    private void switchFXMLScene(String resourceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceName));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            this.mainStage.setScene(scene);
            if (!mainStage.isShowing())
                this.mainStage.show();
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
        switchFXMLScene("startMenuCreate.fxml");
    }

    /**
     * Show game join menu event.
     */
    @Override
    protected void switchToJoin() {
        switchFXMLScene("startMenuJoin.fxml");
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
        myPlayer = new PlayerGUI(username);
        playersInGame.add(myPlayer);
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
        switchFXMLScene("initialScreen.fxml");
    }

    @Override
    protected void enableFirstRoundActions() {
    }

    @Override
    protected void showScene() {

    }

    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        for (PlayerGUI player : playersInGame){
            player.setTokenChoicePopUpScene(availableColors);
        }
    }

    @Override
    protected void updateDrawableArea() {
        String[] resourceDrawableArea = new String[3];
        String[] goldDrawableArea = new String[3];
        for (int i = 0; i < 3; i++) {
            resourceDrawableArea[i] = messageParser.getCardID("resourceDrawableArea", i);
            goldDrawableArea[i] = messageParser.getCardID("goldDrawableArea", i);
        }
        for (PlayerGUI player: playersInGame){
           player.getScene().getDrawableArea().setResourceCardsDrawableArea(resourceDrawableArea);
           player.getScene().getDrawableArea().setGoldCardsDrawableArea(goldDrawableArea);
        }
    }

    @Override
    protected void giveInitialCard(String username) {
        String messageParserCardID = messageParser.getCardID();
        String initialCard = messageParserCardID.concat("F");
        for (PlayerGUI player: playersInGame){
            if (player.getUsername().equals(username)){
                player.getScene().getBoard().getInitialCard().setCardImage(initialCard);
            }
        }
    }

    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) {
        for(String username: playerUsernames){
            if (!myPlayer.getUsername().equals(username)){
                PlayerGUI player = new PlayerGUI(username);
                playersInGame.add(player);
            }
        }
    }

    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs) {
        return usernameOfPlayerWhoseTurnItIs.equals(myPlayer.getUsername());
    }

    @Override
    protected void switchToLoading() {
        switchFXMLScene("loading.fxml");
    }

    @Override
    protected void updatePlayerBoard(String affectedPlayer) {

    }

    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {

    }


}
