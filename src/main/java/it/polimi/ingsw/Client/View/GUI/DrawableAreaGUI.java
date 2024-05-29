package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.Group;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Group to represent drawable area in main scene
 */
public class DrawableAreaGUI extends Group {

    /**
     * VBox to contain resource cards
     */
    private final VBox resourceCardsContainer = new VBox(10);
    /**
     * VBox to contain gold cards
     */
    private final VBox goldCardsContainer = new VBox(10);
    /**
     * Resource cards present in drawable area
     */
    private final CardGUI[] resourceCards = new CardGUI[3];
    /**
     * Gold cards present in drawable area
     */
    private final CardGUI[] goldCards = new CardGUI[3];
    /**
     * All cards present in drawable area
     */
    private final ArrayList<CardGUI> drawableAreaCards = new ArrayList<>();
    /**
     * Indicator for chosen drawable area (resource/gold)
     */
    private String chosenDrawableArea;
    /**
     * Index of chosen card in drawable area
     */
    private int chosenDrawableAreaCardIndex;

    /**
     * DrawableAreaGUI constructor
     */
    public DrawableAreaGUI(){
        resourceCardsContainer.setPrefSize(77,170);
        resourceCardsContainer.setLayoutX(920);
        resourceCardsContainer.setLayoutY(66);
        this.getChildren().add(resourceCardsContainer);

        goldCardsContainer.setPrefSize(77,170);
        goldCardsContainer.setLayoutX(920);
        goldCardsContainer.setLayoutY(342);
        this.getChildren().add(goldCardsContainer);

        initializeCards();
    }

    /**
     * Initialize resource and gold cards and adds them to their containers
     * Adds all cards to drawableAreaCards arraylist
     * */
    private void initializeCards() {
        for (int i = 0; i < 3; i++) {
            resourceCards[i] = new CardGUI();
            goldCards[i] = new CardGUI();
            resourceCardsContainer.getChildren().add(resourceCards[i]);
            goldCardsContainer.getChildren().add(goldCards[i]);

            drawableAreaCards.add(resourceCards[i]);
            drawableAreaCards.add(goldCards[i]);
        }
    }

    /**
     * Getter for drawable area cards
     * @return drawableAreaCards
     */
    protected ArrayList<CardGUI> getDrawableAreaCards() {
        return drawableAreaCards;
    }

    /**
     * Event handler for the selection of drawable area cards
     * Sets the type of drawable area of the selected card
     * @param drawableAreaCard selected drawable area card
     * @param isResourceCard indicates if selected card is of resource type or not
     * @return true if a card in the drawable area is selected, false otherwise
     */
    protected boolean setChosenDrawableAreaCard(CardGUI drawableAreaCard, Boolean isResourceCard) {
        if (drawableAreaCard.isSelected){
            if (isResourceCard){
                chosenDrawableArea = "resourceDrawableArea";
            } else {
                chosenDrawableArea = "goldDrawableArea";
            }
            return true;
        } else return false;
    }

    /**
     * Getter for chosen drawable area
     * @return chosenDrawableArea
     */
    protected String getChosenDrawableArea(){
        return chosenDrawableArea;
    }

    /**
     * Getter for resource cards
     * @return resourceCards
     */
    protected CardGUI[] getResourceCards() {
        return resourceCards;
    }

    /**
     * Getter for gold cards
     * @return goldCards
     */
    protected CardGUI[] getGoldCards() {
        return goldCards;
    }

    /**
     * Sets resource cards IDs and images, flips first resource card
     * @param resourceCardsIDs IDs received from server
     */
    protected void setResourceCardsDrawableArea(String[] resourceCardsIDs){
        for (int i = 0; i < resourceCardsIDs.length; i++) {
            resourceCards[i].setCardIDAndImage(resourceCardsIDs[i]);
        }
        if (resourceCards[0].isFaceUp())
            resourceCards[0].flipAndShow();
    }

    /**
     * Sets gold cards IDs and images, flips first gold card
     * @param goldCardsIDs IDs received from server
     */
    protected void setGoldCardsDrawableArea(String[] goldCardsIDs){
        for (int i = 0; i < goldCardsIDs.length; i++) {
            goldCards[i].setCardIDAndImage(goldCardsIDs[i]);
        }
        if (goldCards[0].isFaceUp())
            goldCards[0].flipAndShow();
    }

    /**
     * Deactivates event handlers in drawable area cards
     */
    protected void deactivateEventHandlerOnDrawableArea(){
        for (CardGUI card : drawableAreaCards){
            card.setOnMouseClicked(null);
            if (card.isSelected){
                card.setBorder(null);
                card.isSelected=false;
            }
        }
    }

    /**
     * Getter for chosen drawable area card index
     * @return chosenDrawableAreaCardIndex
     */
    protected int getChosenDrawableAreaCardIndex() {
        return chosenDrawableAreaCardIndex;
    }

    /**
     * Setter for chosen drawable area card index
     * @param chosenDrawableAreaCardIndex chosenDrawableAreaCardIndex
     */
    protected void setChosenDrawableAreaCardIndex(int chosenDrawableAreaCardIndex) {
        this.chosenDrawableAreaCardIndex = chosenDrawableAreaCardIndex;
    }
}
