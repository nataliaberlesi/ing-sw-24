package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.layout.HBox;

public class HandGUI extends HBox {
    private final CardGUI[] handCards = new CardGUI[3];

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
}
