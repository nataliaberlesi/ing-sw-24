package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class EndGameScene extends Scene {
    private final AnchorPane root;
    private final Label winnerLabel = new Label("CONGRATULATIONS!\nYOU WON!!!\n");
    private final Label loserLabel =  new Label("Sorry,\nYou lost :(\n");
    private final Label finalScore = new Label("This is the final score:\n");
    private final GridPane finalScoreBoard = new GridPane();

    public EndGameScene() {
        super(new AnchorPane(), 1060, 595);
        root = (AnchorPane) this.getRoot();
        initializeScene();
        initializeGridPane();
    }

    private void initializeGridPane() {
        finalScoreBoard.setLayoutX(438);
        finalScoreBoard.setLayoutY(358);
        this.root.getChildren().add(finalScoreBoard);

    }

    private void initializeScene() {
        AnchorPane root = (AnchorPane) this.getRoot();
        root.setPrefSize(1060, 595);

        ImageView backgroundImageView = new ImageView(new Image(String.valueOf(GUIApplication.class.getResource("Images/initialScreenOriginal.jpg"))));
        backgroundImageView.setFitWidth(1060);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setPickOnBounds(true);
        backgroundImageView.setOpacity(0.1);

        StaticsForGUI.setLabelCharacteristics(new Label("CONGRATULATIONS!\nYOU WON!!!\n"),"Lao MN Bold", 36, 330, 108);
        StaticsForGUI.setLabelCharacteristics(new Label("Sorry,\nYou lost :(\n"),"Lao MN Bold", 36, 467, 108);
        StaticsForGUI.setLabelCharacteristics(new Label("This is the final score:\n"), "Lao MN Bold", 36,340, 266);

        this.root.getChildren().add(backgroundImageView);
        this.root.getChildren().add(finalScore);
        this.root.getChildren().add(winnerLabel);
        this.root.getChildren().add(loserLabel);
        winnerLabel.setVisible(false);
        loserLabel.setVisible(false);
    }

    public void setWinnerLabel(){
        winnerLabel.setVisible(true);
    }

    public void setLoserLabel(){
        winnerLabel.setVisible(true);
    }

    protected void setScoreBoard(ArrayList<ScoreboardPositionDTO> ranking, String username){
        finalScoreBoard.setVgap(10);
        finalScoreBoard.setVgap(10);
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

            if (ranking.get(i).username().equalsIgnoreCase(username) && ranking.get(i).position() == 1){
                setWinnerLabel();
            }
            else setLoserLabel();

        }
    }
}