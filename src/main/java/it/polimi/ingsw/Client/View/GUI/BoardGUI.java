package it.polimi.ingsw.Client.View.GUI;
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
    private CardGUI chosenCard;

    private ArrayList<CardGUI> cardsOnBoard = new ArrayList<>();

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
        initialCard.convertCoordinatesFromModelToGUIAndSetLayout(0, 0);
        initialCard.setCardIDAndImage(cardID);
        initialCard.initializeCorners();
        anchorPane.getChildren().add(initialCard);
        cardsOnBoard.add(initialCard);

        playerColorToken.setFitWidth(20);
        playerColorToken.setFitHeight(20);
        playerColorToken.setLayoutX(1276);
        playerColorToken.setLayoutY(657);
        anchorPane.getChildren().add(playerColorToken);
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
        firstPlayerToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream("Images/Tokens/BLACK.png"))));
        firstPlayerToken.setFitWidth(20);
        firstPlayerToken.setFitHeight(20);
        firstPlayerToken.setLayoutX(1306);
        firstPlayerToken.setLayoutY(657);
        anchorPane.getChildren().add(firstPlayerToken);
        firstPlayerToken.toFront();

    }

    /**
     * Sets the token of the player to a color image
     * @param tokenImage image resource associated with the token color
     */
    public void setPlayerColorToken(String tokenImage){
        String imagePath = String.format("Images/Tokens/%s.png", tokenImage);
        playerColorToken.setImage(new Image(Objects.requireNonNull(GUIApplication.class.getResourceAsStream(imagePath))));
        playerColorToken.toFront();
    }


    /**
     * Method to place cards on board
     * @param cards cards to be placed on board
     */
    public void updateBoard(ArrayList<CardGUI> cards){

        for (int i=1; i < cards.size(); i++) {
            anchorPane.getChildren().add(cards.get(i));
        }
        cardsOnBoard=cards;
        cardsOnBoard.set(0, initialCard);
    }
    public void setChosenCard(CardGUI chosenCard){
        this.chosenCard = chosenCard;
    }

    public ArrayList<CardGUI> getCardsOnBoard() {
        return cardsOnBoard;
    }

    public CardGUI getChosenCard() {
        return chosenCard;
    }

    public void deactivateEventHandlerOnCorners(){
        for (CardGUI card : cardsOnBoard){
            for (CornerGUI corner : card.getCorners()) {
                corner.setBorder(null);
                corner.isSelected = false;
                corner.setOnMouseClicked(null);
            }
        }
        MainScene.atLeastOneCornerSelected = false;
    }


}
