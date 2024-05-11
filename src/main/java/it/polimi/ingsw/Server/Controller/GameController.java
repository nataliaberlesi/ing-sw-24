package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
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
                return createGame(username,messageParams);
            }
            case JOIN -> {
                return joinGame(username);

            }
            case FIRSTROUND -> {
                return playFirstRound(messageParams);
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
    public Message createGame(String username,String messageParams){
        if(this.gameInstance!=null){
            MessageType type=MessageType.ERROR;
            String params="Game is already created";
            return new Message(type, parser.toJsonObject(params));
        }
        CreateGame createGame=messageParser.parseCreateGame(username,messageParams);
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
        String outMessageParams=parser.toJson(unavailableUsername);
        return this.craftJSONMessage(MessageType.JOIN, outMessageParams);

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
    public Message playFirstRound(String params) {
        FirstRound firstRound=messageParser.parseFirstRound(params);

        return new Message(MessageType.FIRSTROUND,null);
    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
    }
    public boolean gameIsStarted() {
        return gameInstance.gameIsStarted();
    }
    public Message craftJSONMessage(MessageType messageType, String params) {
        return new Message(messageType, parser.toJsonObject(params));
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
