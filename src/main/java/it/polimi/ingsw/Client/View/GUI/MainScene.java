package it.polimi.ingsw.Client.View.GUI;
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
    private final Button flipCardsButton = new Button("Flip your cards");
    private final Button[] seeOtherPlayersSceneButtons = new Button[3];

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
        handLabel.setLayoutX(310);
        handLabel.setLayoutY(456);
        this.root.getChildren().add(handLabel);

        Label secretObjectiveLabel = new Label("Secret Objective");
        secretObjectiveLabel.setFont(new Font("System Bold Italic", 16));
        secretObjectiveLabel.setAlignment(javafx.geometry.Pos.CENTER);
        secretObjectiveLabel.setLayoutX(524);
        secretObjectiveLabel.setLayoutY(456);
        this.root.getChildren().add(secretObjectiveLabel);

        Label commonObjectivesLabel = new Label("Common Objectives");
        commonObjectivesLabel.setFont(new Font("System Bold Italic", 16));
        commonObjectivesLabel.setAlignment(javafx.geometry.Pos.CENTER);
        commonObjectivesLabel.setLayoutX(656);
        commonObjectivesLabel.setLayoutY(456);
        this.root.getChildren().add(commonObjectivesLabel);

        turnLabel.setFont(new Font("System Bold", 18));
        turnLabel.setAlignment(javafx.geometry.Pos.CENTER);
        turnLabel.setLayoutX(10);
        turnLabel.setLayoutY(490);
        this.root.getChildren().add(turnLabel);

        actionLabel.setFont(new Font("System Bold", 18));
        actionLabel.setAlignment(javafx.geometry.Pos.CENTER);
        actionLabel.setLayoutX(10);
        actionLabel.setLayoutY(525);
        this.root.getChildren().add(actionLabel);
    }

    /**
     * Sets up all buttons for the scene
     */
    private void setUpButtons() {
        flipCardsButton.setLayoutX(350);
        flipCardsButton.setLayoutY(555);
        flipCardsButton.setMnemonicParsing(false);

        for (int i = 0; i < 3; i++) {
            seeOtherPlayersSceneButtons[i] = new Button();
            seeOtherPlayersSceneButtons[i].setLayoutX(38);
            seeOtherPlayersSceneButtons[i].setLayoutY(322+45*i);
            seeOtherPlayersSceneButtons[i].setMnemonicParsing(false);
        }
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

    /**
     * Sets the buttons to see other player's scenes to a specific username
     * @param usernames usernames
     */
    public void setSeeOtherPlayersGameButtons(ArrayList<String> usernames){
        for (int i = 0; i < 3; i++) {
            seeOtherPlayersSceneButtons[i].setText("See " + usernames.get(i) + "game");
        }
    }

    public BoardGUI getBoard() {
        return board;
    }
    public DrawableAreaGUI getDrawableArea(){
        return drawableArea;
    }
}
