package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class BoardGUI extends ScrollPane {
    /**
     * Anchor pane that will be used as container of cards inside the scroll pane
     */
    private final AnchorPane anchorPane;
    /**
     * Initial card placed in the middle of the board
     */
    private final CardGUI initialCard = new CardGUI();
    /**
     * Image View to contain the black token
     */
    private final ImageView firstPlayerToken = new ImageView();
    /**
     * Image View to contain the player's color token
     */
    private final ImageView playerColorToken = new ImageView();

    public BoardGUI(){
        this.setPrefSize(663, 418);
        this.setLayoutX(200);
        this.setLayoutY(22);
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(2602.5, 1335);
        this.setContent(anchorPane);
        this.setHvalue(0.5);
        this.setVvalue(0.5);
        setInitialCard();
    }

    /**
     * Sets the initial card's characteristics
     */
    private void setInitialCard() {
        initialCard.setGuiAndModelCoordinates(1260, 640);
        initialCard.setAsInitialCard();
        playerColorToken.setFitWidth(20);
        playerColorToken.setFitHeight(20);
        playerColorToken.setLayoutX(1276);
        playerColorToken.setLayoutY(657);
        initialCard.getChildren().add(playerColorToken);
        anchorPane.getChildren().add(initialCard);
    }

    /**
     * Getter for initial card
     * @return initial card
     */
    public CardGUI getInitialCard() {
        return initialCard;
    }

    /**
     * Sets the first player token to black
     * @param player first player
     */
    public void setFirstPlayerToken(PlayerGUI player){
        if (player.isFirstPlayer()){
            firstPlayerToken.setFitWidth(20);
            firstPlayerToken.setFitHeight(20);
            firstPlayerToken.setLayoutX(1306);
            firstPlayerToken.setLayoutY(657);
            firstPlayerToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/Tokens/BLACK.png"))));
            initialCard.getChildren().add(firstPlayerToken);
        }
    }

    /**
     * Sets the token of the player to a color image
     * @param tokenImage image resource associated with the token color
     */
    public void setPlayerColorToken(String tokenImage){
        String imagePath = String.format("Images/%s.png", tokenImage);
        playerColorToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath))));
    }

    /**
     * Getter for player color token
     * @return player color token image view
     */
    public ImageView getPlayerColorToken() {
        return playerColorToken;
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
