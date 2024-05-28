package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.layout.*;

/**
 * HBox to represent hand in main scene
 */
public class HandGUI extends HBox {
    /**
     * Hand cards
     */
    private final CardGUI[] handCards = new CardGUI[3];
    /**
     * Index of chosen card in hand cards
     */
    private int chosenHandCardIndex;
    /**
     * Chosen hand card
     */
    private CardGUI chosenHandCard = new CardGUI();

    /**
     * Constructor
     */
    public HandGUI(){
        this.setLayoutX(266.0);
        this.setLayoutY(489.0);
        this.setPrefSize(264.0,56.0);
        this.setSpacing(10);
        addCardsToHand();
    }

    /**
     * Adds the hand cards to the hand
     */
    private void addCardsToHand() {
        for (int i = 0; i < 3; i++) {
            handCards[i] = new CardGUI();
            this.getChildren().add(handCards[i]);
        }
    }

    /**
     * Getter for hand cards
     * @return hand cards array
     */
    protected CardGUI[] getHandCards() {
        return handCards;
    }

    /**
     * Getter for chosen hand card
     * @return chosenHandCard
     */
    protected CardGUI getChosenHandCard() {
        return chosenHandCard;
    }

    /**
     * Updates my player's hand setting IDs and images of hand cards
     * @param handIDs IDs of hand cards
     */
    protected void updateMyHand(String[] handIDs) {
        for (int i = 0; i < handIDs.length; i++){
            if (handIDs[i] != null)
                handCards[i].setCardIDAndImage(handIDs[i]);
            else handCards[i].removeCardIDAndImage();
        }
    }

    /**
     * Updates my player's hand for when other players want to see my player's hand
     * Sets IDs and images of hand cards. Makes all hand cards face down
     * @param handIDs IDs of hand cards
     */
    protected void updateMyHandForOtherPlayers(String[] handIDs){
        for (int i = 0; i < handIDs.length; i++){
            if (handIDs[i] != null)
                handCards[i].setCardIDAndImage(handIDs[i].substring(0,2));
            else handCards[i].removeCardIDAndImage();
        }
    }

    /**
     * Deactivates event handlers on hand cards
     */
    protected void deactivateEventHandlerOnHandCards(){
        for (CardGUI card : handCards){
            if (card.isSelected){
                card.setBorder(null);
                card.isSelected=false;
            }
            card.setOnMouseClicked(null);
        }
    }

    /**
     * Getter for chosen hand card index
     * @return chosenHandCardIndex
     */
    protected Integer getChosenHandCardIndex() {
        return chosenHandCardIndex;
    }

    /**
     * Setter for chosen hand card.
     * @param handCard equals chosenHandCard if hand card was not already selected
     */
    protected void setChosenHandCard(CardGUI handCard) {
        if (handCard.isSelected) {
            chosenHandCard = handCard;
        }
    }

    /**
     * Setter for chosen hand card index
     * @param finalI chosenHandCardIndex
     */
    protected void setChosenHandCardIndex(int finalI) {
        chosenHandCardIndex = finalI;
    }
}
