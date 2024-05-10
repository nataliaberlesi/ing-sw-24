package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * card as shown in the GUI
 */
public class CardGUI {

    /**
     * Card's face up resource ID
     */
    private String faceUpCardID;
    /**
     * Card's face down resource ID
     */
    private String faceDownCardID;
    /**
     * Card's associated ImageView
     */
    private final ImageView cardImageView;
    /**
     * Buttons to represent the card's 4 corners
     * */
    private final Button[] corners = new Button[4];

    /**
     * Model coordinates of where the card is placed
     */
    private Coordinates modelCoordinates;
    /**
     * GUI coordinates of where the card is placed
     */
    private Point2D guiCoordinates;
    /**
     * Map of Model-GUI Coordinates
     */
    private Map<Point2D, Coordinates> coordinatesMap;

    /**
     * Indicates the orientation of the card
     */
    private boolean isFaceUp = true;
    /**
     * Indicates if this is a starting card
     * */
    private boolean isFirstCard = false;

    /**
     * flips the card
     */
    public void flipCard(){
        if(isFaceUp){
            faceDownCardID = faceUpCardID.substring(0,2);
            setCardImage(faceDownCardID);
        }
        else {
            setCardImage(this.faceUpCardID);
        }

        isFaceUp=!isFaceUp;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }


    public CardGUI(){
        cardImageView = new ImageView();
        cardImageView.setFitHeight(55);
        cardImageView.setFitWidth(82.5);
        cardImageView.setPickOnBounds(true);
        cardImageView.setPreserveRatio(true);
    }

    public String getFaceUpCardID(){
        return faceUpCardID;
    }

    public void setFaceUpCardID(String faceUpCardID) {
        this.faceUpCardID = faceUpCardID;
    }

    /**
     * Sets the modelCoordinates
     * @param modelCoordinates of where the card is being placed
     */
    public void setModelCoordinates(Coordinates modelCoordinates) {
        this.modelCoordinates = modelCoordinates;
    }
    /**
     * Gets the modelCoordinates
     */
    public Coordinates getModelCoordinates(){
        return modelCoordinates;
    }
    /**
     * Sets the guiCoordinates to the layouts associated with its ImageView
     * */
    public void setGuiCoordinates() {
        this.guiCoordinates = new Point2D(cardImageView.getLayoutX(), cardImageView.getLayoutY());
    }
    /**
     * Gets the guiCoordinates
     */
    public Point2D getGuiCoordinates(){
        return guiCoordinates;
    }
    /**
     * Sets the coordinatesMap between GUI and Model
     */
    public void setCoordinatesMap() {
        coordinatesMap = new HashMap<>();
        coordinatesMap.put(guiCoordinates, modelCoordinates);
    }

    /**
     * Sets the card's ImageView to the Image associated with its ID
     * */
    public void setCardImage(String cardID){
        if (isFaceUp)
            this.faceUpCardID = cardID;
        else this.faceDownCardID = cardID;

        String imagePath = String.format("Images/%s.png", cardID);
        Image cardImage = new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath)));
        this.cardImageView.setImage(cardImage);

    }

    public ImageView getCardImageView() {
        return this.cardImageView;
    }


    public void putClickableCornersOnCard(CardGUI boardCard){
        ImageView boardCardIV = boardCard.getCardImageView();
        for (int i = 0; i < 4; i++) {
            corners[i] = new Button();
            corners[i].prefHeight(23.0);
            corners[i].prefWidth(19.5);
            corners[i].setOpacity(0);
        }
        corners[0].setLayoutX(boardCardIV.getLayoutX() + 63);
        corners[0].setLayoutY(boardCardIV.getLayoutY());
        corners[1].setLayoutX(boardCardIV.getLayoutX());
        corners[1].setLayoutY(boardCardIV.getLayoutY());
        corners[2].setLayoutX(boardCardIV.getLayoutX());
        corners[2].setLayoutY(boardCardIV.getLayoutY()+30);
        corners[3].setLayoutX(boardCardIV.getLayoutX()+63);
        corners[3].setLayoutY(boardCardIV.getLayoutY()+30);
    }

}
