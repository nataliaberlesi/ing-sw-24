package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

public class PlayersInGame {

    /**
     * list of players that have joined game
     */
    private final ArrayList<PlayerCLI> players =new ArrayList<>();

    /**
     * shows score of each player
     */
    public void printScores() {
        System.out.println("SCOREBOARD:");
        for(PlayerCLI player : players) {
            System.out.println(player.getUsername()+": "+player.getScore());
        }
    }

    /**
     *
     * @param playerCLI new player that has joined
     * @throws RuntimeException if player already joined
     */
    public void addPlayer(PlayerCLI playerCLI) throws RuntimeException {
        if(players.contains(playerCLI)) {
            throw new RuntimeException("Player already exists");
        }
        players.add(playerCLI);
    }
}
