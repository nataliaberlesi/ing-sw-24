package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance {
    private ArrayList<String> playersTurnOrder;
    private String currentPlayer;
    private int numberOfPlayers;
    private HashMap<String, Player> players;
    public GameInstance(String masterNickname,int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        playersTurnOrder =new ArrayList<String>();
        players=new HashMap<String,Player>();
        joinPlayer(masterNickname);
    }
    public void joinPlayer(String username) {
        playersTurnOrder.add(username);
        players.put(username,new Player(username));
    }
    public boolean unavailableUsername(String username) {
        return !(playersTurnOrder.contains(username));
    }
    public boolean checkIfGameIsFull() {
        return this.playersTurnOrder.size()== numberOfPlayers;
    }
}
