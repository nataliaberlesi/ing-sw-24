package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.layout.*;

public class HandGUI extends HBox {
    private final CardGUI[] handCards = new CardGUI[3];
    private int index;
    private CardGUI chosenHandCard = new CardGUI();

    public HandGUI(){
        this.setLayoutX(266.0);
        this.setLayoutY(489.0);
        this.setPrefSize(264.0,56.0);
        this.setSpacing(10);
        addCardsToHand();
    }

    /**
     * Adds the hand cards to the container
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
    public CardGUI[] getHandCards() {
        return handCards;
    }

    public CardGUI getChosenHandCard() {
        return chosenHandCard;
    }

    public void updateMyHand(String[] handIDs) {
        for (int i = 0; i < handIDs.length; i++){
            if (handIDs[i] != null)
                handCards[i].setCardIDAndImage(handIDs[i]);
            else handCards[i].removeCardIDAndImage();
        }
    }
    public void updateMyHandForOtherPlayers(String[] handIDs){
        for (int i = 0; i < handIDs.length; i++){
            if (handIDs[i] != null)
                handCards[i].setCardIDAndImage(handIDs[i].substring(0,2));
            else handCards[i].removeCardIDAndImage();
        }
    }
    public void deactivateEventHandlerOnHandCards(){
        for (CardGUI card : handCards){
            card.setOnMouseClicked(null);
        }
    }
    public Integer getIndex() {
        return index;
    }

    public boolean setChosenHandCard(CardGUI handCard) {
        if (handCard.isSelected){
            chosenHandCard = handCard;
            return true;
        } else return false;
    }

    public void setIndex(int finalI) {
        index = finalI;
    }
}
