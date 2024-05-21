package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class CardGUI extends AnchorPane {
    /**
     * ImageView associated with the card
     * */
    private final ImageView imageView = new ImageView();
    /**
     * Regions to represent the corners of the card
     * */
    private Region[] corners = new Region[4];
    /**
     * Card's face up resource ID
     */
    private String faceUpCardID;
    /**
     * Card's face down resource ID
     */
    private String faceDownCardID;

    /**
     * Indicates the orientation of the card
     */
    private boolean isFaceUp = true;

    /**
     * Model coordinates of where client would like the next card to be placed
     */
    private Coordinates chosenCornerCoordinates;
    /**
     * Model coordinates of where the card is placed
     */
    private final Coordinates modelCoordinates = new Coordinates();
    /**
     * GUI coordinates of where the card is placed
     */
    private final Coordinates guiCoordinates = new Coordinates();

    private boolean isCornerSelected = false;
    private boolean isSelected = false;

    public CardGUI(){
        imageView.setFitHeight(55);
        imageView.setFitWidth(82.5);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
    }

    public CardGUI(String cardID, Coordinates modelCoordinates, Boolean isFacingUp){
        imageView.setFitHeight(55);
        imageView.setFitWidth(82.5);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
        this.setCardIDAndImage(cardID);
        this.isFaceUp = isFacingUp;
        this.convertCoordinatesFromModelToGUIAndSetImageViewLayout(modelCoordinates.getX(), modelCoordinates.getY());
        initializeCorners();
    }

    /**
     * Puts the corners on the card
     * */
    public void initializeCorners() {
        double cornerWidth = 19.5;
        double cornerHeight = 23.0;

        double[] xPositions = {imageView.getLayoutX(), imageView.getLayoutX() + (imageView.getFitWidth() - cornerWidth)};
        double[] yPositions = {imageView.getLayoutY(), imageView.getLayoutY() + (imageView.getFitHeight() - cornerHeight)};

        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Region();
            corners[i].setPrefSize(cornerWidth, cornerHeight);
            corners[i].setLayoutX(xPositions[i % 2]);
            corners[i].setLayoutY(yPositions[i / 2]);
            corners[i].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            this.getChildren().add(corners[i]);
            corners[i].toFront();
        }
    }

    public Region[] getCorners() {
        return corners;
    }

    /**
     * Gets the chosen corner's coordinates for placing the next card on board
     * @return coordinates
     */
    public Coordinates getChosenCornerCoordinates(){
        return chosenCornerCoordinates;
    }

    public void setChosenCornerCoordinates(Coordinates chosenCornerCoordinates) {
        this.chosenCornerCoordinates = chosenCornerCoordinates;
    }

    /**
     * Flips the card
     */
    public void flipCard(){
        isFaceUp=!isFaceUp;
    }

    public void flipAndShow(){
        flipCard();
        setCardImage();
    }

    /**
     * Returns the state of the card
     * */
    public boolean isFaceUp() {
        return isFaceUp;
    }


    public void setCardIDAndImage(String cardID){
        this.faceUpCardID = cardID;
        this.faceDownCardID = cardID.substring(0,2);
        setCardImage();
    }
    /**
     * Sets the card's ImageView to the Image associated with its ID
     * */
    public void setCardImage(){
        String cardID;
        if (isFaceUp){
            cardID = faceUpCardID;
        }
        else cardID = faceDownCardID;
        String imagePath = String.format("Images/%s.png", cardID);
        Image cardImage = new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath)));
        imageView.setImage(cardImage);
    }

    public void removeCardImage(){
        this.imageView.setImage(null);
    }

    /**
     * Gets the ID of the card facing up
     * @return card's ID
     */
    public String getCardID(){
        if (isFaceUp)
            return faceUpCardID;
        else return faceDownCardID;
    }

    /**
     * Sets the face up ID of the card
     * @param faceUpCardID string to represent the ID of the card facing up
     * */
    public void setFaceUpCardID(String faceUpCardID) {
        this.faceUpCardID = faceUpCardID;
    }


    /**
     * Sets the guiCoordinates to the layouts associated with its ImageView
     * */
    public void convertCoordinatesFromModelToGUIAndSetImageViewLayout(int x, int y){
        modelCoordinates.setX(x);
        modelCoordinates.setY(y);
        guiCoordinates.setX(63*x + 1260);
        guiCoordinates.setY(640 - 32*y);
        this.imageView.setLayoutX(63*x + 1260);
        this.imageView.setLayoutY(640 - 32*y);
    }


    /**
     * Gets the guiCoordinates
     * @return gui coordinates
     */
    public Coordinates getGuiCoordinates(){
        return guiCoordinates;
    }

    /**
     * Gets the modelCoordinates
     * @return model coordinates
     */
    public Coordinates getModelCoordinates(){
        return modelCoordinates;
    }

    public void toggleSelection(CardGUI card, HandGUI hand) {
        for (CardGUI cardInHand : hand.getHandCards()){
            if(cardInHand.isSelected && !cardInHand.equals(card)){
                cardInHand.setBorder(null);
                cardInHand.isSelected = false;
            }
        }

        if ((card.getBorder() == null || card.getBorder().getStrokes().isEmpty()) && !card.isSelected) {
            card.setBorder(new Border(new BorderStroke(
                    Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5)
            )));
            card.isSelected = true;
        } else {
            card.setBorder(null);
            card.isSelected = false;
        }
    }
}
