package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

public class PlayersInGameCLI {

    /**
     * list of players that have joined game
     */
    private final ArrayList<PlayerCLI> players =new ArrayList<>();

    private PlayerCLI myPlayer;

    /**
     * prints score of each player
     */
    public void printScores() {
        System.out.println("SCOREBOARD:");
        for(PlayerCLI player : players) {
                player.printScore();
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

    /**
     *
     * @param username of player that method caller is looking for
     * @return player with same username as passed in params
     * @throws RuntimeException if no such player is present
     */
    public PlayerCLI getPlayer(String username) throws RuntimeException {
        for(PlayerCLI player : players) {
            if(player.getUsername().equals(username)) {
                return player;
            }
        }
        throw new RuntimeException("Player not found");
    }
}
