package it.polimi.ingsw.Client.View.GUI;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.ArrayList;

/**
 * GridPane to represent score board in main scene
 */
public class ScoreBoardGUI extends GridPane {
    /**
     * Number of players in game
     */
    private final int numberOfPlayersInGame;
    /**
     * Labels for player's usernames
     */
    private final ArrayList<Label> playerLabels = new ArrayList<>();
    /**
     * Labels for player's scores
     */
    private final ArrayList<Label> scoreLabels = new ArrayList<>();

    /**
     * Constructor of ScoreBoardGUI
     * @param playerUsernames list of usernames of players in game
     */
    public ScoreBoardGUI(ArrayList<String> playerUsernames){
        this.setLayoutX(25);
        this.setLayoutY(50);
        this.setVgap(5);
        this.setHgap(35);
        this.numberOfPlayersInGame = playerUsernames.size();
        setUpLabels();
        setUsernamesAndInitialScore(playerUsernames);
    }

    /**
     * Sets all labels in the score board
     */
    private void setUpLabels() {
        Label playersLabel = new Label("Players");
        StaticsForGUI.setLabelCharacteristics(playersLabel, "System Bold", 18, 0,0);
        setRowIndex(playersLabel, 0);
        setColumnIndex(playersLabel, 0);
        setColumnSpan(playersLabel, 2);
        this.getChildren().add(playersLabel);

        Label scoreLabel = new Label("Score");
        StaticsForGUI.setLabelCharacteristics(scoreLabel, "System Bold", 18, 0,0);
        scoreLabel.setAlignment(javafx.geometry.Pos.CENTER);
        setRowIndex(scoreLabel, 0);
        setColumnIndex(scoreLabel, 1);
        this.getChildren().add(scoreLabel);

        for (int i = 0; i < numberOfPlayersInGame; i++) {
            playerLabels.add(new Label());
            StaticsForGUI.setLabelCharacteristics(playerLabels.get(i), "System", 16, 0,0);
            GridPane.setRowIndex(playerLabels.get(i), i + 1);
            GridPane.setColumnIndex(playerLabels.get(i), 0);
            this.getChildren().add(playerLabels.get(i));

            scoreLabels.add(new Label());
            StaticsForGUI.setLabelCharacteristics(scoreLabels.get(i), "System", 16, 0,0);
            GridPane.setRowIndex(scoreLabels.get(i), i + 1);
            GridPane.setColumnIndex(scoreLabels.get(i), 1);
            this.getChildren().add(scoreLabels.get(i));
        }
    }

    /**
     * Sets usernames and initial score on the scoreBoard
     */
    protected void setUsernamesAndInitialScore(ArrayList<String> playerUsernames){
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            playerLabels.get(i).setText(playerUsernames.get(i));
            scoreLabels.get(i).setText("0");
        }
    }

    /**
     * Updates the player's score on the scoreboard
     */
    protected void updatePlayerScore(String username, int score) {
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            if (playerLabels.get(i).getText().equalsIgnoreCase(username)){
                scoreLabels.get(i).setText(String.valueOf(score));
            }
        }
    }

    /**
     * Updates score board with game points + objective points in final scene
     * @param ranking ranking of players after game ends
     */
    protected void updatePlayerScoreWithObjectivesPoints(ArrayList<ScoreboardPositionDTO> ranking){
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            int index = getPlayerScoreIndex(ranking.get(i).username());
            int totalScore = ranking.get(i).score();
            int oldScore = Integer.parseInt(scoreLabels.get(index).getText());
            int objectivesScore = totalScore - oldScore;
            scoreLabels.get(index).setText(oldScore + " + " + objectivesScore);
        }
    }

    /**
     * Gets the index of the score in the scoreboard associated with a certain player
     * @param username player's username
     * @return index of score in scoreboard associated with player
     */
    private int getPlayerScoreIndex(String username){
        for (int i = 0; i < numberOfPlayersInGame; i++) {
            if (playerLabels.get(i).getText().equalsIgnoreCase(username))
                return i;
        }
        return -1;
    }
}
