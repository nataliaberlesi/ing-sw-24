package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

public class PlayersScore {

    private final ArrayList<PlayerCLI> players =new ArrayList<>();

    public void printScores() {
        System.out.println("SCOREBOARD:");
        for(PlayerCLI player : players) {
            System.out.println(player.getUsername()+": "+player.getScore());
        }
    }

    public void addPlayer(PlayerCLI playerCLI) throws RuntimeException {
        players.add(playerCLI);
    }
}
