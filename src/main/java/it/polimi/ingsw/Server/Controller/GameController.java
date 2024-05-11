package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.StartFirstRound;
import it.polimi.ingsw.Server.Network.*;

import java.io.IOException;
import java.util.HashMap;

public class GameController {
    private MessageParser messageParser;
    private GameInstance gameInstance;
    private Server server;
    private Parser parser;
    public GameController() {
        this.messageParser=new MessageParser();
    }

    public Message dispatchMessage(String message) {
        MessageType messageType=messageParser.getMessageType(message);
        String username=messageParser.getUsername(message);
        String messageParams=messageParser.getMessageParams(message);
        return queryModel(messageType, username, messageParams);
    }
    /**
     * Given a specific type of message, queries the model to get/change some information
     */
    public Message queryModel(MessageType messageType,String username, String messageParams) {
        JsonObject jsonParams=messageParser.getParser().toJsonObject(messageParams);
        switch (messageType) {
            case CREATE -> {
                return createGame(messageParams);
            }
            case JOIN -> {
                return joinGame(username);

            }
            case FIRSTROUND -> {
                //TODO
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
     * Creates a game
     */
    public Message createGame(String messageParams){
        if(this.gameInstance!=null){
            MessageType type=MessageType.ERROR;
            String params="Game is already created";
            return new Message(type, params);
        }
        CreateGame createGame=messageParser.parseCreateGame(messageParams);
        this.server.setMaxAllowablePlayers(createGame.numberOfPlayers());
        this.gameInstance=new GameInstance(createGame.username(),createGame.numberOfPlayers());
        MessageType type=MessageType.CREATE;
        String params="OK";
        return new Message(type, params);
    }
    public Message joinGame(String username) {
        Boolean unavailableUsername=this.gameInstance.unavailableUsername(username);
        if(!unavailableUsername){
            gameInstance.joinPlayer(username);
        }
        String outMessageParams=parser.toJson(unavailableUsername);
        return this.craftJSONMessage(MessageType.JOIN, outMessageParams);

    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
    }
    public boolean gameIsStarted() {
        return gameInstance.gameIsStarted();
    }

    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters in JSON format
     */
    public String getJSONStartFirstRoundParams() {
        StartFirstRound startFirstRound=SetUpGame.getStartFirstRoundParams(gameInstance);
        gameInstance.setDrawableArea(startFirstRound.drawableArea());
        return parser.toJson(startFirstRound);
    }
    public Message craftJSONMessage(MessageType messageType, String params) {
        return new Message(messageType, params);
    }

    /**
     * Starts a game when all user are joined
     * @throws IOException
     */
    public void startGame() throws IOException {
    }

    /**
     *
     * @throws IOException
     */
    public void chooseColor() throws IOException {
    }
    public void shutDown() {

    }
}
