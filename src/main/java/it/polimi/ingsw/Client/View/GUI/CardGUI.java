package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        this.getChildren().add(imageView);
        initializeCorners();
    }

    public CardGUI(String cardID, Coordinates modelCoordinates, Boolean isFacingUp){
        imageView.setFitHeight(55);
        imageView.setFitWidth(82.5);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
        initializeCorners();
        this.setCardID(cardID);
        this.isFaceUp = isFacingUp;
        this.convertCoordinatesFromModelToGUIAndSetImageViewLayout(modelCoordinates.getX(), modelCoordinates.getY());

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
            int finalI = i;
            corners[i].setOnMouseClicked((event -> handleCornerClick(finalI, event)));
            this.getChildren().add(corners[i]);
        }
    }

    private Coordinates handleCornerClick(int i, MouseEvent event) {
        Coordinates coordinates = CornerCoordinatesCalculator.cornerCoordinates(this.modelCoordinates, i);
        event.consume();
        return coordinates;
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


    public void setCardID(String cardID){
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
    public void convertCoordinatesFromGUIToModelAndSetImageViewLayout(int x, int y) {
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
        guiCoordinates.setX(x);
        guiCoordinates.setY(y);
        modelCoordinates.setX((x-1260)/63);
        modelCoordinates.setY(-(y-640)/32);
    }

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

}
