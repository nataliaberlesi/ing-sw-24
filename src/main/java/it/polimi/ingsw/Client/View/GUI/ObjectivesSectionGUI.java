package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.layout.HBox;

public class ObjectivesSectionGUI extends HBox {
    private final CardGUI[] objectiveCards = new CardGUI[3];

    public ObjectivesSectionGUI(){
        this.setLayoutX(576.0);
        this.setLayoutY(489.0);
        this.setPrefSize(264.0,56.0);
        this.setSpacing(10);
        addCardsToObjectivesSection();
    }

    /**
     * Adds the objective cards to the container
     */
    private void addCardsToObjectivesSection() {
        for (int i = 0; i < 3; i++) {
            objectiveCards[i] = new CardGUI();
            this.getChildren().add(objectiveCards[i]);
        }
        objectiveCards[0].setAsPrivateObjective();
    }

    /**
     * Getter for objective cards
     * @return the objective cards array
     */
    public CardGUI[] getObjectiveCards() {
        return objectiveCards;
    }
}
