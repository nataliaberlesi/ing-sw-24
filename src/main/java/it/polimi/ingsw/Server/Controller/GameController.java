package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
import it.polimi.ingsw.Server.Controller.DTO.StartFirstRound;
import it.polimi.ingsw.Server.Controller.DTO.StartSecondRound;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Model.PlacedCard;
import it.polimi.ingsw.Server.Network.*;
import it.polimi.ingsw.Server.Model.Color;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    private final MessageParser messageParser;
    private GameInstance gameInstance;
    private final Server server;
    private final Parser parser;
    private MessageType previousMessageType;
    public GameController(Server server) {
        this.server=server;
        this.parser=Parser.getInstance();
        this.messageParser=new MessageParser();
    }

    public Message dispatchMessage(String message) {
        MessageType messageType=messageParser.getMessageType(message);
        String username=messageParser.getUsername(message);
        JsonObject jsonParams=messageParser.getMessageParams(message);
        return queryModel(messageType, username, jsonParams);
    }
    /**
     * Given a specific type of message, queries the model to get/change some information
     */
    public Message queryModel(MessageType messageType,String username, JsonObject jsonParams) {
        switch (messageType) {
            case CREATE -> {
                return createGame(username,jsonParams);
            }
            case JOIN -> {
                return joinGame(username);

            }
            case FIRSTROUND -> {
                return playFirstRound(username,jsonParams);
            }
            case SECONDROUND -> {
                //TODO
            }
            case ACTION_PLACECARD -> {
                //TODO
            }
            case ACTION_DRAWCARD -> {
                //TODO
            }
            case ABORT -> {
                //TODO
            }
            case ERROR -> {
                //TODO
            }
        }
        return MessageCrafter.craftErrorMessage(previousMessageType,username);
    }

    /**
     * Creates a game using master player data
     */
    public Message createGame(String username,JsonObject jsonParams){
        if(this.gameInstance!=null){
            MessageType type=MessageType.ABORT;
            String params="Game is already created";
            return new Message(type, null);
        }
        CreateGame createGame=messageParser.parseCreateGame(username,jsonParams);
        this.server.setMaxAllowablePlayers(createGame.numberOfPlayers());
        this.gameInstance=new GameInstance(createGame.username(),createGame.numberOfPlayers());
        Message message = MessageCrafter.craftCreateMessage(username);
        previousMessageType=message.type();
        return message;
    }

    /**
     * Lets a player join a game if his username is not taken
     * @param username of the joining player
     * @return an affermative answer if the username is available, a negative answer otherwise
     */
    public Message joinGame(String username) {
        Boolean unavailableUsername=this.gameInstance.unavailableUsername(username);
        if(!unavailableUsername){
            gameInstance.joinPlayer(username);
        }
        Message message = MessageCrafter.craftJoinMessage(username,unavailableUsername);
        previousMessageType=message.type();
        return message;

    }
    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters in JSON format
     */
    public JsonObject getJSONStartFirstRoundParams() {
        StartFirstRound startFirstRound=SetUpGame.getStartFirstRoundParams(gameInstance);
        return parser.toJsonTree(startFirstRound).getAsJsonObject();
    }

    /**
+     * Plays the current player's first round with given parameters
     * @param username the player who is playing
     * @param jsonParams the parameters of his action
     * @return
     */
    public Message playFirstRound(String username, JsonObject jsonParams) {
        FirstRound firstRound=messageParser.parseFirstRound(jsonParams);
        String card=null;
        gameInstance.chooseColor(username, firstRound.color());
        gameInstance.placeStartingCard(username, firstRound.flip());
        ArrayList<PlacedCard> placedCards=gameInstance.getPlayers().get(username).getPlayerBoard().getPlacedCards();
        int turn=gameInstance.nextTurn();
        String currentPlayer=gameInstance.getTurn();
        if(turn!=0) {
            card=gameInstance.getStartingDeck().next();
            gameInstance.saveStartingCard(currentPlayer,card);
        }
        gameInstance.checkIfAllBoardsAreSet();
        Message message = MessageCrafter.craftFirstRoundMessage(card,currentPlayer,gameInstance.getAvailableColors(),username,placedCards,firstRound.color());
        previousMessageType=message.type();
        return message;
    }
    public JsonObject getJSONStartSecondRoundParams() {
        StartSecondRound startSecondRound=SetUpGame.getStartSecondRoundParams(gameInstance);
        return parser.toJsonTree(startSecondRound).getAsJsonObject();
    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
    }
    public boolean firstRoundIsStarted() {
        return gameInstance.firstRoundIsStarted();
    }
    public boolean secondRoundIsStarted() {
        return gameInstance.secondRoundIsStarted();
    }
    public Message craftJSONMessage(MessageType messageType, String params) {
        return new Message(messageType, parser.toJsonObject(params));
    }

    /**
     * Starts a game when all user are joined
     * @throws IOException
     */
    public void startFirstRound(){
        this.gameInstance.startFirstRound();
    }
    public void startSecondRound() {
        this.gameInstance.startSecondRound();
    }
    /**
     *
     * @throws IOException
     */
    public void chooseColor() throws IOException {
    }
    public void shutDown() {

    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public boolean allBoardsAreSet() {
        return this.gameInstance.allBoardsAreSet();
    }
}
