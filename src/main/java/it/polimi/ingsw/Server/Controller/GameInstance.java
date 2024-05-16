package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Model.Cards.Deck;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.DrawableArea;
import it.polimi.ingsw.Server.Model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Is the instance of the game for the controller and acts as a way to communicate with the model data
 */
public class GameInstance {
    private ArrayList<String> playersTurnOrder;
    private int currentPlayerIndex;
    private final int numberOfPlayers;

    private HashMap<String, Player> players;
    private DrawableArea drawableArea;
    private boolean firstRoundIsStarted;
    private boolean secondRoundIsStarted;
    private boolean allBoardsAreSet;
    private Deck startingDeck;
    private ArrayList<Color> availableColors;

    public GameInstance(String masterNickname,int numberOfPlayers) {
        this.currentPlayerIndex=0;
        this.numberOfPlayers = numberOfPlayers;
        playersTurnOrder =new ArrayList<String>();
        availableColors=new ArrayList<Color>();
        availableColors.addAll(Arrays.asList(Color.values()));
        players=new HashMap<String,Player>();
        joinPlayer(masterNickname);
    }

    /**
     * Adds the given username to playersTurnOrder and creates the Player model instance
     * @param username the player to be added
     */
    public void joinPlayer(String username) {
        playersTurnOrder.add(username);
        players.put(username,new Player(username));
    }

    /**
     * Checks if the given username is already taken
     * @param username the username to check
     * @return true if the username is unavailable
     */
    public boolean unavailableUsername(String username) {
        return playersTurnOrder.contains(username);
    }

    /**
     * Checks if the game has reached his full capacity
     * @return true if the game is full
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
    public DrawableArea getDrawableArea() {
        return this.drawableArea;
    }
    public boolean firstRoundIsStarted() {
        return this.firstRoundIsStarted;
    }
    public boolean secondRoundIsStarted() {
        return this.secondRoundIsStarted;
    }
    public void startFirstRound() {
        this.firstRoundIsStarted =true;
    }
    public void startSecondRound() {
        this.secondRoundIsStarted=true;
    }
    public boolean allBoardsAreSet() {
        return allBoardsAreSet;
    }
    public void checkIfAllBoardsAreSet() {
        boolean flag=false;
        for(String player: playersTurnOrder) {
            flag=!players.get(player).getPlayerBoard().getPlacedCards().isEmpty();
        }
        this.allBoardsAreSet=flag;
    }
    public void setStartingDeck(Deck startingDeck) {
        this.startingDeck=startingDeck;
    }
    /**
     *
     * @return the current player
     */
    public String getTurn() {
        return playersTurnOrder.get(currentPlayerIndex);
    }

    /**
     * Passes to the next turn
     * @return the turn index
     */
    public int nextTurn() {
        if(currentPlayerIndex<playersTurnOrder.size()-1) {
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
    /**
     * Sets the player's color
     * @param username
     * @param color
     */
    public void chooseColor(String username, String color) {
        players.get(username).setPlayerColor(Color.valueOf(color));
        this.availableColors.remove(Color.valueOf(color));
    }
    /**
     * Places the saved starting card into the player's board
     * @param username the player's username
     * @param flipStartingCard the flip status of the card
     */
    public void placeStartingCard(String username, boolean flipStartingCard) {
        players.get(username).getPlayerBoard().placeStartingCard(flipStartingCard);
    }
    /**
     * Saves the starting card into the player instance
     * @param username
     * @param cardID
     */
    public void saveStartingCard(String username, String cardID) {
        players.get(username).placeStartingCard(cardID);
    }
    /**
     * Sets public objectives for each player
     * @param firstPublicObjective
     * @param secondPublicObjective
     */
    public void setPublicObjectives(String firstPublicObjective, String secondPublicObjective) {
        for(String player: getPlayerTurnOrder()) {
            getPlayers().get(player).getPlayerBoard().addObjective(firstPublicObjective);
            getPlayers().get(player).getPlayerBoard().addObjective(secondPublicObjective);
        }
    }
}
