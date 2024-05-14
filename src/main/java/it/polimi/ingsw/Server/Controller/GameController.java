package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
import it.polimi.ingsw.Server.Controller.DTO.StartFirstRound;
import it.polimi.ingsw.Server.Controller.DTO.StartSecondRound;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Network.*;
import it.polimi.ingsw.Server.Model.Color;

import java.io.IOException;

public class GameController {
    private MessageParser messageParser;
    private GameInstance gameInstance;
    private Server server;
    private Parser parser;
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
        return null;
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
        MessageType type=MessageType.CREATE;
        return new Message(type, null);
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
        JsonObject jsonParams=new JsonObject();
        jsonParams.addProperty("unavailableUsername",unavailableUsername);
        return new Message(MessageType.JOIN,jsonParams);

    }
    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters in JSON format
     */
    public JsonObject getJSONStartFirstRoundParams() {
        StartFirstRound startFirstRound=SetUpGame.getStartFirstRoundParams(gameInstance);
        return parser.toJsonObject(parser.toJson(startFirstRound));
    }

    /**
+     * Plays the current player's first round with given parameters
     * @param username
     * @param jsonParams
     * @return
     */
    public Message playFirstRound(String username, JsonObject jsonParams) {
        FirstRound firstRound=messageParser.parseFirstRound(username,jsonParams);
        gameInstance.chooseColor(username, firstRound.color());
        gameInstance.placeStartingCard(username, firstRound.flip());
        JsonObject params=new JsonObject();
        if(gameInstance.nextTurn()<= gameInstance.getNumberOfPlayers()) {
            String startingCardID=gameInstance.getStartingDeck().next();
            params.add("card",parser.toJsonObject(parser.toJson(StartingCardFactory.makeStartingCard(startingCardID))));
            gameInstance.saveStartingCard(username,startingCardID);
        }
        params.addProperty("currentPlayer", gameInstance.getTurn());
        JsonArray availableColors= new JsonArray();
        for(Color availableColor: gameInstance.getAvailableColors()) {
            availableColors.add(availableColor.toString());
        }
        params.add("availableColors", availableColors);
        params.addProperty("affectedPlayer",username);
        params.addProperty("flip",firstRound.flip());
        params.addProperty("color",firstRound.color());
        gameInstance.checkIfAllBoardsAreSet();
        return new Message(MessageType.FIRSTROUND,params);
    }
    public JsonObject getJSONStartSecondRoundParams() {
        StartSecondRound startSecondRound=SetUpGame.getStartSecondRoundParams(gameInstance);
        return parser.toJsonObject(parser.toJson(startSecondRound));
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
