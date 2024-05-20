package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Model.*;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Deck;
import it.polimi.ingsw.Server.Model.Cards.ResourceCardFactory;

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
    private boolean gameIsStarted;
    private boolean allBoardsAreSet;
    private boolean allObjectivesHaveBeenChosen;
    private boolean endgameIsStarted;
    private boolean finalRoundIsStarted;
    private boolean gameIsEnded;
    private Deck startingDeck;
    private ArrayList<Color> availableColors;

    public GameInstance(String masterNickname,int numberOfPlayers) {
        this.currentPlayerIndex=0;
        allBoardsAreSet=false;
        allObjectivesHaveBeenChosen=false;
        firstRoundIsStarted=false;
        secondRoundIsStarted=false;
        gameIsStarted=false;
        endgameIsStarted=false;
        finalRoundIsStarted=false;
        gameIsEnded=false;
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
    public int getCurrentPlayerIndex(){return this.currentPlayerIndex;}
    public void setDrawableArea(DrawableArea drawableArea) {
        this.drawableArea=drawableArea;
    }
    public DrawableArea getDrawableArea() {
        return this.drawableArea;
    }
    public void startFirstRound() {
        this.firstRoundIsStarted =true;
    }
    public void startSecondRound() {
        this.secondRoundIsStarted=true;
    }
    public void startGame() {this.gameIsStarted=true;}
    public void startEndgame(){this.endgameIsStarted=true;}
    public void startFinalRound(){this.finalRoundIsStarted=true;}
    public boolean firstRoundIsStarted() {
        return this.firstRoundIsStarted;
    }
    public boolean secondRoundIsStarted() {
        return this.secondRoundIsStarted;
    }
    public boolean allBoardsAreSet() {
        return allBoardsAreSet;
    }
    public boolean isEndgameIsStarted(){return this.endgameIsStarted;}
    public boolean isFinalRoundIsStarted(){return this.finalRoundIsStarted;}
    public void checkIfAllBoardsAreSet() {
        boolean flag=true;
        for(String player: playersTurnOrder) {
            if(players.get(player).getPlayerBoard().getPlacedCards().isEmpty()){
                flag=false;
            }
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
    public void setPublicObjectives(String currentPlayer,String firstPublicObjective, String secondPublicObjective) {
        getPlayers().get(currentPlayer).getPlayerBoard().addObjective(firstPublicObjective);
        getPlayers().get(currentPlayer).getPlayerBoard().addObjective(secondPublicObjective);
    }
    public Card[] getHand(String player) {
        Card[] hand=new Card[3];
        for(int i=0;i<3;i++) {
            String cardID=getPlayers().get(player).getPlayerHand().showCardInHand(i);
            if(cardID!=null) {
                hand[i]= ResourceCardFactory.makeResourceCard(cardID);
            } else {
                hand[i]=null;
            }
        }
        return hand;
    }
    public ArrayList<PlacedCard> getPlacedCards(String player) {
        return getPlayers().get(player).getPlayerBoard().getPlacedCards();
    }

    public Integer getScore(String currentPlayer) {
        return getPlayers().get(currentPlayer).getPlayerBoard().getScore();
    }

    public Card[] getResourceDrawableArea() {
        Card[] cards=new Card[3];
        for(int i=0;i<3;i++) {
            cards[i]=ResourceCardFactory.makeResourceCard(getDrawableArea().getResourceDrawingSection().seeCard(i));
        }
        return cards;
    }
    public Card[] getGoldDrawableArea() {
        Card[] cards=new Card[3];
        for(int i=0;i<3;i++) {
            cards[i]=ResourceCardFactory.makeResourceCard(getDrawableArea().getGoldDrawingSection().seeCard(i));
        }
        return cards;
    }

    public boolean allObjectivesHaveBeenChosen() {
        return this.allObjectivesHaveBeenChosen;
    }

    public boolean gameIsStarted() {
        return this.gameIsStarted;
    }
    public void checkIfAllObjectivesHaveBeenChosen() {
        boolean flag=true;
        for(String player: playersTurnOrder) {
            if(!(players.get(player).getPlayerBoard().seeFirstPrivateObjectiveID().equals("OBJECTIVE_ALREADY_BEEN_CHOSEN") && players.get(player).getPlayerBoard().seeSecondPrivateObjectiveID().equals("OBJECTIVE_ALREADY_BEEN_CHOSEN"))){
                flag=false;
            }
        }
        this.allObjectivesHaveBeenChosen=flag;
    }
    public Scoreboard getScoreBoard() {
        Scoreboard scoreboard=new Scoreboard();
        for(String player: playersTurnOrder) {
            scoreboard.addPlayer(players.get(player));
        }
        scoreboard.sortScoreboard();
        scoreboard.orderPositions();
        return scoreboard;
    }

    public boolean checkEndgame() {
        for(String player:playersTurnOrder) {
            if(players.get(player).getPlayerBoard().getScore()>=20){
                return true;
            }
        }
        return false;
    }
    public void endGame(){
        this.gameIsEnded=true;
    }
    public boolean gameIsEnded(){
        return this.gameIsEnded;
    }
    public void calculateEndgamePoints() {
        for(String player:playersTurnOrder) {
            players.get(player).getPlayerBoard().calculateAndUpdateObjectiveScore();
        }
    }
}
