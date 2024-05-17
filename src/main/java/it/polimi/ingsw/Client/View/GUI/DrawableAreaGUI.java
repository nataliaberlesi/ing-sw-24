package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.Group;
import javafx.scene.layout.VBox;

public class DrawableAreaGUI extends Group {

    private final VBox resourceCardsContainer = new VBox(10);
    private final VBox goldCardsContainer = new VBox(10);
    private final CardGUI[] resourceCards = new CardGUI[3];
    private final CardGUI[] goldCards = new CardGUI[3];

    public DrawableAreaGUI(){

        resourceCardsContainer.setPrefSize(77,170);
        resourceCardsContainer.setLayoutX(920);
        resourceCardsContainer.setLayoutY(86);
        this.getChildren().add(resourceCardsContainer);

        goldCardsContainer.setPrefSize(77,170);
        goldCardsContainer.setLayoutX(920);
        goldCardsContainer.setLayoutY(367);
        this.getChildren().add(goldCardsContainer);

        initializeCards();
    }

    /**
     * Initialize resource and gold cards in their containers
     * */
    private void initializeCards() {
        for (int i = 0; i < 3; i++) {
            resourceCards[i] = new CardGUI();
            goldCards[i] = new CardGUI();
            resourceCardsContainer.getChildren().add(resourceCards[i]);
            goldCardsContainer.getChildren().add(goldCards[i]);
        }
    }

    /**
     * Getter for gold cards
     * @return a gold card in a specific position of the gold cards array
     */
    public CardGUI getGoldCards(int position) {

        return goldCards[position];
    }

    /**
     * Getter for resource cards
     * @return a resource card in a specific position of the gold cards array
     */
    public CardGUI getResourceCard(int position) {
        return resourceCards[position];
    }

    public void setResourceCardsDrawableArea(String[] resourceCardsIDs){
        for (int i = 0; i < resourceCardsIDs.length; i++) {
            resourceCards[i].setCardID(resourceCardsIDs[i]);
        }
        resourceCards[0].flipAndShow();
    }
    public void setGoldCardsDrawableArea(String[] goldCardsIDs){
        for (int i = 0; i < goldCardsIDs.length; i++) {
            goldCards[i].setCardID(goldCardsIDs[i]);
        }
        goldCards[0].flipAndShow();

    }
}
