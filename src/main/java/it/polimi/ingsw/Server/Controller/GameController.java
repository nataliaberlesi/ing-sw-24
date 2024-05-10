package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Network.*;

import java.io.IOException;

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
                return joinGame(username,messageParams);

            }
            case START_FIRSTROUND -> {

            }
            case ACTION -> {
            }
            case FINAL_ROUND -> {

            }
            case ABORT -> {

            }
            case ERROR -> {

            }
        }

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
    public Message joinGame(String username, String messageParams) {
        Boolean unavailableUsername=this.gameInstance.unavailableUsername(username);
        if(unavailableUsername) {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("unavailableUsername",unavailableUsername);
            return this.craftJSONMessage(MessageType.JOIN, jsonObject.toString());
        }
        gameInstance.joinPlayer(username);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("unavailableUsername",unavailableUsername);
        jsonObject.addProperty("full",gameInstance.checkIfGameIsFull());
        jsonObject.addProperty("username",username);
        return this.craftJSONMessage(MessageType.JOIN, jsonObject.toString());

    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
    }
    public boolean gameIsStarted() {
        return this.gameIsStarted();
    }
    public String getJSONStartParams() {
        //TODO
        return"";
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
