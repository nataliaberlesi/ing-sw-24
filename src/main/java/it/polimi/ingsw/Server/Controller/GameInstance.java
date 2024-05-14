package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
import it.polimi.ingsw.Server.Model.Cards.Deck;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.DrawableArea;
import it.polimi.ingsw.Server.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Is the instance of the game for the controller and acts as a way to communicate with the model data
 */
public class GameInstance {
    private ArrayList<String> playersTurnOrder;
    private int currentPlayerIndex;
    private String currentPlayer;
    private int numberOfPlayers;

    private HashMap<String, Player> players;
    private DrawableArea drawableArea;
    private SetUpGame setUpGame;
    private boolean gameIsStarted;
    private Deck startingDeck;
    private ArrayList<Color> availableColors;

    public GameInstance(String masterNickname,int numberOfPlayers) {
        this.currentPlayerIndex=0;
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
        return playersTurnOrder.contains(username);
    }

    /**
     * Checks if the game has reached his full capacity
     * @return
     */
    public boolean checkIfGameIsFull() {
        return this.playersTurnOrder.size()== numberOfPlayers;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public ArrayList<String> getPlayerTurnOrder() {
        return playersTurnOrder;
    }
    public HashMap<String, Player> getPlayers() {
        return players;
    }
    public void setDrawableArea(DrawableArea drawableArea) {
        this.drawableArea=drawableArea;
    }
    public boolean gameIsStarted() {
        return this.gameIsStarted;
    }
    public void startGame() {
        this.gameIsStarted=true;
    }
    public void setStartingDeck(Deck startingDeck) {
        this.startingDeck=startingDeck;
    }
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    public String getTurn() {
        return playersTurnOrder.get(currentPlayerIndex);
    }
    public int nextTurn() {
        if(currentPlayerIndex<playersTurnOrder.size()) {
            currentPlayerIndex++;
        }
        else {
            currentPlayerIndex=0;
        }
        return currentPlayerIndex;
    }

    public ArrayList<Color> getAvailableColors() {
        return availableColors;
    }
    public Deck getStartingDeck() {
        return startingDeck;
    }

    public void chooseColor(String username, String color) {
        players.get(username).setPlayerColor(Color.valueOf(color));
    }

    public void placeStartingCard(String username, boolean b) {
        //players.get(username).placeStartingCard();
    }
}
