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
        goldCardsContainer.setPrefSize(77,170);
        goldCardsContainer.setLayoutX(920);
        goldCardsContainer.setLayoutY(367);
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
     * @return gold cards array
     */
    public CardGUI[] getGoldCards() {
        return goldCards;
    }

    /**
     * Getter for resource cards
     * @return resource cards array
     */
    public CardGUI[] getResourceCards() {
        return resourceCards;
    }
}
