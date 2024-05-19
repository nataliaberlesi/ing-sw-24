package it.polimi.ingsw.Client.View.GUI;

import java.util.ArrayList;

public class PlayersInGameGUI {

    /**
     * list of players that have joined game
     */
    private final ArrayList<PlayerGUI> players = new ArrayList<>();

    /**
     * @param username of player that method caller is looking for
     * @return player with same username as passed in params
     * @throws RuntimeException if no such player is present
     */
    public PlayerGUI getPlayer(String username) throws RuntimeException {
        for(PlayerGUI player : players) {
            if(player.getUsername().equals(username)) {
                return player;
            }
        }
        throw new RuntimeException("Player not found");
    }

    /**
     *
     * @param player new player that has joined
     * @throws RuntimeException if player already joined
     */
    public void addPlayer(PlayerGUI player) throws RuntimeException {
        if(players.contains(player)) {
            throw new RuntimeException("Player already exists");
        }
        players.add(player);
    }

    public ArrayList<PlayerGUI> getPlayers() {
        return players;
    }
}
