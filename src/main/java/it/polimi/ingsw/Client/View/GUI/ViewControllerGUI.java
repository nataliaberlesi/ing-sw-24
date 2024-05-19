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
     * Initial application stage.
     */
    private Stage stage;

    /**
     * Alert dialog for errors.
     */
    private Alert errorAlert;

    /**
     * Instance of player to represent my player
     */
    private PlayerGUI myPlayer;

    private MainScene mainScene;
    private TokenChoicePopUp tokenChoicePopUpScene;

    /**
     * Array list of players to represent players in game
     */
    private final ArrayList<PlayerGUI> playersInGame = new ArrayList<>();

    protected static int numberOfPlayersInGame;

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
    }

    protected void setStage(Stage stage){
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

    private void switchFXMLScene(String resourceName) {
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
        mainScene = new MainScene(this);
        mainScene.setPlayerInScene(myPlayer);
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
    protected void setCurrentPlayer(String currentPlayer) {

    }

    @Override
    protected boolean isPlayerInGame(String username) {
        return false;
    }

    @Override
    protected void updatePlayerScore(String username, int score) {

    }

    @Override
    protected void setPublicObjectives() {

    }

    @Override
    protected void updatePlayerHand(String username) {

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
        mainScene.setLastMessageToArrive(messageParser.getMessageType());
        mainScene.handleFirstCardPlacementAndColorChoice();
    }

    @Override
    protected void showScene() {
        stage.setScene(mainScene);
        stage.hide();
        stage.show();
    }
    public void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        this.tokenChoicePopUpScene = new TokenChoicePopUp(tokenColors, mainScene);
    }

    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        if (availableColors != null){
            setTokenChoicePopUpScene(availableColors);
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
           mainScene.getDrawableArea().setResourceCardsDrawableArea(resourceDrawableArea);
           mainScene.getDrawableArea().setGoldCardsDrawableArea(goldDrawableArea);

    }

    @Override
    protected void giveInitialCard(String username) {
        try {
            String initialCardID = messageParser.getCardID();
            for (PlayerGUI player : playersInGame) {
                if (player.getUsername().equalsIgnoreCase(username)) {
                    CardGUI initialCard = player.getBoard().getInitialCard();
                    initialCard.setCardID(initialCardID);
                    initialCard.setCardImage();
                }
            }
        } catch (RuntimeException e){
            //this happens at end of first round, when server doesn't give a new startingCard and a new currentPlayer
        }
    }

    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) {
        for(String username: playerUsernames){
            if (!myPlayer.getUsername().equals(username)){
                PlayerGUI player = new PlayerGUI(username);
                playersInGame.add(player);
            }
            else playersInGame.add(myPlayer);
        }
        numberOfPlayersInGame = playersInGame.size();
        mainScene.getScoreBoard().setUsernames(playerUsernames); //sets score to 0 for all players
        mainScene.setSeeOtherPlayersGameButtons(playersInGame);
        mainScene.setHelloPlayerLabel(myPlayer.getUsername());
        for (PlayerGUI player : playersInGame){
            if (player.getUsername().equals(playerUsernames.getFirst())){
                player.getBoard().setFirstPlayerToken();
            }
        }
    }

    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs) {
        if (usernameOfPlayerWhoseTurnItIs.equals(myPlayer.getUsername())){
            mainScene.setTurnLabel("It's your turn!");
            mainScene.setConfirmActionLabel("Use the confirm action button to confirm your choice");
            return true;
        }
        else {
        mainScene.setTurnLabel("Wait for your turn!");
        return false;
        }
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
        for (PlayerGUI player : playersInGame){
            if (player.getUsername().equals(affectedPlayer)){
                player.getBoard().setPlayerColorToken(color);
            }
        }
    }
    protected PlayerGUI getMyPlayer(){
        return this.myPlayer;
    }
    protected MessageDispatcher getMessageDispatcher(){
        return this.messageDispatcher;
    }

    public ArrayList<PlayerGUI> getPlayersInGame() {
        return playersInGame;
    }

    public TokenChoicePopUp getTokenChoicePopUpScene() {
        return tokenChoicePopUpScene;
    }
}
