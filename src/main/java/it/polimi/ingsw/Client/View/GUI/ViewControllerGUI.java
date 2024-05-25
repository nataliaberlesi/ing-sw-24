package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;
import javafx.application.Platform;
import javafx.event.Event;
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

    /**
     * Pop up stage
     */
    private Stage popUpStage;

    /**
     * Chat stage
     */
    private Stage chatStage;

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
     * Instance of chat scene for the game
     */
    private ChatGUI chatScene;
    /**
     * Players in game
     */
    private final PlayersInGameGUI playersInGame = new PlayersInGameGUI();
    /**
     * Number of players ChoiceBox.
     */
    @FXML
    private ChoiceBox<Integer> numberOfPlayersChoiceBox;
    /**
     * Username TextField.
     */
    @FXML
    private TextField usernameField;

    /**
     * Constructor for ViewControllerGUI class
     * @param messageParser messageParser instance
     * @param messageDispatcher messageDispatcher instance
     */
    public ViewControllerGUI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
    }

    /**
     * Sets initial stage that will be used for different scenes throughout the game
     * @param stage stage
     */
    protected void setStage(Stage stage){
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> exit());
        this.numberOfPlayersChoiceBox = new ChoiceBox<>();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        setStageCharacteristics(this.stage);
    }

    /**
     * Sets stage's characteristics
     * @param stage stage to be set
     */
    private void setStageCharacteristics(Stage stage) {
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setTitle("Codex Naturalis");
        stage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
    }

    /**
     * Called to exit the game when abort message is received
     */
    @Override
    protected void exit() {
        if (myPlayer != null)
            messageDispatcher.abortGame(myPlayer.getUsername(), myPlayer.getUsername() + " doesn't want to play anymore :(");
        else messageDispatcher.abortGame(null, "A player has disconnected");
        terminate();
    }

    /**
     *  Terminates client and JavaFX application
     */
    @Override
    protected void terminate() {
        this.popUpStage.close();
        this.stage.hide();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Initializes popUpStage that will be used for different scenes throughout the game
     */
    protected void setPopUpStage(){
        popUpStage = new Stage();
        popUpStage.setOnCloseRequest(Event::consume);
        setStageCharacteristics(popUpStage);
    }

    protected void setChatStage(){
        chatStage = new Stage();
        setStageCharacteristics(chatStage);
    }

    /**
     * Getter for main application stage
     * @return stage
     */
    public Stage getStage(){
        return stage;
    }

    /**
     * Getter for popUpStage
     * @return popUpStage
     */
    public Stage getPopUpStage() {
        return popUpStage;
    }

    /**
     * Getter for chat stage
     * @return stage
     */
    public Stage getChatStage(){
        return chatStage;
    }

    /**
     * Getter for main scene
     * @return main scene
     */
    public MainScene getMainScene() {
        return mainScene;
    }

    /**
     * Getter for chat scene
     * @return chat scene
     */
    public ChatGUI getChatScene() {
        return chatScene;
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
    protected void showUpdatedChat() {

    }

    @Override
    protected void addMessageToChat(String sender, String receiver, String bodyOfMessage, boolean isPrivate) {

    }

    /**
     * Sets a scene on a stage
     * @param scene sce to be set
     * @param stage stage where to set the scene
     */
    protected void setSceneOnStage(Scene scene, Stage stage){
        stage.setScene(scene);
        stage.hide();
        stage.show();
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
            setSceneOnStage(scene, stage);
        } catch (IOException e){
            throw new RuntimeException("Error while trying to switch scene");
        }
    }

    /**
     * Creates and shows initial scene
     */
    @Override
    protected void connectScene() {
        InitialScene initialScene = new InitialScene(this);
        setSceneOnStage(initialScene, stage);
    }

    /**
     * In case of persistence, creates and shows initial scene with secondary constructor
     */
    @Override
    protected void askCreateOrContinue() {
        InitialScene initialSceneWithPersistence = new InitialScene(this, popUpStage);
        setSceneOnStage(initialSceneWithPersistence, stage);
    }

    /**
     * Sends persistence answer to server
     * @param answer player's answer
     */
    protected void sendPersistenceMessage(boolean answer){
        messageDispatcher.notifyPersistence(answer);
    }

    /**
     * Verifies if players in game is empty
     * @return true if there are no players in game, false otherwise
     */
    @Override
    protected boolean playersInfoNotYetAdded() {
        return playersInGame.getPlayers().isEmpty();
    }

    /**
     * Verifies if is my player
     * @param username username of player
     * @return true if username of player is the same as username of my player, false otherwise
     */
    @Override
    protected boolean isMyPlayer(String username) {
        return username.equalsIgnoreCase(myPlayer.getUsername());
    }

    /**
     * Sets private objective to player in case of persistence
     */
    @Override
    protected void setPrivateObjective() {
        myPlayer.setPrivateObjectiveID(messageParser.getChosenObjectiveID());
        mainScene.getObjectivesSection().getPrivateObjective().setCardIDAndImage(myPlayer.getPrivateObjectiveID());
    }

    /**
     * Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */
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
        String username = this.usernameField.getCharacters().toString().toUpperCase();
        super.checkParamsAndSendCreate(username, numberOfPlayers);
    }

    /**
     * Confirm join event
     */
    @FXML
    protected void confirmJoin() {
        String username = this.usernameField.getCharacters().toString().toUpperCase();
        super.checkParamsAndSendJoin(username);
    }

    /**
     * Switches to loading scene
     */
    @Override
    protected void switchToLoading() {
        LoadingScene loadingScene = new LoadingScene();
        setSceneOnStage(loadingScene, stage);
    }

    /**
     * Sets choice box
     * @param url url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfPlayersChoiceBox.getItems().addAll(2,3,4);
    }

    /**
     * Shows error alert message.
     * @param header header text
     * @param content content text
     */
    @Override
    protected void showErrorAlert(String header, String content) {
        this.errorAlert.setHeaderText(header);
        this.errorAlert.setContentText(content);
        this.errorAlert.showAndWait();
    }

    /**
     * Creates the player of the current running client
     * @param username username of player
     */
    @Override
    protected void createMyPlayer(String username) {
        myPlayer = new PlayerGUI(username.toUpperCase());
        mainScene = new MainScene(this, myPlayer);
    }

    /**
     * Notifies client that view is awaiting server to check the username
     */
    @Override
    protected void switchWaitingServerResponse() {
        //not implemented for GUI
    }

    /**
     * Sets current player in ongoing game
     * @param currentPlayer current player
     */
    @Override
    protected void setCurrentPlayer(String currentPlayer) {
        for(PlayerGUI player: playersInGame.getPlayers()){
            player.setCurrentPlayer(player.getUsername().equalsIgnoreCase(currentPlayer));
        }
    }

    /**
     * Adds all players to playersInGame for ongoing game
     * @param playerUsernames usernames of all players in game
     */
    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) {
        for(String username: playerUsernames){
            if (!myPlayer.getUsername().equals(username)){
                PlayerGUI player = new PlayerGUI(username.toUpperCase());
                playersInGame.addPlayer(player);
            }
            else playersInGame.addPlayer(myPlayer);
        }
        mainScene.setSeeOtherPlayersGameButtons(playersInGame.getPlayers());
        mainScene.setScoreBoard(playerUsernames);
        mainScene.setHelloPlayerLabel(myPlayer.getUsername());
    }

    /**
     * Gives initial card to current player and sets its event handler
     * @param username username of current player
     */
    @Override
    protected void giveInitialCard(String username) {
        try {
            PlayerGUI player = playersInGame.getPlayer(username);
            player.getBoard().setInitialCard(messageParser.getCardID());
            CardGUI initialCard = player.getBoard().getInitialCard();
            if (player.equals(myPlayer))
                initialCard.setOnMouseClicked(event -> initialCard.flipAndShow());
            myPlayer.getBoard().setChosenCard(initialCard);
            playersInGame.getPlayers().getFirst().getBoard().setFirstPlayerToken();
        } catch (RuntimeException e){
            //this happens at end of first round, when server doesn't give a new startingCard and a new currentPlayer
        }
    }

    /**
     * Updates drawable area in main scene for all players
     */
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

    /**
     * Updates available colors in popUpScene
     * @param availableColors available token colors to choose from
     */
    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        if (availableColors != null){
            setTokenChoicePopUpScene(availableColors);
        }
    }

    /**
     * Initializes token color choice scene and adds it to the popUpStage
     * @param tokenColors available token colors to choose from
     */
    private void setTokenChoicePopUpScene(ArrayList<String> tokenColors) {
        TokenColorChoice tokenColorChoiceScene = new TokenColorChoice(tokenColors, this);
        popUpStage.setScene(tokenColorChoiceScene);
    }

    /**
     * Shows main scene
     */
    @Override
    protected void showScene() {
        stage.setScene(mainScene);
        stage.hide();
        stage.show();
    }

    /**
     * Verifies if it's my player's turn and sets turn label adequately
     * @param usernameOfPlayerWhoseTurnItIs username of player whose turn it is
     * @return true if it's my player's turn, false otherwise
     */
    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs) {
        if (usernameOfPlayerWhoseTurnItIs.equals(myPlayer.getUsername())){
            mainScene.setTurnLabel("It's your turn!");
            mainScene.setConfirmActionLabel("\nUse the confirm button to confirm your choice");
            return true;
        }
        else {
            mainScene.setTurnLabel("Wait for your turn!\n"+usernameOfPlayerWhoseTurnItIs+" is playing");
            mainScene.setConfirmActionLabel("");
            mainScene.setActionLabel("");
            return false;
        }
    }

    /**
     * Sets first round action label on main scene, shows token color choice pop up, sets event handler for confirm button in main scene
     */
    @Override
    protected void enableFirstRoundActions() {
        mainScene.setActionLabel("Click on the initial card if you want to flip it\nChoose a color by clicking on it");
        popUpStage.show();
        mainScene.getConfirmActionButton().setOnMouseClicked(event ->  sendFirstRoundMessageAndUpdateScene());
    }

    /**
     * Sends first round message to server and updates main scene accordingly
     */
    private void sendFirstRoundMessageAndUpdateScene(){
        messageDispatcher.firstRound(myPlayer.getUsername(), myPlayer.getBoard().getChosenCard().isFaceUp(), myPlayer.getColor());
        mainScene.getConfirmActionButton().setDisable(true);
        MainScene.enableActions = false;
        for (Button button : mainScene.getSeeOtherPlayersSceneButtons())
            button.setDisable(false);
        myPlayer.getBoard().getInitialCard().setOnMouseClicked(null);
        mainScene.setActionLabel("");
    }

    /**
     * Updates the board of affected player
     * @param affectedPlayer last player to do actions on their board
     */
    @Override
    protected void updatePlayerBoard(String affectedPlayer) {
        if (playersInGame.getPlayer(affectedPlayer).getBoard().getInitialCard() == null) //only in case of persistence
            playersInGame.getPlayer(affectedPlayer).getBoard().setInitialCard(messageParser.getPlacedCardsGUI().getFirst().getCardID());

        playersInGame.getPlayer(affectedPlayer).getBoard().updateBoard(messageParser.getPlacedCardsGUI());

        playersInGame.getPlayers().getFirst().getBoard().setFirstPlayerToken();
        if (playersInGame.getPlayer(affectedPlayer).getColor() != null)
            setPlayerColor(affectedPlayer, playersInGame.getPlayer(affectedPlayer).getColor());
        else { //only in case of persistence
            playersInGame.getPlayer(affectedPlayer).setColor(messageParser.getColor());
            setPlayerColor(affectedPlayer, playersInGame.getPlayer(affectedPlayer).getColor());
        }
    }

    /**
     * Sets player color to chosen color in pop up scene
     * @param affectedPlayer player that choose color
     * @param color color chosen
     */
    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {
        playersInGame.getPlayer(affectedPlayer).getBoard().setPlayerColorToken(color);
    }

    /**
     * Updates hand of player to all players in game
     * @param username player to update hand
     */
    @Override
    protected void updatePlayerHand(String username) {
        if (playersInGame.getPlayer(username).equals(myPlayer))
            playersInGame.getPlayer(username).getHand().updateMyHand(messageParser.getHandIDs());
        else playersInGame.getPlayer(username).getHand().updateMyHandForOtherPlayers(messageParser.getHandIDs());
        mainScene.enableFlipHandButton();
    }

    /**
     * Sets public objectives in main scene
     */
    @Override
    protected void setPublicObjectives() {
        mainScene.getObjectivesSection().setPublicObjectives(messageParser.getPublicObjectivesID());
    }

    /**
     * Initializes private objectives choice scene and sets it on pop up stage
     */
    @Override
    protected void setPrivateObjectiveChoice() {
    PrivateObjectiveChoice privateObjectiveChoiceScene = new PrivateObjectiveChoice(messageParser.getPrivateObjectivesID(), this);
    popUpStage.setScene(privateObjectiveChoiceScene);
    }

    /**
     * Sets second round action label on main scene, shows private objectives choice pop up, sets event handler for confirm button in main scene
     */
    @Override
    protected void enableSecondRoundActions() {
        mainScene.setActionLabel("Choose your secret objective\nby clicking on it");
        popUpStage.show();
        mainScene.getConfirmActionButton().setOnMouseClicked(event -> sendSecondRoundMessageAndUpdateScene());
    }

    /**
     * Sends second round message to server and updates main scene accordingly
     */
    private void sendSecondRoundMessageAndUpdateScene() {
        MainScene.enableActions = false;
        mainScene.getConfirmActionButton().setDisable(true);
        mainScene.getObjectivesSection().getPrivateObjective().setCardIDAndImage(myPlayer.getPrivateObjectiveID());
        messageDispatcher.secondRound(myPlayer.getUsername(), myPlayer.getPrivateObjectiveIndex());
    }

    /**
     * Adjusts main scene to place card action and enables confirm action button
     */
    @Override
    protected void enablePlaceCard() {
        mainScene.setActionLabel("Choose a card from your hand\nand a corner to place it on the board");
        mainScene.addEventHandlerToHandCards();
        mainScene.setEventHandlerToBoardCards();
        mainScene.getConfirmActionButton().setOnMouseClicked(event -> sendPlaceCardMessageAndUpdateScene());
    }

    /**
     * Sends place card message to server and updates main scene accordingly
     */
    private void sendPlaceCardMessageAndUpdateScene() {
        MainScene.enableActions = false;
        mainScene.getConfirmActionButton().setDisable(true);
        messageDispatcher.placeCard(myPlayer.getUsername(), myPlayer.getHand().getChosenHandCard().isFaceUp(), myPlayer.getHand().getChosenHandCardIndex(), myPlayer.getBoard().getChosenCard().getChosenCornerCoordinates());
        myPlayer.getHand().deactivateEventHandlerOnHandCards();
        myPlayer.getBoard().deactivateEventHandlerOnCorners();

    }

    /**
     * Checks if a player is present in the game
     * @param username username to check
     * @return true if player with username to check is in the game, false otherwise
     */
    @Override
    protected boolean isPlayerInGame(String username) {
        return playersInGame.isPlayerInGame(username);
    }

    /**
     * Updates score board in main scene, sets end game label if a player goes over 20 points
     * @param username player's username to update score
     * @param score score to be updated
     */
    @Override
    protected void updatePlayerScore(String username, int score) {
        if (score >= 20){
            mainScene.getEndGameLabel().setVisible(true);
        }
        mainScene.getScoreBoard().updatePlayerScore(username, score);
    }

    /**
     * Adjusts main scene to draw card action and enables confirm action button
     */
    @Override
    protected void enableDrawCard() {
        mainScene.setActionLabel("Draw a resource or gold card!");
        mainScene.addEventHandlerToDrawableAreaCards();
        mainScene.getConfirmActionButton().setOnMouseClicked(event -> sendDrawCardMessageAndUpdateScene());
    }

    /**
     * Sends draw card message to server and updates main scene accordingly
     */
    private void sendDrawCardMessageAndUpdateScene() {
        MainScene.enableActions = false;
        mainScene.enableConfirmButtonClick();
        mainScene.getDrawableArea().deactivateEventHandlerOnDrawableArea();
        messageDispatcher.drawCard(myPlayer.getUsername(), mainScene.getDrawableArea().getChosenDrawableAreaCardIndex(), mainScene.getDrawableArea().getChosenDrawableArea());
    }

    @Override
    protected void showFinalScoreBoard() {

    }

    /**
     * Sets and show final scene with winners and scoreboard
     */
    @Override
    protected void setFinalScoreBoard() {
        EndGameScene endGameScene = new EndGameScene();
        endGameScene.setScoreBoard(messageParser.getFinalScoreBoardGUI(), myPlayer.getUsername());
        setSceneOnStage(endGameScene, stage);
    }

    /**
     * Getter for my player, i.e., player associated with this instance of view controller
     * @return my player
     */
    protected PlayerGUI getMyPlayer(){
        return this.myPlayer;
    }


}
