package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Is the instance of the game for the controller and acts as a way to communicate with the model data
 */
public class GameInstance {
    private ArrayList<String> playersTurnOrder;
    private String currentPlayer;
    private int numberOfPlayers;
    private HashMap<String, Player> players;
    private SetUpGame setUpGame;
    public GameInstance(String masterNickname,int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        playersTurnOrder =new ArrayList<String>();
        players=new HashMap<String,Player>();
        joinPlayer(masterNickname);
    }

    /**
     * Adds the given username to playersTurnOrder and creates the Player model instance
     * @param username
     */
    public void joinPlayer(String username) {
        playersTurnOrder.add(username);
        players.put(username,new Player(username));
    }

    /**
     * Checks if the given username is already taken
     * @param username
     * @return
     */
    public boolean unavailableUsername(String username) {
        return !(playersTurnOrder.contains(username));
    }

    /**
     * Checks if the game has reached his full capacity
     * @return
     */
    public boolean checkIfGameIsFull() {
        return this.playersTurnOrder.size()== numberOfPlayers;
    }
}
