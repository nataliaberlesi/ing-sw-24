package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
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
    private CardGUI lastCardPlaced;
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
        anchorPane.setPrefSize(2603, 1335);
        this.setContent(anchorPane);
        this.setHvalue(0.5);
        this.setVvalue(0.5);
    }

    /**
     * Sets the initial card's characteristics
     */
    public void setInitialCard(String cardID) {
        initialCard.convertCoordinatesFromGUIToModelAndSetImageViewLayout(1260, 640);
        initialCard.setAsInitialCard();
        playerColorToken.setFitWidth(20);
        playerColorToken.setFitHeight(20);
        playerColorToken.setLayoutX(1276);
        playerColorToken.setLayoutY(657);
        initialCard.setOnMouseClicked(event -> initialCard.flipAndShow());
        initialCard.getChildren().add(playerColorToken);
        initialCard.setCardIDAndImage(cardID);
        anchorPane.getChildren().add(initialCard);
        lastCardPlaced = initialCard;
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
     */
    public void setFirstPlayerToken(){
        firstPlayerToken.setFitWidth(20);
        firstPlayerToken.setFitHeight(20);
        firstPlayerToken.setLayoutX(1306);
        firstPlayerToken.setLayoutY(657);
        firstPlayerToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/Tokens/BLACK.png"))));
        initialCard.getChildren().add(firstPlayerToken);

    }

    /**
     * Sets the token of the player to a color image
     * @param tokenImage image resource associated with the token color
     */
    public void setPlayerColorToken(String tokenImage){
        String imagePath = String.format("Images/Tokens/%s.png", tokenImage);
        playerColorToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath))));
    }


    /**
     * Method to place a card on the board in a certain position and associate an image to it
     * @param cards card to be placed on board
     */
    public void updateBoard(ArrayList<CardGUI> cards){
        int i;
        for (i = cards.size() - 1; i > 0 ; i--) {
            if (cards.get(i).equals(lastCardPlaced)){
                break;
            }
        }
        i++;
        for (; i < cards.size(); i++) {
            anchorPane.getChildren().add(cards.get(i));
        }
    }
}
