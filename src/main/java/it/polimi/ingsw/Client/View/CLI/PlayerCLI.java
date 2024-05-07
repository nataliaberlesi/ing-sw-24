package it.polimi.ingsw.Client.View.CLI;

public class PlayerCLI {

    private final String username;
    private int score;

    public PlayerCLI(String username) {
        this.username = username;
        this.score = 0;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
