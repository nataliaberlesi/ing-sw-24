package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Objects;

/**
 * AnchorPane to represent cards in GUI
 */
public class CardGUI extends AnchorPane {
    /**
     * ImageView associated with the card
     * */
    private final ImageView imageView = new ImageView();
    /**
     * Corners of the card
     * */
    private final CornerGUI[] corners = new CornerGUI[4];
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
     * Model coordinates of where player would like the next card to be placed on the board
     */
    private Coordinates chosenCornerCoordinates;
    /**
     * Model coordinates of where the card is placed in the board
     */
    private final Coordinates modelCoordinates = new Coordinates();
    /**
     * GUI coordinates of where the card is placed in the board
     */
    private final Coordinates guiCoordinates = new Coordinates();
    /**
     * Indicates if a card is selected or not
     */
    protected boolean isSelected = false;

    /**
     * Constructor of card for cards in hand or in drawable area
     */
    public CardGUI(){
        setCardCharacteristics();
    }

    /**
     * Constructor of card for board cards
     * @param cardID resource ID of card
     * @param modelCoordinates model coordinates of where the card is to be placed in the board
     * @param isFacingUp orientation of the card
     */
    public CardGUI(String cardID, Coordinates modelCoordinates, Boolean isFacingUp){
        setCardCharacteristics();
        this.isFaceUp = isFacingUp;
        this.setCardIDAndImage(cardID);
        this.convertCoordinatesFromModelToGUIAndSetLayout(modelCoordinates.getX(), modelCoordinates.getY());
        initializeCorners();
    }

    /**
     * Sets card's imageview characteristics
     */
    private void setCardCharacteristics(){
        this.setPrefSize(StaticsForGUI.dimensions.get("cardWidth"), StaticsForGUI.dimensions.get("cardHeight"));
        imageView.setFitHeight(StaticsForGUI.dimensions.get("cardHeight"));
        imageView.setFitWidth(StaticsForGUI.dimensions.get("cardWidth"));
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
    }

    /**
     * Puts the corners on the card
     * */
     private void initializeCorners() {

        double[] xPositions = {imageView.getLayoutX(), imageView.getLayoutX() + (imageView.getFitWidth() - StaticsForGUI.dimensions.get("cornerWidth"))};
        double[] yPositions = {imageView.getLayoutY(), imageView.getLayoutY() + (imageView.getFitHeight() - StaticsForGUI.dimensions.get("cornerHeight"))};

        for (int i = 0; i < corners.length; i++) {
            corners[i] = new CornerGUI();
            if (i==0 || i==3){
                corners[i].setLayoutX(xPositions[1]);
            }
            if (i==1 || i==2){
                corners[i].setLayoutX(xPositions[0]);
            }
            corners[i].setLayoutY(yPositions[i / 2]);
            corners[i].cornerCoordinates = CornerCoordinatesCalculator.cornerCoordinates(this.getModelCoordinates(), i);
            this.getChildren().add(corners[i]);
            corners[i].toFront();
        }
    }

    /**
     * Getter for card's corners
     * @return corners
     */
    protected CornerGUI[] getCorners() {
        return corners;
    }

    /**
     * Getter for chosen corner's coordinates
     * @return coordinates where player wants next card to be placed on board
     */
    protected Coordinates getChosenCornerCoordinates(){
        return chosenCornerCoordinates;
    }

    /**
     * Setter for chosen corner coordinates
     * @param chosenCornerCoordinates coordinates where player wants next card to be placed on board
     */
    protected void setChosenCornerCoordinates(Coordinates chosenCornerCoordinates) {
        this.chosenCornerCoordinates = chosenCornerCoordinates;
    }

    /**
     * Flips the card
     */
    protected void flipCard(){
        isFaceUp=!isFaceUp;
    }

    /**
     * Flips the card and updates its image
     */
    protected void flipAndShow(){
        flipCard();
        setCardImage();
    }

    /**
     * Returns the state of the card
     * */
    protected boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * Sets card's ID and image
     * @param cardID string ID associated with image resource
     */
    protected void setCardIDAndImage(String cardID){
        this.faceUpCardID = cardID;
        this.faceDownCardID = cardID.substring(0,2);
        setCardImage();
    }

    /**
     * Sets the card's ImageView to the Image associated with its ID
     * */
    protected void setCardImage(){
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
     * Removes card ID and image from Card
     */
    protected void removeCardIDAndImage(){
        this.imageView.setImage(null);
        this.faceUpCardID = null;
        this.faceDownCardID = null;
    }

    /**
     * Gets the ID of the card
     * @return card's ID
     */
    protected String getCardID(){
        if (isFaceUp)
            return faceUpCardID;
        else return faceDownCardID;
    }

    /**
     * Converts the model coordinates to gui coordinates and places the card on the board
     * */
    protected void convertCoordinatesFromModelToGUIAndSetLayout(int x, int y){
        modelCoordinates.setX(x);
        modelCoordinates.setY(y);
        guiCoordinates.setX((int)((StaticsForGUI.dimensions.get("cardWidth") - StaticsForGUI.dimensions.get("cornerWidth"))*x + StaticsForGUI.dimensions.get("initialCardLayoutX")));
        guiCoordinates.setY((int)(StaticsForGUI.dimensions.get("initialCardLayoutY") - (StaticsForGUI.dimensions.get("cardHeight") - StaticsForGUI.dimensions.get("cornerHeight"))*y));
        this.setLayoutX(guiCoordinates.getX());
        this.setLayoutY(guiCoordinates.getY());
    }

    /**
     * Gets the modelCoordinates
     * @return model coordinates
     */
    protected Coordinates getModelCoordinates(){
        return modelCoordinates;
    }

    /**
     * Event handler for the selection of hand cards
     * @param hand player's hand cards
     */
    protected void toggleSelection(HandGUI hand) {
        for (CardGUI cardInHand : hand.getHandCards()){
            if(cardInHand.isSelected && !cardInHand.equals(this)){  //if there is already another card selected, unselect it
                cardInHand.setBorder(null);
                cardInHand.isSelected = false;
            }
        }

        addBorderAndSelectCard(this);
    }

    /**
     * Event handler for the selection of drawable area cards
     * @param drawableAreaGUI player's drawable area cards
     */
    protected void toggleSelection(DrawableAreaGUI drawableAreaGUI){
        for (CardGUI cardInDrawableArea : drawableAreaGUI.getDrawableAreaCards()){
            if(cardInDrawableArea.isSelected && !cardInDrawableArea.equals(this)){ //if there is already another card selected, unselect it
                cardInDrawableArea.setBorder(null);
                cardInDrawableArea.isSelected = false;
            }
        }

        addBorderAndSelectCard(this);
    }

    /**
     * Adds (or removes) red border to/from selected card and marks it as selected/unselected
     * @param card selected card
     */
    private void addBorderAndSelectCard(CardGUI card) {
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
