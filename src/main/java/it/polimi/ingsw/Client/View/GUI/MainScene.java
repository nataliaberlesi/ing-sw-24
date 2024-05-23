package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Objects;

public class MainScene extends Scene {

    private final AnchorPane root;
    private final ObjectivesSectionGUI objectivesSection = new ObjectivesSectionGUI();
    private DrawableAreaGUI drawableArea = new DrawableAreaGUI();
    private ScoreBoardGUI scoreBoard;
    private Label turnLabel = new Label();
    private Label actionLabel = new Label();
    private Label helloPlayerLabel;
    private Label confirmActionLabel = new Label();
    private final Button flipYourHandButton = new Button("Flip your hand");
    private final Button confirmActionButton = new Button("Confirm action");
    private final ArrayList<Button> seeOtherPlayersSceneButtons = new ArrayList<>();
    private ViewControllerGUI viewControllerGUI;
    private PlayerGUI playerInScene;
    protected static boolean enableActions = false;

    public MainScene(ViewControllerGUI viewControllerGUI, PlayerGUI playerInScene) {
        super(new AnchorPane(), 1060, 595);
        this.playerInScene = playerInScene;
        this.viewControllerGUI = viewControllerGUI;
        root = (AnchorPane) this.getRoot();
        setUpBackground();
        setUpLabels();
        setUpButtons();
        root.getChildren().add(drawableArea);
        root.getChildren().add(objectivesSection);
        addHandAndBoardToScene();
    }

    /**
     * Adds playerInScene hand and board to scene
     */
    private void addHandAndBoardToScene(){
        root.getChildren().add(playerInScene.getHand());
        root.getChildren().add(playerInScene.getBoard());
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
        handLabel.setLayoutX(380);
        handLabel.setLayoutY(457);
        this.root.getChildren().add(handLabel);

        Label secretObjectiveLabel = new Label("Secret Objective");
        secretObjectiveLabel.setFont(new Font("System Bold Italic", 16));
        secretObjectiveLabel.setAlignment(Pos.CENTER);
        secretObjectiveLabel.setLayoutX(560);
        secretObjectiveLabel.setLayoutY(457);
        this.root.getChildren().add(secretObjectiveLabel);

        Label commonObjectivesLabel = new Label("Common Objectives");
        commonObjectivesLabel.setFont(new Font("System Bold Italic", 16));
        commonObjectivesLabel.setAlignment(Pos.CENTER);
        commonObjectivesLabel.setLayoutX(686);
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
        flipYourHandButton.setDisable(true);
        flipYourHandButton.setOnMouseClicked(event -> flipAndShowHand());
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
        for (int i = 0; i < playersInGame.size(); i++) {
            seeOtherPlayersSceneButtons.add(new Button("See " + playersInGame.get(i).getUsername() + "'s game"));
            seeOtherPlayersSceneButtons.get(i).setLayoutX(38);
            seeOtherPlayersSceneButtons.get(i).setLayoutY(277+45*i);
            seeOtherPlayersSceneButtons.get(i).setMnemonicParsing(false);
            int finalI = i;
            seeOtherPlayersSceneButtons.get(i).setDisable(true);
            seeOtherPlayersSceneButtons.get(i).setOnMouseClicked(event -> handleSeeOtherPlayerScene(playersInGame.get(finalI)));
            this.root.getChildren().add(seeOtherPlayersSceneButtons.get(i));
        }
    }

    private void handleSeeOtherPlayerScene(PlayerGUI otherPlayer) {
        root.getChildren().remove(playerInScene.getHand());
        root.getChildren().remove(playerInScene.getBoard());
        playerInScene = otherPlayer;
        enableConfirmButtonClick();
        enableFlipHandButton();
        addHandAndBoardToScene();
        viewControllerGUI.showScene();
    }

    public DrawableAreaGUI getDrawableArea(){
        return drawableArea;
    }

    public ScoreBoardGUI getScoreBoard() {
        return scoreBoard;
    }


    public ObjectivesSectionGUI getObjectivesSection() {
        return objectivesSection;
    }

    public void enableFlipHandButton(){
        if (playerInScene.equals(viewControllerGUI.getMyPlayer()))
            flipYourHandButton.setDisable(false);
        else flipYourHandButton.setDisable(true);
    }

    private void flipAndShowHand() {
        HandGUI hand = viewControllerGUI.getMyPlayer().getHand();
        for (CardGUI card : hand.getHandCards()){
            if (card.getCardID() != null)
                card.flipAndShow();
            if (card.isSelected) {
                card.setBorder(null);
                card.isSelected = false;
                enableActions = false;
                enableConfirmButtonClick();
            }
        }
    }

    public void enableConfirmButtonClick(){
        if (playerInScene.equals(viewControllerGUI.getMyPlayer()) && playerInScene.isCurrentPlayer() && enableActions)
            confirmActionButton.setDisable(false);
        else confirmActionButton.setDisable(true);
    }

    public void addEventHandlerToHandCards(){
        HandGUI hand = viewControllerGUI.getMyPlayer().getHand();
        CardGUI[] handCards = hand.getHandCards();
        for ( int i = 0; i < handCards.length; i++){
            int finalI = i;
            CardGUI handCard = handCards[i];
            handCard.setOnMouseClicked(event -> {
                handCard.toggleSelection(handCard, hand);
                boolean isHandCardChosen = hand.setChosenHandCard(handCard);
                hand.setIndex(finalI);
                enableCornerChoice();
                setEventHandlerToBoardCards();
                if (isHandCardChosen && CornerGUI.atLeastOneCornerSelected){
                    enableActions = true;
                    enableConfirmButtonClick();
                }else{
                    enableActions = false;
                    enableConfirmButtonClick();
                }
            });
        }
    }

    private void enableCornerChoice() {
        setActionLabel("Click on the corner where\nyou'd like to place your card");
    }
    public void setEventHandlerToBoardCards() {
        for (CardGUI card : viewControllerGUI.getMyPlayer().getBoard().getCardsOnBoard()){
            addEventHandlerToCorners(card);
        }
    }
    public void addEventHandlerToCorners(CardGUI card){
        CornerGUI[] corners = card.getCorners();
        for (int i = 0; i < corners.length; i++) {
            int finalI = i;
            corners[i].setOnMouseClicked(event -> handleCornerClick(corners[finalI], card));
        }
    }

    private void handleCornerClick(CornerGUI corner, CardGUI card) {
        boolean atLeastOneCornerSelected = corner.toggleSelection(corner, viewControllerGUI.getMyPlayer().getBoard(), this);
        viewControllerGUI.getMyPlayer().getBoard().setChosenCard(card);
        card.setChosenCornerCoordinates(corner.cornerCoordinates);
        if (atLeastOneCornerSelected && viewControllerGUI.getMyPlayer().getHand().getChosenHandCard().isSelected){
            enableActions = true;
            enableConfirmButtonClick();
        }else{
            enableActions = false;
            enableConfirmButtonClick();
        }
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

    public ArrayList<Button> getSeeOtherPlayersSceneButtons() {
        return seeOtherPlayersSceneButtons;
    }

    public ViewControllerGUI getViewControllerGUI(){
        return viewControllerGUI;
    }

    public void setPlayerInScene(PlayerGUI player) {
        this.playerInScene = player;
    }

    public PlayerGUI getPlayerInScene() {
        return playerInScene;
    }

    public void setScoreBoard(ArrayList<String> playerUsernames) {
        scoreBoard = new ScoreBoardGUI(playerUsernames);
        root.getChildren().add(scoreBoard);
    }

    public Button getConfirmActionButton() {
        return confirmActionButton;
    }

}
