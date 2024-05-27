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

    /**
     * Root of the scene
     */
    private final AnchorPane root;
    /**
     * Instance of ObjectivesSectionGUI that will be used in scene
     */
    private final ObjectivesSectionGUI objectivesSection = new ObjectivesSectionGUI();
    /**
     * Instance of DrawableAreaGUI that will be used in scene
     */
    private final DrawableAreaGUI drawableArea = new DrawableAreaGUI();
    /**
     * Instance of ScoreBoardGUI that will be used in scene
     */
    private ScoreBoardGUI scoreBoard;
    /**
     * Label to state if it is the player's turn or not
     */
    private final Label turnLabel = new Label();
    /**
     * Label to explain actions to players
     */
    private final Label actionLabel = new Label();
    /**
     * Label to explain confirm button to players
     */
    private final Label confirmActionLabel = new Label();
    /**
     * Label to warn players that end game has started
     */
    private final Label endGameLabel = new Label("End Game!");
    /**
     * Button that allows players to flip their hand
     */
    private final Button flipYourHandButton = new Button("Flip your hand");
    /**
     * Button that confirms player's actions and sends messages to server
     */
    private final Button confirmActionButton = new Button("Confirm action");
    /**
     * Button to interact with chat
     */
    private final Button chatButton = new Button("Chat");
    /**
     * Buttons to see other player's board and flipped hand and to go back to player's scene
     */
    private final ArrayList<Button> seeOtherPlayersSceneButtons = new ArrayList<>();
    /**
     * Instance of ViewControllerGUI to interact with controller
     */
    private final ViewControllerGUI viewControllerGUI;
    /**
     * Player who is currently appearing on main scene
     */
    private PlayerGUI playerInScene;


    /**
     * Constructor for main scene
     * @param viewControllerGUI view controller gui instance
     * @param playerInScene player that is appearing on main scene
     */
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

        StaticsForGUI.setLabelCharacteristics(endGameLabel, "System Bold", 18, 55,20);
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

        StaticsForGUI.setButtonCharacteristics(chatButton, "System Bold", 16, 65, 180, true);
        chatButton.setOnMouseClicked(event -> {
            viewControllerGUI.openChat();
            chatButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        });

        this.root.getChildren().addAll(flipYourHandButton, confirmActionButton, chatButton);
    }

    /**
     * Sets the hello player label to a specific username and adds it to main scene
     * @param username username
     */
    public void setHelloPlayerLabel(String username){
        Label helloPlayerLabel = new Label("Hi " + username);
        StaticsForGUI.setLabelCharacteristics(helloPlayerLabel, "System Bold", 18, 38, 457);
        this.root.getChildren().add(helloPlayerLabel);
    }

    /**
     * Sets the buttons to see other player's scenes to a specific username
     * @param playersInGame players in game
     */
    public void setSeeOtherPlayersGameButtons(ArrayList<PlayerGUI> playersInGame){
        for (int i = 0; i < playersInGame.size(); i++) {
            seeOtherPlayersSceneButtons.add(new Button("See " + playersInGame.get(i).getUsername() + "'s game"));
            StaticsForGUI.setButtonCharacteristics(seeOtherPlayersSceneButtons.get(i), "System", 12, 38, 277+45*i, true);
            int finalI = i;
            seeOtherPlayersSceneButtons.get(i).setOnAction(event -> handleSeeOtherPlayerScene(playersInGame.get(finalI)));
            this.root.getChildren().add(seeOtherPlayersSceneButtons.get(i));
        }
    }

    /**
     * Handles the request to see other player's scene
     * @param otherPlayer player requested to see scene
     */
    private void handleSeeOtherPlayerScene(PlayerGUI otherPlayer) {
        root.getChildren().remove(playerInScene.getHand());
        root.getChildren().remove(playerInScene.getBoard());
        playerInScene = otherPlayer;
        enableConfirmButtonClick();
        enableFlipHandButton();
        enableChatButton();
        addHandAndBoardToScene();
        viewControllerGUI.showScene();
    }

    public void enableChatButton() {
        if (playerInScene.equals(viewControllerGUI.getMyPlayer()))
            chatButton.setDisable(false);
        else chatButton.setDisable(true);
    }

    /**
     * Enables flip hand button
     */
    public void enableFlipHandButton(){
        if (playerInScene.equals(viewControllerGUI.getMyPlayer()))
            flipYourHandButton.setDisable(false);
        else flipYourHandButton.setDisable(true);
    }

    /**
     * Handles "flip your hand" button click flipping and showing hand
     */
    private void flipAndShowHand() {
        HandGUI hand = viewControllerGUI.getMyPlayer().getHand();
        for (CardGUI card : hand.getHandCards()){
            if (card.getCardID() != null)
                card.flipAndShow();
            if (card.isSelected) {
                card.setBorder(null);
                card.isSelected = false;
                confirmActionButton.setDisable(true);
                StaticsForGUI.enableActions = false;
            }
        }
    }

    /**
     * Enables confirm button click only if player in scene is my player, is current player and actions are enabled
     */
    public void enableConfirmButtonClick(){
        if (playerInScene.equals(viewControllerGUI.getMyPlayer()) && playerInScene.isCurrentPlayer() && StaticsForGUI.enableActions)
            confirmActionButton.setDisable(false);
        else confirmActionButton.setDisable(true);
    }

    /**
     * Adds event handlers to hand cards
     */
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
                if (StaticsForGUI.atLeastOneCornerSelected && viewControllerGUI.getMyPlayer().getHand().getChosenHandCard().isSelected){
                    StaticsForGUI.enableActions = true;
                    confirmActionButton.setDisable(false);
                }else{
                    StaticsForGUI.enableActions = false;
                    confirmActionButton.setDisable(true);
                }
            });
        }
    }

    /**
     * Sets event handlers to board cards
     */
    public void setEventHandlerToBoardCards() {
        for (CardGUI card : viewControllerGUI.getMyPlayer().getBoard().getCardsOnBoard()){
            addEventHandlerToCorners(card);
        }
    }

    /**
     * Adds event handlers to corners of board cards
     */
    public void addEventHandlerToCorners(CardGUI card){
        CornerGUI[] corners = card.getCorners();
        for (int i = 0; i < corners.length; i++) {
            int finalI = i;
            corners[i].setOnMouseClicked(event -> {
                handleCornerClick(corners[finalI], card);
                event.consume();});
        }
    }

    /**
     * Handles clicks on corners of board cards
     * @param corner corner clicked
     * @param card card on which said corner was click
     */
    public void handleCornerClick(CornerGUI corner, CardGUI card) {
        StaticsForGUI.atLeastOneCornerSelected = corner.toggleSelection(corner, viewControllerGUI.getMyPlayer().getBoard());
        viewControllerGUI.getMyPlayer().getBoard().setChosenCard(card);
        card.setChosenCornerCoordinates(corner.cornerCoordinates);
        if (StaticsForGUI.atLeastOneCornerSelected && viewControllerGUI.getMyPlayer().getHand().getChosenHandCard().isSelected){
            StaticsForGUI.enableActions = true;
            confirmActionButton.setDisable(false);
        }else{
            StaticsForGUI.enableActions = false;
            confirmActionButton.setDisable(true);
        }
    }

    /**
     * Adds event handlers to drawable area cards
     */
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

    /**
     * Checks if a resource or gold card is selected
     * @param isResourceCardChosen true if a resource card is chosen, false otherwise
     * @param isGoldCardChosen true if a gold card is chosen, false otherwise
     */
    private void checkForSelectedCards(boolean isResourceCardChosen, boolean isGoldCardChosen) {
        if (isResourceCardChosen || isGoldCardChosen){
            StaticsForGUI.enableActions = true;
            enableConfirmButtonClick();
        } else{
            StaticsForGUI.enableActions = false;
            confirmActionButton.setDisable(true);
        }
    }

    /**
     * Getter for drawable area
     * @return drawable area
     */
    public DrawableAreaGUI getDrawableArea(){
        return drawableArea;
    }

    /**
     * Getter for score board
     * @return score board
     */
    public ScoreBoardGUI getScoreBoard() {
        return scoreBoard;
    }

    /**
     * Getter for objectives section
     * @return objectives section
     */
    public ObjectivesSectionGUI getObjectivesSection() {
        return objectivesSection;
    }

    /**
     * Getter for endgame label
     * @return endGameLabel
     */
    public Label getEndGameLabel() {
        return endGameLabel;
    }

    /**
     * Getter for chat button
     * @return chatButton
     */
    public Button getChatButton() {
        return chatButton;
    }

    /**
     * Getter for seeOtherPlayersSceneButtons
     * @return seeOtherPlayersSceneButtons
     */
    public ArrayList<Button> getSeeOtherPlayersSceneButtons() {
        return seeOtherPlayersSceneButtons;
    }

    /**
     * Getter for confirmActionButton
     * @return confirmActionButton
     */
    public Button getConfirmActionButton() {
        return confirmActionButton;
    }

    /**
     * Sets confirm action label to a specific text
     * @param text text to be set
     */
    public void setConfirmActionLabel(String text) {
        this.confirmActionLabel.setText(text);
    }

    /**
     * Sets action label to a specific text
     * @param text text to be set
     */
    public void setActionLabel(String text){
        this.actionLabel.setText(text);
    }

    /**
     * Sets the turn label to a specific text
     * @param text text
     */
    public void setTurnLabel(String text){
        turnLabel.setText(text);
    }

    /**
     * Sets score board to player's usernames and adds it to main scene
     * @param playerUsernames players in game usernames
     */
    public void setScoreBoard(ArrayList<String> playerUsernames) {
        scoreBoard = new ScoreBoardGUI(playerUsernames);
        root.getChildren().add(scoreBoard);
    }

}
