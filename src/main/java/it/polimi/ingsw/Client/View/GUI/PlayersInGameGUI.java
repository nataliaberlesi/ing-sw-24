package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.View.CLI.PlayerCLI;

import java.util.ArrayList;

public class PlayersInGameGUI {

    /**
     * List of players that have joined game
     */
    private final ArrayList<PlayerGUI> players = new ArrayList<>();

    /**
     * Getter for player in game
     * @param username of player that method caller is looking for
     * @return player with same username as passed in params
     * @throws RuntimeException if no such player is present
     */
    public PlayerGUI getPlayer(String username) throws RuntimeException {
        for(PlayerGUI player : players) {
            if(player.getUsername().equalsIgnoreCase(username)) {
                return player;
            }
        }
        throw new RuntimeException("Player not found");
    }

    /**
     * Indicates if player is present in the game or not
     * @param username of player that might be in game
     * @return true if username corresponds to a player in the game
     */
    public boolean isPlayerInGame(String username) {
        for(PlayerGUI player : players) {
            if(player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds player to player in game
     * @param player new player that has joined
     * @throws RuntimeException if player already joined
     */
    public void addPlayer(PlayerGUI player) throws RuntimeException {
        if(players.contains(player)) {
            throw new RuntimeException("Player already exists");
        }
        players.add(player);
    }

    /**
     * Getter for players in game
     * @return players
     */
    public ArrayList<PlayerGUI> getPlayers() {
        return players;
    }
}
