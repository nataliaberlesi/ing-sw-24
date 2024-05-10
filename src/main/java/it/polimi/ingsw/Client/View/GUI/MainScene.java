package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainScene extends Scene {
    private final SceneController mainSceneController;
    private final AnchorPane root;
    private final VBox resourceCardsDrawableArea = new VBox(10);
    private final VBox goldCardsDrawableArea = new VBox(10);
    private final HBox handCardsDrawableArea = new HBox(10);
    private final HBox objectiveCardsArea = new HBox(10);
    private CardGUI[] resourceCards = new CardGUI[3];
    private CardGUI[] goldCards = new CardGUI[3];
    private CardGUI[] handCards = new CardGUI[3];
    private CardGUI[] objectiveCards = new CardGUI[3];
    private final CardGUI firstCard = new CardGUI();

    public MainScene() {
        super(new AnchorPane(), 1060, 595);
        root = (AnchorPane)this.getRoot();
        this.mainSceneController = new SceneController(this);
        initializeComponents();
    }

    private void initializeComponents(){
        setUpVBoxes();
        setUpHBoxes();
        setUpLabels();
        setUpButtons();
        setUpScrollPane();
        setUpGridPane();
    }

    private void setUpVBoxes() {
        resourceCardsDrawableArea.setLayoutX(920);
        resourceCardsDrawableArea.setLayoutY(86);
        resourceCardsDrawableArea.setPrefSize(77, 170);
        addImageViewsToVBox(resourceCardsDrawableArea, resourceCards);
        root.getChildren().add(resourceCardsDrawableArea);

        goldCardsDrawableArea.setLayoutX(920);
        goldCardsDrawableArea.setLayoutY(367);
        goldCardsDrawableArea.setPrefSize(77, 170);
        addImageViewsToVBox(goldCardsDrawableArea, goldCards);
        root.getChildren().add(goldCardsDrawableArea);
    }

    private void addImageViewsToVBox(VBox vbox, CardGUI[] cards) {
        for (int i = 0; i < 3; i++) {
            ImageView imageView = cards[i].getCardImageView();
            imageView.setUserData(cards[i]);
            imageView.setOnMouseClicked(mainSceneController::handleDrawableAreaClicked);
            vbox.getChildren().add(imageView);
        }
    }

    public VBox getResourceCardsDrawableArea(){
        return resourceCardsDrawableArea;
    }
    public VBox getGoldCardsDrawableArea(){
        return goldCardsDrawableArea;
    }
    private void setUpHBoxes() {
    handCardsDrawableArea.setLayoutX(266.0);
    handCardsDrawableArea.setLayoutY(489.0);
    handCardsDrawableArea.setPrefSize(264.0,56.0);
    addImageViewsToHBox(handCardsDrawableArea, handCards);

    objectiveCardsArea.setLayoutX(576.0);
    objectiveCardsArea.setLayoutY(489.0);
    objectiveCardsArea.setPrefSize(264.0,56.0);
    addImageViewsToHBox(objectiveCardsArea, objectiveCards);
    }

    private void addImageViewsToHBox(HBox hbox, CardGUI[] cards) {
        for (int i = 0; i < 3; i++) {
            ImageView imageView = cards[i].getCardImageView();
            imageView.setUserData(cards[i]);
            imageView.setOnMouseClicked(mainSceneController::handleHandAreaClicked);
            hbox.getChildren().add(imageView);
        }
    }

    public HBox getHandCardsDrawableArea() {
        return handCardsDrawableArea;
    }

    public HBox getObjectiveCardsArea() {
        return objectiveCardsArea;
    }

    private void setUpLabels() {
    }
    private void setUpButtons() {
    }
    private void setUpScrollPane() {
    }
    private void setUpGridPane() {
        AnchorPane Board;

    }

}
