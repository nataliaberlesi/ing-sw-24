package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * Scene to declare winners and losers of game
 */
public class EndGameScene extends Scene {
    /**
     * Scene's root
     */
    private final AnchorPane root;
    /**
     * Winner label
     */
    private final Label winnerLabel = new Label("CONGRATULATIONS,\nYOU WON!!!\n");
    /**
     * Loser label
     */
    private final Label loserLabel =  new Label("Sorry,\nYou lost :(\n");
    /**
     * Final score label
     */
    private final Label finalScore = new Label("This is the final score:\n");
    /**
     * Final score board
     */
    private final GridPane finalScoreBoard = new GridPane();
    /**
     * Instance of ViewControllerGUI to control scene
     */
    private final ViewControllerGUI viewControllerGUI;

    /**
     * Constructor of endgame scene
     * @param viewControllerGUI viewControllerGUI instance
     */
    public EndGameScene(ViewControllerGUI viewControllerGUI) {
        super(new AnchorPane(), 1060, 595);
        this.viewControllerGUI = viewControllerGUI;
        root = (AnchorPane) this.getRoot();
        initializeScene();
        initializeGridPane();
    }

    /**
     * Initializes final score board's characteristics and adds it to scene
     */
    private void initializeGridPane() {
        finalScoreBoard.setLayoutX(438);
        finalScoreBoard.setLayoutY(358);
        this.root.getChildren().add(finalScoreBoard);
    }

    /**
     * Initializes other scene's components
     */
    private void initializeScene() {
        AnchorPane root = (AnchorPane) this.getRoot();
        root.setPrefSize(1060, 595);

        ImageView backgroundImageView = new ImageView(new Image(String.valueOf(GUIApplication.class.getResource("Images/initialScreenOriginal.jpg"))));
        backgroundImageView.setFitWidth(1060);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setPickOnBounds(true);
        backgroundImageView.setOpacity(0.2);

        StaticsForGUI.setLabelCharacteristics(finalScore, "Lao MN Bold", 36,345, 266);

        Button goBackButton = new Button("See game");
        StaticsForGUI.setButtonCharacteristics(goBackButton, "Lao MN Bold", 24, 852, 485, false);
        goBackButton.setOnMouseClicked(event -> viewControllerGUI.setSceneOnStage(viewControllerGUI.getMainScene(), viewControllerGUI.getStage()));
        viewControllerGUI.getMainScene().getConfirmActionButton().setDisable(true);

        this.root.getChildren().addAll(backgroundImageView, finalScore, goBackButton);
    }

    /**
     * Sets winner label to scene for players that won the game
     */
    protected void setWinnerLabel(){
        StaticsForGUI.setLabelCharacteristics(winnerLabel,"Lao MN Bold", 36, 330, 108);
        this.root.getChildren().add(winnerLabel);

    }

    /**
     * Sets loser label to scene for players that lost the game
     */
    protected void setLoserLabel(){
        StaticsForGUI.setLabelCharacteristics(loserLabel,"Lao MN Bold", 36, 467, 108);
        this.root.getChildren().add(loserLabel);
    }

    /**
     * Sets final score board with rankings and winner/loser label
     * @param ranking ranking of players with usernames, scores and positions
     * @param myPlayerUsername username of my player
     */
    protected void setScoreBoard(ArrayList<ScoreboardPositionDTO> ranking, String myPlayerUsername){
        finalScoreBoard.setVgap(5);
        finalScoreBoard.setHgap(20);
        ArrayList<Label> rankingLabels = new ArrayList<>();
        ArrayList<Label> usernames = new ArrayList<>();
        ArrayList<Label> scores = new ArrayList<>();

        for (int i = 0; i < ranking.size(); i++){
            rankingLabels.add(new Label(String.valueOf(ranking.get(i).position())));
            usernames.add(new Label(String.valueOf(ranking.get(i).username())));
            scores.add(new Label(String.valueOf(ranking.get(i).score())));

            StaticsForGUI.setLabelCharacteristics(rankingLabels.get(i),"Lao MN Bold", 30, 0, 0);
            StaticsForGUI.setLabelCharacteristics(usernames.get(i),"Lao MN Bold", 30, 0, 0);
            StaticsForGUI.setLabelCharacteristics(scores.get(i),"Lao MN Bold", 30, 0, 0);

            GridPane.setRowIndex(rankingLabels.get(i), i + 1);
            GridPane.setColumnIndex(rankingLabels.get(i), 0);
            finalScoreBoard.getChildren().add(rankingLabels.get(i));

            GridPane.setRowIndex(usernames.get(i), i + 1);
            GridPane.setColumnIndex(usernames.get(i), 1);
            finalScoreBoard.getChildren().add(usernames.get(i));

            GridPane.setRowIndex(scores.get(i), i + 1);
            GridPane.setColumnIndex(scores.get(i), 2);
            finalScoreBoard.getChildren().add(scores.get(i));

            if (ranking.get(i).username().equalsIgnoreCase(myPlayerUsername) && ranking.get(i).position() == 1){
                setWinnerLabel();
                viewControllerGUI.getMainScene().setTurnLabel("Congratulations!\nYou won!");
                viewControllerGUI.getMainScene().setConfirmActionLabel("");
            }
            if (ranking.get(i).username().equalsIgnoreCase(myPlayerUsername) && ranking.get(i).position() != 1){
                setLoserLabel();
                viewControllerGUI.getMainScene().setTurnLabel("Sorry,\nYou lost :(");
                viewControllerGUI.getMainScene().setConfirmActionLabel("");
            }
        }
    }
}