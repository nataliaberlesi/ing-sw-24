package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class BoardGUI extends ScrollPane {
    /**
     * Anchor pane that will be used as container of cards inside the scroll pane
     */
    private final AnchorPane anchorPane;

    public BoardGUI(){
        this.setPrefSize(663, 418);
        this.setLayoutX(200);
        this.setLayoutY(22);
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(2602.5, 1335);
        this.setContent(anchorPane);
        this.setHvalue(0.5);
        this.setVvalue(0.5);
        CardGUI initialCard = new CardGUI();
        initialCard.setGuiAndModelCoordinates(1260, 640);
        initialCard.setAsInitialCard();
        anchorPane.getChildren().add(initialCard);

    }

    /**
     * Method to place a card on the board in a certain position and associate an image to it
     * @param card card to be placed on board
     * @param guiCoordinates coordinates where to place the card on the anchor pane
     * @param imageID id of the image associated to the card
     */
    public void placeCardOnBoard(CardGUI card, Coordinates guiCoordinates, String imageID){
        card.setGuiAndModelCoordinates(guiCoordinates.getX(), guiCoordinates.getY());
        card.setCardImage(imageID);
        anchorPane.getChildren().add(card);
    }

}
