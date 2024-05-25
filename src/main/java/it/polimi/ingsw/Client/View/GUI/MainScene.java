package it.polimi.ingsw.Client.View.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainScene extends Scene {

    private final AnchorPane root;
    private final ObjectivesSectionGUI objectivesSection = new ObjectivesSectionGUI();
    private DrawableAreaGUI drawableArea = new DrawableAreaGUI();
    private ScoreBoardGUI scoreBoard;
    private final Label turnLabel = new Label();
    private final Label actionLabel = new Label();
    private final Label confirmActionLabel = new Label();
    private final Label endGameLabel = new Label("End Game!");
    private final Button flipYourHandButton = new Button("Flip your hand");
    private final Button confirmActionButton = new Button("Confirm action");
    private final Button chatButton = new Button("Chat");
    private final ArrayList<Button> seeOtherPlayersSceneButtons = new ArrayList<>();
    private ViewControllerGUI viewControllerGUI;
    private PlayerGUI playerInScene;
    protected static boolean enableActions = false;
    protected static boolean atLeastOneCornerSelected = false;

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
        StaticsForGUI.setLabelCharacteristics(resourceCardsLabel, "System Bold Italic", 16, 912,52);

        Label goldCardsLabel = new Label("Gold Cards");
        StaticsForGUI.setLabelCharacteristics(goldCardsLabel, "System Bold Italic", 16, 924,332);

        Label handLabel = new Label("Hand");
        StaticsForGUI.setLabelCharacteristics(handLabel, "System Bold Italic", 16, 380,457);

        Label secretObjectiveLabel = new Label("Secret Objective");
        StaticsForGUI.setLabelCharacteristics(secretObjectiveLabel, "System Bold Italic", 16, 560,457);

        Label commonObjectivesLabel = new Label("Common Objectives");
        StaticsForGUI.setLabelCharacteristics(commonObjectivesLabel, "System Bold Italic", 16, 686,457);

        StaticsForGUI.setLabelCharacteristics(confirmActionLabel, "System Bold", 14, 30,547);

        StaticsForGUI.setLabelCharacteristics(turnLabel, "System Bold", 18, 30,487);

        StaticsForGUI.setLabelCharacteristics(actionLabel, "System Bold", 16, 30,517);

        StaticsForGUI.setLabelCharacteristics(endGameLabel, "System Bold", 18, 45,180);
        endGameLabel.setVisible(false);

        this.root.getChildren().addAll(resourceCardsLabel, goldCardsLabel, handLabel, secretObjectiveLabel, commonObjectivesLabel,
                confirmActionLabel, turnLabel, actionLabel, endGameLabel);

    }

    /**
     * Sets up all standard buttons for the scene
     */
    private void setUpButtons() {
        StaticsForGUI.setButtonCharacteristics(flipYourHandButton, "System", 12, 350, 555, true);
        flipYourHandButton.setOnMouseClicked(event -> flipAndShowHand());

        StaticsForGUI.setButtonCharacteristics(confirmActionButton, "System Bold", 16, 38, 230, true);

        StaticsForGUI.setButtonCharacteristics(chatButton, "System Bold", 16, 38, 185, false);
        chatButton.setOnMouseClicked(event -> {
            viewControllerGUI.showUpdatedChat();
            viewControllerGUI.setSceneOnStage(viewControllerGUI.getChatScene(), viewControllerGUI.getChatStage());
        });

        this.root.getChildren().addAll(flipYourHandButton, confirmActionButton);
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
        Label helloPlayerLabel = new Label("Hi " + username);
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
                confirmActionButton.setDisable(true);
                enableActions = false;
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
                hand.setChosenHandCard(handCard);
                hand.setChosenHandCardIndex(finalI);
                if (atLeastOneCornerSelected && viewControllerGUI.getMyPlayer().getHand().getChosenHandCard().isSelected){
                    enableActions = true;
                    confirmActionButton.setDisable(false);
                }else{
                    enableActions = false;
                    confirmActionButton.setDisable(true);
                }
            });
        }
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
            corners[i].setOnMouseClicked(event -> {
                handleCornerClick(corners[finalI], card);
                event.consume();});
        }
    }

    public void handleCornerClick(CornerGUI corner, CardGUI card) {
        MainScene.atLeastOneCornerSelected = corner.toggleSelection(corner, viewControllerGUI.getMyPlayer().getBoard());
        viewControllerGUI.getMyPlayer().getBoard().setChosenCard(card);
        card.setChosenCornerCoordinates(corner.cornerCoordinates);
        if (MainScene.atLeastOneCornerSelected && viewControllerGUI.getMyPlayer().getHand().getChosenHandCard().isSelected){
            MainScene.enableActions = true;
            confirmActionButton.setDisable(false);
        }else{
            MainScene.enableActions = false;
            confirmActionButton.setDisable(true);
        }
    }


    public void addEventHandlerToDrawableAreaCards() {
        AtomicBoolean isResourceCardChosen = new AtomicBoolean(false);
        AtomicBoolean isGoldCardChosen = new AtomicBoolean(false);
        CardGUI[] resourceCards = drawableArea.getResourceCards();
        CardGUI[] goldCards = drawableArea.getGoldCards();
        for (int i = 0; i < resourceCards.length; i++){
            int finalI = i;
            resourceCards[i].setOnMouseClicked(event -> {
                drawableArea.setChosenDrawableAreaCardIndex(finalI);
                resourceCards[finalI].toggleSelection(resourceCards[finalI], drawableArea);
                isResourceCardChosen.set(drawableArea.setChosenDrawableAreaCard(resourceCards[finalI], true));
                checkForSelectedCards(isResourceCardChosen.get(), isGoldCardChosen.get());
            });
        }

        for (int i = 0; i < goldCards.length; i++){
            int finalI = i;
            goldCards[i].setOnMouseClicked(event -> {
                drawableArea.setChosenDrawableAreaCardIndex(finalI);
                goldCards[finalI].toggleSelection(goldCards[finalI], drawableArea);
                isGoldCardChosen.set(drawableArea.setChosenDrawableAreaCard(goldCards[finalI], false));
                checkForSelectedCards(isResourceCardChosen.get(), isGoldCardChosen.get());
            });
        }
    }

    private void checkForSelectedCards(boolean isResourceCardChosen, boolean isGoldCardChosen) {
        if (isResourceCardChosen || isGoldCardChosen){
            enableActions = true;
            enableConfirmButtonClick();
        } else{
            enableActions = false;
            enableConfirmButtonClick();
        }
    }


    public Label getEndGameLabel() {
        return endGameLabel;
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
