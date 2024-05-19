package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoreBoardGUI extends GridPane {
    private final int numberOfPlayersInGame;
    private final ArrayList<Label> playerLabels = new ArrayList<>();
    private final ArrayList<Label> scoreLabels = new ArrayList<>();

    public ScoreBoardGUI(ArrayList<String> playerUsernames){
        this.setLayoutX(25);
        this.setLayoutY(50);
        this.setPrefSize(166.0, 154.0);
        this.numberOfPlayersInGame = playerUsernames.size();
        setUpLabels();
        setUsernamesAndScore(playerUsernames);
        columnsConstraintsSetUp();
    }

    /**
     * Sets column constraints
     */
    private void columnsConstraintsSetUp() {
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        column1.setMaxWidth(137.688);
        column1.setMinWidth(10.0);
        column1.setPrefWidth(115.942);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        column2.setMaxWidth(96.323);
        column2.setMinWidth(10.0);
        column2.setPrefWidth(84.558);

        this.getColumnConstraints().addAll(column1, column2);
    }

    /**
     * Sets all labels in the score board
     */
    private void setUpLabels() {
        Label playersLabel = new Label("Players");
        playersLabel.setFont(new Font("System Bold", 16));
        playersLabel.setAlignment(javafx.geometry.Pos.CENTER);
        setRowIndex(playersLabel, 0);
        setColumnIndex(playersLabel, 0);
        setColumnSpan(playersLabel, 2);
        this.getChildren().add(playersLabel);

        Label scoreLabel = new Label("Score");
        scoreLabel.setFont(new Font("System Bold", 16));
        scoreLabel.setAlignment(javafx.geometry.Pos.CENTER);
        setRowIndex(scoreLabel, 0);
        setColumnIndex(scoreLabel, 1);
        this.getChildren().add(scoreLabel);

        for (int i = 0; i < numberOfPlayersInGame; i++) {
            playerLabels.add(new Label());
            playerLabels.get(i).setFont(new Font("System", 14));
            playerLabels.get(i).setAlignment(javafx.geometry.Pos.CENTER);
            GridPane.setRowIndex(playerLabels.get(i), i + 1);
            GridPane.setColumnIndex(playerLabels.get(i), 0);
            this.getChildren().add(playerLabels.get(i));

            scoreLabels.add(new Label());
            scoreLabels.get(i).setFont(new Font("System", 14));
            scoreLabels.get(i).setAlignment(javafx.geometry.Pos.CENTER);
            GridPane.setRowIndex(scoreLabels.get(i), i + 1);
            GridPane.setColumnIndex(scoreLabels.get(i), 1);
            this.getChildren().add(scoreLabels.get(i));
        }
    }

    /**
     * Sets usernames and initial score on the scoreBoard
     */
    public void setUsernamesAndScore(ArrayList<String> playerUsernames){
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            playerLabels.get(i).setText(playerUsernames.get(i));
            scoreLabels.get(i).setText("0");
        }
    }

    /**
     * Updates the player's score on the scoreboard
     */
    public void updatePlayerScore(String username, int score) {
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            if (playerLabels.get(i).getText().equalsIgnoreCase(username)){
                scoreLabels.get(i).setText(String.valueOf(score));
            }
        }
    }
}
