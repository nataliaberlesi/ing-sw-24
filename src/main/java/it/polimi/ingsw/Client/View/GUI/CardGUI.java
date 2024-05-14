package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import java.util.Objects;

public class CardGUI extends AnchorPane {
    /**
     * ImageView associated with the card
     * */
    private final ImageView imageView = new ImageView();
    /**
     * Regions to represent the corners of the card
     * */
    private final Region[] corners = new Region[4];
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
     * Indicates if this is a starting card
     * */
    private boolean isInitialCard = false;
    /**
     * Indicates if this is a private objective card
     */
    private boolean isPrivateObjective = false;
    /**
     * Model coordinates of where the card is placed
     */
    private final Coordinates modelCoordinates = new Coordinates();
    /**
     * GUI coordinates of where the card is placed
     */
    private final Coordinates guiCoordinates = new Coordinates();

    public CardGUI(){
        imageView.setFitHeight(55);
        imageView.setFitWidth(82.5);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        initializeCorners();
    }

    /**
     * Puts the corners on the card
     * */
    private void initializeCorners() {
        double cornerWidth = 19.5;
        double cornerHeight = 23.0;

        double[] xPositions = {0, imageView.getFitWidth() - cornerWidth};
        double[] yPositions = {0, imageView.getFitHeight() - cornerHeight};

        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Region();
            corners[i].setPrefSize(cornerWidth, cornerHeight);
            corners[i].setLayoutX(xPositions[i % 2]);
            corners[i].setLayoutY(yPositions[i / 2]);
            corners[i].setOpacity(0);
            this.getChildren().add(corners[i]);
        }
    }

    /**
     * Gets the corners of the card
     * @return corners
     */
    public Region[] getCorners() {
        return corners;
    }

    /**
     * Flips the card
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

    /**
     * Returns the state of the card
     * */
    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * Sets the card's ImageView to the Image associated with its ID
     * @param cardID resource string to represent cardID
     * */
    public void setCardImage(String cardID){
        if (isFaceUp)
            this.faceUpCardID = cardID;
        else this.faceDownCardID = cardID;
        String imagePath = String.format("Images/%s.png", cardID);
        Image cardImage = new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath)));
        imageView.setImage(cardImage);
    }

    /**
     * Gets the ID of the card facing up
     * @return face up card's ID
     */
    public String getFaceUpCardID(){
        return faceUpCardID;
    }

    /**
     * Sets the face up ID of the card
     * @param faceUpCardID string to represent the ID of the card facing up
     * */
    public void setFaceUpCardID(String faceUpCardID) {
        this.faceUpCardID = faceUpCardID;
    }

    /**
     * Verifies if this card is an initial card
     * */
    public boolean isInitialCard() {
        return isInitialCard;
    }

    /**
     * Sets this card to be an initialCard
     * */
    public void setAsInitialCard() {
        isInitialCard = true;
    }
    /**
     * Verifies if this card is a private objective card
     * */
    public boolean isPrivateObjective() {
        return isPrivateObjective;
    }
    /**
     * Sets this card to be a private objective card
     * */
    public void setAsPrivateObjective() {
        isPrivateObjective = true;
    }

    /**
     * Sets the guiCoordinates to the layouts associated with its ImageView
     * */
    public void setGuiAndModelCoordinates(int x, int y) {
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
        guiCoordinates.setX(x);
        guiCoordinates.setY(y);
        modelCoordinates.setX((x-1260)/63);
        modelCoordinates.setY(-(x-640)/32);
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

}
