package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.layout.HBox;

/**
 * HBox to represent objectives section of main scene
 */
public class ObjectivesSectionGUI extends HBox {
    /**
     * Public objective in objectives section
     */
    private final CardGUI[] publicObjectiveCards = new CardGUI[2];
    /**
     * Private objective in objectives section
     */
    private final CardGUI privateObjective = new CardGUI();

    /**
     * Constructor for objectives section
     */
    public ObjectivesSectionGUI(){
        this.setLayoutX(576.0);
        this.setLayoutY(489.0);
        this.setPrefSize(264.0,56.0);
        this.setSpacing(10);
        addCardsToObjectivesSection();
    }

    /**
     * Adds the objective cards to the objectives section
     */
    private void addCardsToObjectivesSection() {
        this.getChildren().add(privateObjective);
        for (int i = 0; i < publicObjectiveCards.length; i++) {
            publicObjectiveCards[i] = new CardGUI();
            this.getChildren().add(publicObjectiveCards[i]);
        }
    }

    /**
     * Setter for public objectives
     * @param cardIDs public objectives IDs
     */
     public void setPublicObjectives(String[] cardIDs){
        for (int i = 0; i < publicObjectiveCards.length; i++)
            publicObjectiveCards[i].setCardIDAndImage(cardIDs[i]);
     }

    /**
     * Getter for private objective
     * @return privateObjective
     */
     public CardGUI getPrivateObjective(){
        return privateObjective;
     }
}
