package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Objects;

public class MainScene extends Scene {

    private final AnchorPane root;
    private final BoardGUI board = new BoardGUI();
    private final HandGUI hand = new HandGUI();
    private final ObjectivesSectionGUI objectivesSection = new ObjectivesSectionGUI();
    private final DrawableAreaGUI drawableArea = new DrawableAreaGUI();
    private final ScoreBoardGUI scoreBoard = new ScoreBoardGUI();
    private final Label turnLabel = new Label();
    private final Label actionLabel = new Label();
    private final Button flipYourHandButton = new Button("Flip your hand");
    private final Button confirmActionButton = new Button("Confirm action");
    private final ArrayList<Button> seeOtherPlayersSceneButtons = new ArrayList<>();
    private CardGUI chosenCard = board.getInitialCard();
    private MessageDispatcher messageDispatcher;
    private MessageType lastMessageToArrive;
    private PlayerGUI myplayer;

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

    public MainScene(MessageDispatcher messageDispatcher, PlayerGUI myPlayer) {
        super(new AnchorPane(), 1060, 595);
        this.myplayer = myPlayer;
        this.messageDispatcher = messageDispatcher;
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
        resourceCardsLabel.setAlignment(javafx.geometry.Pos.CENTER);
        resourceCardsLabel.setLayoutX(912);
        resourceCardsLabel.setLayoutY(52);
        this.root.getChildren().add(resourceCardsLabel);

        Label goldCardsLabel = new Label("Gold Cards");
        goldCardsLabel.setFont(new Font("System Bold Italic", 16));
        goldCardsLabel.setAlignment(javafx.geometry.Pos.CENTER);
        goldCardsLabel.setLayoutX(924);
        goldCardsLabel.setLayoutY(332);
        this.root.getChildren().add(goldCardsLabel);

        Label handLabel = new Label("Hand");
        handLabel.setFont(new Font("System Bold Italic", 16));
        handLabel.setAlignment(javafx.geometry.Pos.CENTER);
        handLabel.setLayoutX(370);
        handLabel.setLayoutY(457);
        this.root.getChildren().add(handLabel);

        Label secretObjectiveLabel = new Label("Secret Objective");
        secretObjectiveLabel.setFont(new Font("System Bold Italic", 16));
        secretObjectiveLabel.setAlignment(javafx.geometry.Pos.CENTER);
        secretObjectiveLabel.setLayoutX(524);
        secretObjectiveLabel.setLayoutY(457);
        this.root.getChildren().add(secretObjectiveLabel);

        Label commonObjectivesLabel = new Label("Common Objectives");
        commonObjectivesLabel.setFont(new Font("System Bold Italic", 16));
        commonObjectivesLabel.setAlignment(javafx.geometry.Pos.CENTER);
        commonObjectivesLabel.setLayoutX(656);
        commonObjectivesLabel.setLayoutY(457);
        this.root.getChildren().add(commonObjectivesLabel);

        Label confirmActionLabel = new Label("Use the confirm action button to confirm your choice");
        confirmActionLabel.setFont(new Font("System Bold", 14));
        confirmActionLabel.setAlignment(javafx.geometry.Pos.CENTER);
        confirmActionLabel.setLayoutX(30);
        confirmActionLabel.setLayoutY(547);
        this.root.getChildren().add(confirmActionLabel);

        turnLabel.setFont(new Font("System Bold", 18));
        turnLabel.setAlignment(javafx.geometry.Pos.CENTER);
        turnLabel.setLayoutX(30);
        turnLabel.setLayoutY(487);
        this.root.getChildren().add(turnLabel);

        actionLabel.setFont(new Font("System Bold", 16));
        actionLabel.setAlignment(javafx.geometry.Pos.CENTER);
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
     * Sets the action label to a specific text
     * @param text text
     */
    public void setActionLabel(String text){
        actionLabel.setText(text);
    }

    public void setHelloPlayerLabel(String username){
        Label helloPlayerLabel = new Label("Hi "+ username);
        helloPlayerLabel.setFont(new Font("System Bold", 18));
        helloPlayerLabel.setAlignment(Pos.CENTER);
        helloPlayerLabel.setLayoutX(38);
        helloPlayerLabel.setLayoutY(457);
        this.root.getChildren().add(helloPlayerLabel);
    }

    /**
     * Sets the buttons to see other player's scenes to a specific username
     * @param usernames usernames
     */
    public void setSeeOtherPlayersGameButtons(ArrayList<String> usernames){
        for (int i = 0; i < ViewControllerGUI.numberOfPlayersInGame; i++) {
            seeOtherPlayersSceneButtons.add(new Button());
            seeOtherPlayersSceneButtons.get(i).setLayoutX(38);
            seeOtherPlayersSceneButtons.get(i).setLayoutY(277+45*i);
            seeOtherPlayersSceneButtons.get(i).setMnemonicParsing(false);
            seeOtherPlayersSceneButtons.get(i).setText("See " + usernames.get(i) + "'s game");
            this.root.getChildren().add(seeOtherPlayersSceneButtons.get(i));
        }
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
        setActionLabel("Click on the initial card if you want to flip it");
        enableConfirmButtonClick();
    }

    public void enableConfirmButtonClick(){
        confirmActionButton.setDisable(false);
        confirmActionButton.setOnMouseClicked(event -> onConfirmButtonClicked());
    }

    public void onConfirmButtonClicked(){
        switch (lastMessageToArrive){
            case START_FIRSTROUND, FIRSTROUND -> {
                Boolean isFaceUp = board.getInitialCard().isFaceUp();
                board.getInitialCard().setOnMouseClicked(null);
                confirmActionButton.setDisable(true);

                setActionLabel("Choose a color by clicking on it");
                TokenChoicePopUp scene = myplayer.getTokenChoicePopUpScene();
                scene.setPopUpStage();
                scene.popUpStage.setScene(scene);
                scene.popUpStage.show();
                while (scene.color == null){}
                messageDispatcher.firstRound(myplayer.getUsername(), isFaceUp, scene.color);
            }


        }
    }

    public void setChosenCard(CardGUI chosenCard){
        this.chosenCard = chosenCard;
    }

    public void setLastMessageToArrive(MessageType lastMessageToArrive) {
        this.lastMessageToArrive = lastMessageToArrive;
    }


}
