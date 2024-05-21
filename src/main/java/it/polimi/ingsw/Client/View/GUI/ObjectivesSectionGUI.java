package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.layout.HBox;

public class ObjectivesSectionGUI extends HBox {
    private final CardGUI[] publicObjectiveCards = new CardGUI[2];
    private final CardGUI privateObjective = new CardGUI();

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
        this.getChildren().add(privateObjective);
        for (int i = 0; i < publicObjectiveCards.length; i++) {
            publicObjectiveCards[i] = new CardGUI();
            this.getChildren().add(publicObjectiveCards[i]);
        }
    }

     public void setPublicObjectives(String[] cardIDs){
        for (int i = 0; i < publicObjectiveCards.length; i++)
            publicObjectiveCards[i].setCardIDAndImage(cardIDs[i]);
     }
     public CardGUI getPrivateObjective(){
        return privateObjective;
     }
}
