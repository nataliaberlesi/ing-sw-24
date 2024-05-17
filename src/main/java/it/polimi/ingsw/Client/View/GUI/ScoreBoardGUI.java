package it.polimi.ingsw.Client.View.GUI;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardGUI extends GridPane {
    private final Label[] playerLabels = new Label[4];
    private final Label[] scoreLabels = new Label[4];

    public ScoreBoardGUI(){
        this.setLayoutX(25);
        this.setLayoutY(50);
        this.setPrefSize(166.0, 154.0);
        columnsContraintsSetUp();
        rowsConstraintsSetUp();
        setUpLabels();

    }

    /**
     * Sets row constraints
     */
    private void rowsConstraintsSetUp() {
        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(10.0);
            row.setPrefHeight(30.0);
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            this.getRowConstraints().add(row);
        }
    }
    /**
     * Sets column constraints
     */
    private void columnsContraintsSetUp() {
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

        for (int i = 0; i < 4; i++) {
            playerLabels[i] = new Label();
            playerLabels[i].setFont(new Font("System", 14));
            playerLabels[i].setAlignment(javafx.geometry.Pos.CENTER);
            GridPane.setRowIndex(playerLabels[i], i + 1);
            GridPane.setColumnIndex(playerLabels[i], 0);
            this.getChildren().add(playerLabels[i]);

            scoreLabels[i] = new Label();
            scoreLabels[i].setFont(new Font("System", 14));
            playerLabels[i].setAlignment(javafx.geometry.Pos.CENTER);
            GridPane.setRowIndex(scoreLabels[i], i + 1);
            GridPane.setColumnIndex(scoreLabels[i], 1);
            this.getChildren().add(scoreLabels[i]);
        }
    }

    /**
     * Sets usernames on the scoreBoard
     * @param usernames usernames of all players in game
     */
    public void setUsernames(ArrayList<String> usernames){
        for (int i = 0; i < usernames.size(); i++) {
            playerLabels[i].setText(usernames.get(i));
        }
    }

    /**
     * Updates the player's score on the scoreboard
     * @param player player to update score
     */
    public void updatePlayersScores(PlayerGUI player, int playersInGame) {
        for (int i = 0; i < playersInGame; i++) {
            scoreLabels[i].setText("" + player.getScore());
        }
    }
}
