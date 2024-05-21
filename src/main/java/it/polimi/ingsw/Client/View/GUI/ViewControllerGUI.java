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
import java.util.concurrent.Semaphore;

/**
 * Class representing the gui view and acting as main controller for JavaFX application.
 */

public class ViewControllerGUI extends ViewController implements Initializable{

    /**
     * Initial application stage.
     */
    private Stage stage;

    private Stage popUpStage;

    /**
     * Alert dialog for errors.
     */
    private Alert errorAlert;

    /**
     * Instance of player to represent my player
     */
    private PlayerGUI myPlayer;
    /**
     * Instance of main scene for the game
     */
    private MainScene mainScene;
    /**
     * Instance of token color choice pop up for the game
     */
    private TokenColorChoice tokenColorChoiceScene;
    /**
     * Instance of private objective choice pop up for the game
     */
    private PrivateObjectiveChoice privateObjectiveChoiceScene;

    /**
     * Players in game
     */
    private final PlayersInGameGUI playersInGame = new PlayersInGameGUI();

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
    protected void setPopUpStage(){
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        popUpStage.setFullScreen(false);
        popUpStage.setTitle("Codex Naturalis");
        popUpStage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
    }

    public Stage getStage(){
        return stage;
    }

    /**
     * Main view method override for JavaFX main thread.
     */
    @Override
    public void updateView(){
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> {
            super.updateView();
            semaphore.release();
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void terminate() {

    }

    @Override
    protected void exit() {

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

    @Override
    protected void connectScene() {
        switchFXMLScene("initialScreen.fxml");
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
     * Switches to loading scene
     */
    @Override
    protected void switchToLoading() {
        switchFXMLScene("loading.fxml");
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
        mainScene = new MainScene(this, myPlayer);
    }


    @Override
    protected void switchWaitingServerResponse() {
        //not implemented for GUI
    }

    @Override
    protected void setCurrentPlayer(String currentPlayer) {
        for(PlayerGUI player: playersInGame.getPlayers()){
            player.setCurrentPlayer(player.getUsername().equalsIgnoreCase(currentPlayer));
        }
    }

    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) {
        for(String username: playerUsernames){
            if (!myPlayer.getUsername().equals(username)){
                PlayerGUI player = new PlayerGUI(username);
                playersInGame.addPlayer(player);
            }
            else playersInGame.addPlayer(myPlayer);
        }
        mainScene.setSeeOtherPlayersGameButtons(playersInGame.getPlayers());
        mainScene.setScoreBoard(playerUsernames);
        mainScene.setHelloPlayerLabel(myPlayer.getUsername());

        playersInGame.getPlayer(playerUsernames.getFirst()).getBoard().setFirstPlayerToken();
    }

    @Override
    protected void giveInitialCard(String username) {
        try {
            PlayerGUI player = playersInGame.getPlayer(username);
            player.getBoard().setInitialCard(messageParser.getCardID());
            CardGUI initialCard = player.getBoard().getInitialCard();
            if (player.equals(myPlayer))
                initialCard.setOnMouseClicked(event -> initialCard.flipAndShow());
            mainScene.setChosenCard(initialCard);
        } catch (RuntimeException e){
            //this happens at end of first round, when server doesn't give a new startingCard and a new currentPlayer
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
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        if (availableColors != null){
            setTokenChoicePopUpScene(availableColors);
        }
    }

    public void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        TokenColorChoice tokenColorChoiceScene = new TokenColorChoice(tokenColors, this);
        popUpStage.setScene(tokenColorChoiceScene);
    }

    @Override
    protected void showScene() {
        stage.setScene(mainScene);
        stage.hide();
        stage.show();
    }

    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs) {
        if (usernameOfPlayerWhoseTurnItIs.equals(myPlayer.getUsername())){
            mainScene.setTurnLabel("It's your turn!");
            mainScene.setConfirmActionLabel("\nUse the confirm button to confirm your choice");
            return true;
        }
        else {
            mainScene.setTurnLabel("Wait for your turn!");
            return false;
        }
    }

    @Override
    protected void enableFirstRoundActions() {
        mainScene.setActionLabel("Click on the initial card if you want to flip it\nChoose a color by clicking on it");
        popUpStage.show();
        mainScene.getConfirmActionButton().setOnMouseClicked(event ->  sendFirstRoundMessageAndUpdateScene());
    }

    protected void sendFirstRoundMessageAndUpdateScene(){
        messageDispatcher.firstRound(myPlayer.getUsername(), mainScene.getChosenCard().isFaceUp(), myPlayer.getColor());
        mainScene.getConfirmActionButton().setDisable(true);
        for (Button button : mainScene.getSeeOtherPlayersSceneButtons())
            button.setDisable(false);
        for (PlayerGUI player : playersInGame.getPlayers())
            player.getBoard().getInitialCard().setOnMouseClicked(null);
        mainScene.setActionLabel("");
    }

    @Override
    protected void updatePlayerBoard(String affectedPlayer) {
        playersInGame.getPlayer(affectedPlayer).getBoard().updateBoard(messageParser.getPlacedCardsGUI());
    }

    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {
        playersInGame.getPlayer(affectedPlayer).getBoard().setPlayerColorToken(color);
    }

    @Override
    protected void updatePlayerHand(String username) {
        if (playersInGame.getPlayer(username).equals(myPlayer))
            playersInGame.getPlayer(username).getHand().updateMyHand(messageParser.getHandIDs());
        else playersInGame.getPlayer(username).getHand().updateMyHandForOtherPlayers(messageParser.getHandIDs());
        mainScene.enableFlipHandButton();
    }

    @Override
    protected void setPublicObjectives() {
        mainScene.getObjectivesSection().setPublicObjectives(messageParser.getPublicObjectivesID());
    }

    @Override
    protected void setPrivateObjectiveChoice() {
    PrivateObjectiveChoice privateObjectiveChoiceScene = new PrivateObjectiveChoice(messageParser.getPrivateObjectivesID(), this);
    popUpStage.setScene(privateObjectiveChoiceScene);
    }

    @Override
    protected void enableSecondRoundActions() {
        mainScene.setActionLabel("Choose your secret objective\nby clicking on it");
        popUpStage.show();
        mainScene.getConfirmActionButton().setOnMouseClicked(event -> sendSecondRoundMessageAndUpdateScene());
    }

    private void sendSecondRoundMessageAndUpdateScene() {
        messageDispatcher.secondRound(myPlayer.getUsername(), myPlayer.getPrivateObjectiveIndex());
        mainScene.getConfirmActionButton().setDisable(true);
        mainScene.setActionLabel("");
        mainScene.getObjectivesSection().getPrivateObjective().setCardIDAndImage(myPlayer.getPrivateObjectiveID());
    }


    @Override
    protected boolean isPlayerInGame(String username) {
        return playersInGame.isPlayerInGame(username);
    }

    @Override
    protected void updatePlayerScore(String username, int score) {
        mainScene.getScoreBoard().updatePlayerScore(username, score);
    }

    @Override
    protected void enablePlaceCard() {

    }

    @Override
    protected void enableDrawCard() {

    }



    @Override
    protected void enablePlaceStartingCard() {

    }

    @Override
    protected void enableChooseColor() {

    }

    @Override
    protected void disableAllActions() {

    }

    @Override
    protected void showFinalScoreBoard() {

    }

    @Override
    protected void setFinalScoreBoard() {

    }
    protected PlayerGUI getMyPlayer(){
        return this.myPlayer;
    }
    protected MessageDispatcher getMessageDispatcher(){
        return this.messageDispatcher;
    }

    public PlayersInGameGUI getPlayersInGame() {
        return playersInGame;
    }

    public TokenColorChoice getTokenChoiceScene() {
        return tokenColorChoiceScene;
    }

    public MainScene getMainScene() {
        return mainScene;
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }
}
