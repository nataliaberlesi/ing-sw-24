package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainScene extends Scene {

    private Stage stage;
    private final AnchorPane root;
    private BoardGUI board = new BoardGUI();
    private HandGUI hand = new HandGUI();
    private final ObjectivesSectionGUI objectivesSection = new ObjectivesSectionGUI();
    private DrawableAreaGUI drawableArea = new DrawableAreaGUI();
    private final ScoreBoardGUI scoreBoard = new ScoreBoardGUI();
    private Label turnLabel = new Label();
    private Label actionLabel = new Label();
    private Label helloPlayerLabel;
    private Label confirmActionLabel = new Label();
    private final Button flipYourHandButton = new Button("Flip your hand");
    private final Button confirmActionButton = new Button("Confirm action");
    private final HashMap<PlayerGUI, Button> seeOtherPlayersSceneButtons = new HashMap<>();
    private CardGUI chosenCard = board.getInitialCard();
    private ViewControllerGUI viewControllerGUI;
    private MessageType lastMessageToArrive;
    private TokenChoicePopUp tokenChoicePopUp;
    private PlayerInfo info;

    public MainScene() {
        super(new AnchorPane(), 1060, 595);
        root = (AnchorPane) this.getRoot();
        setUpBackground();
        setUpLabels();
        setUpButtons();
        root.getChildren().add(board);
        root.getChildren().add(drawableArea);
        root.getChildren().add(hand);
        root.getChildren().add(objectivesSection);
        root.getChildren().add(scoreBoard);
    }

    public MainScene(ViewControllerGUI viewControllerGUI) {
        super(new AnchorPane(), 1060, 595);
        this.viewControllerGUI = viewControllerGUI;
        root = (AnchorPane) this.getRoot();
        setUpBackground();
        setUpLabels();
        setUpButtons();
        root.getChildren().add(board);
        root.getChildren().add(drawableArea);
        root.getChildren().add(hand);
        root.getChildren().add(objectivesSection);
        root.getChildren().add(scoreBoard);
    }


    /**
     * Sets up background image
     */
    private void setUpBackground() {
        ImageView background = new ImageView();
        background.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/initialScreenOriginal.jpg"))));
        background.setOpacity(0.1);
        background.setFitHeight(595);
        background.setFitWidth(1060);
        background.setPreserveRatio(true);
        background.setPickOnBounds(true);
        root.getChildren().add(background);
    }

    /**
     * Sets up all labels for the scene
     */
    private void setUpLabels(){
        Label resourceCardsLabel = new Label("Resource Cards");
        resourceCardsLabel.setFont(new Font("System Bold Italic", 16));
        resourceCardsLabel.setAlignment(Pos.CENTER);
        resourceCardsLabel.setLayoutX(912);
        resourceCardsLabel.setLayoutY(52);
        this.root.getChildren().add(resourceCardsLabel);

        Label goldCardsLabel = new Label("Gold Cards");
        goldCardsLabel.setFont(new Font("System Bold Italic", 16));
        goldCardsLabel.setAlignment(Pos.CENTER);
        goldCardsLabel.setLayoutX(924);
        goldCardsLabel.setLayoutY(332);
        this.root.getChildren().add(goldCardsLabel);

        Label handLabel = new Label("Hand");
        handLabel.setFont(new Font("System Bold Italic", 16));
        handLabel.setAlignment(Pos.CENTER);
        handLabel.setLayoutX(370);
        handLabel.setLayoutY(457);
        this.root.getChildren().add(handLabel);

        Label secretObjectiveLabel = new Label("Secret Objective");
        secretObjectiveLabel.setFont(new Font("System Bold Italic", 16));
        secretObjectiveLabel.setAlignment(Pos.CENTER);
        secretObjectiveLabel.setLayoutX(524);
        secretObjectiveLabel.setLayoutY(457);
        this.root.getChildren().add(secretObjectiveLabel);

        Label commonObjectivesLabel = new Label("Common Objectives");
        commonObjectivesLabel.setFont(new Font("System Bold Italic", 16));
        commonObjectivesLabel.setAlignment(Pos.CENTER);
        commonObjectivesLabel.setLayoutX(656);
        commonObjectivesLabel.setLayoutY(457);
        this.root.getChildren().add(commonObjectivesLabel);

        confirmActionLabel.setFont(new Font("System Bold", 14));
        confirmActionLabel.setAlignment(Pos.CENTER);
        confirmActionLabel.setLayoutX(30);
        confirmActionLabel.setLayoutY(547);
        this.root.getChildren().add(confirmActionLabel);

        turnLabel.setFont(new Font("System Bold", 18));
        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.setLayoutX(30);
        turnLabel.setLayoutY(487);
        this.root.getChildren().add(turnLabel);

        actionLabel.setFont(new Font("System Bold", 16));
        actionLabel.setAlignment(Pos.CENTER);
        actionLabel.setLayoutX(30);
        actionLabel.setLayoutY(517);
        this.root.getChildren().add(actionLabel);
    }

    /**
     * Sets up all standard buttons for the scene
     */
    private void setUpButtons() {
        flipYourHandButton.setLayoutX(350);
        flipYourHandButton.setLayoutY(555);
        flipYourHandButton.setMnemonicParsing(false);
        this.root.getChildren().add(flipYourHandButton);

        confirmActionButton.setLayoutX(38);
        confirmActionButton.setLayoutY(230);
        confirmActionButton.setMnemonicParsing(false);
        confirmActionButton.setFont(new Font("System Bold", 16));
        confirmActionButton.setDisable(true);
        this.root.getChildren().add(confirmActionButton);
    }

    /**
     * Sets the turn label to a specific text
     * @param text text
     */
    public void setTurnLabel(String text){
        turnLabel.setText(text);
    }

    /**
     * Sets the hello player label to a specific username
     * @param username username
     */

    public void setHelloPlayerLabel(String username){
        helloPlayerLabel = new Label("Hi "+ username);
        helloPlayerLabel.setFont(new Font("System Bold", 18));
        helloPlayerLabel.setAlignment(Pos.CENTER);
        helloPlayerLabel.setLayoutX(38);
        helloPlayerLabel.setLayoutY(457);
        this.root.getChildren().add(helloPlayerLabel);
    }

    /**
     * Sets the buttons to see other player's scenes to a specific username
     * @param playersInGame players in game
     */
    public void setSeeOtherPlayersGameButtons(ArrayList<PlayerGUI> playersInGame){
        for (int i = 0; i < ViewControllerGUI.numberOfPlayersInGame; i++) {
            seeOtherPlayersSceneButtons.put(playersInGame.get(i), new Button("See " + playersInGame.get(i).getUsername() + "'s game"));
            seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setLayoutX(38);
            seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setLayoutY(277+45*i);
            seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setMnemonicParsing(false);
            int finalI = i;
            if (playersInGame.get(i).equals(viewControllerGUI.getMyPlayer())){
                seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setDisable(true);
                seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setOnMouseClicked(event -> handleSeeMyPlayerScene());
            }
            else  seeOtherPlayersSceneButtons.get(playersInGame.get(i)).setOnMouseClicked(event -> handleSeeOtherPlayerScene(playersInGame.get(finalI)));

            this.root.getChildren().add(seeOtherPlayersSceneButtons.get(playersInGame.get(i)));
        }
    }

    private void handleSeeMyPlayerScene() {
        restoreMyPlayerInfo(viewControllerGUI.getMyPlayer().getMainScene(), info);
        enableActions(this);
        this.stage.setScene(viewControllerGUI.getMyPlayer().getMainScene());
        this.stage.hide();
        this.stage.show();
    }

    private void handleSeeOtherPlayerScene(PlayerGUI otherPlayer) {

//            BoardGUI newBoard = otherPlayer.getMainScene().getBoard(); This does not create new objects, just point to the same ones, problem!!!
            //I can't do that, I need to create a copy of the otherplayer hand and board and dettach all event handlers, then put the copies in my player's scene!
//            CardGUI[] otherPlayerHandCards = otherPlayer.getMainScene().getHand().getHandCards();
//            for (CardGUI card : otherPlayerHandCards){
//                if (card.isFaceUp() && card.getCardID() != null){
//                    card.flipAndShow();
//                }
//            }

        saveMyPlayerInfo(turnLabel.getText(), actionLabel.getText(), confirmActionLabel.getText());
        changeMyPlayerScene(otherPlayer);
        disableAllActions(this); //disable all actions on the copied scene except the button to return to my scene
        this.stage.setScene(this); //set my scene to be the copied scene
        this.stage.hide();
        this.stage.show();
    }

    private void saveMyPlayerInfo(String turnLabel, String actionLabel, String confirmActionLabel) {
        info = new PlayerInfo(turnLabel, actionLabel, confirmActionLabel);
    }

    private void restoreMyPlayerInfo(MainScene scene, PlayerInfo info) {
        scene.setActionLabel(info.actionLabel);
        scene.setConfirmActionLabel(info.confirmActionLabel);
        scene.setTurnLabel(info.turnLabel);
        scene.getBoard().getAnchorPane().getChildren().removeLast();
    }

    private void changeMyPlayerScene(PlayerGUI otherPlayer) {
        this.actionLabel.setText("Click the \"See " + viewControllerGUI.getMyPlayer().getUsername() + " 's game\"\nbutton to go back to your game");
        this.confirmActionLabel.setText("");
        if (otherPlayer.getMainScene().getTurnLabelText().equals("It's your turn!"))
            this.setTurnLabel(otherPlayer.getUsername() + " is playing!");
        else this.setTurnLabel(otherPlayer.getUsername() + " is waiting!");

        WritableImage capturedImage = captureAnchorPaneSnapshot(otherPlayer.getMainScene().getBoard().getAnchorPane());
        displayCapturedImage(this.board.getAnchorPane(), capturedImage);

    }

    private void displayCapturedImage(AnchorPane targetPane, WritableImage image) {
        if (image != null) {
            ImageView imageView = new ImageView(image);
            targetPane.getChildren().add(imageView);
        }
    }

    private WritableImage captureAnchorPaneSnapshot(AnchorPane anchorPane) {
        SnapshotParameters parameters = new SnapshotParameters();
        int width = (int) anchorPane.getPrefWidth();
        int height = (int) anchorPane.getPrefHeight();

        if (width > 0 && height > 0) {
            WritableImage image = new WritableImage(width, height);
            return anchorPane.snapshot(parameters, image);
        }
        return null;
    }

    private void disableAllActions(MainScene scene) {
        scene.confirmActionButton.setDisable(true);
        scene.flipYourHandButton.setDisable(true);
        scene.seeOtherPlayersSceneButtons.get(viewControllerGUI.getMyPlayer()).setDisable(false);
        for (PlayerGUI player : viewControllerGUI.getPlayersInGame()) {
            if (!player.equals(viewControllerGUI.getMyPlayer())) {
                scene.seeOtherPlayersSceneButtons.get(player).setDisable(true);
            }
        }
    }

    private void enableActions(MainScene scene){
        scene.confirmActionButton.setDisable(false);
        scene.flipYourHandButton.setDisable(false);
        this.seeOtherPlayersSceneButtons.get(viewControllerGUI.getMyPlayer()).setDisable(true);
        for (PlayerGUI player : viewControllerGUI.getPlayersInGame()) {
            if (!player.equals(viewControllerGUI.getMyPlayer())) {
                scene.seeOtherPlayersSceneButtons.get(player).setDisable(false);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public BoardGUI getBoard() {
        return board;
    }

    public DrawableAreaGUI getDrawableArea(){
        return drawableArea;
    }

    public ScoreBoardGUI getScoreBoard() {
        return scoreBoard;
    }

    public HandGUI getHand() {
        return hand;
    }

    public ObjectivesSectionGUI getObjectivesSection() {
        return objectivesSection;
    }

    public void handleFirstCardPlacementAndColorChoice() {
        actionLabel.setText("Click on the initial card if you want to flip it");
        enableConfirmButtonClick();
    }

    public void enableConfirmButtonClick(){
        confirmActionButton.setDisable(false);
        confirmActionButton.setOnMouseClicked(event -> onConfirmButtonClicked());
    }

    public void onConfirmButtonClicked(){
        switch (lastMessageToArrive){
            case START_FIRSTROUND, FIRSTROUND -> {
                chosenCard.setOnMouseClicked(null);
                confirmActionButton.setDisable(true);

                setActionLabel("Choose a color by clicking on it");
                setConfirmActionLabel("");
                tokenChoicePopUp = viewControllerGUI.getMyPlayer().getTokenChoicePopUpScene();
                tokenChoicePopUp.setPopUpStage();
                tokenChoicePopUp.popUpStage.setScene(tokenChoicePopUp);
                tokenChoicePopUp.popUpStage.show();
            }
            case START_SECONDROUND -> {

            }
        }
    }

    public void setChosenCard(CardGUI chosenCard){
        this.chosenCard = chosenCard;
    }

    public CardGUI getChosenCard() {
        return chosenCard;
    }

    public void setLastMessageToArrive(MessageType lastMessageToArrive) {
        this.lastMessageToArrive = lastMessageToArrive;
    }

    public void setConfirmActionLabel(String text) {
        this.confirmActionLabel.setText(text);
    }

    public void setActionLabel(String text){
        this.actionLabel.setText(text);

    }
    public String getTurnLabelText(){
        return turnLabel.getText();
    }
    public ViewControllerGUI getViewControllerGUI(){
        return viewControllerGUI;
    }
    public record PlayerInfo(String turnLabel, String actionLabel, String confirmActionLabel){}
}
