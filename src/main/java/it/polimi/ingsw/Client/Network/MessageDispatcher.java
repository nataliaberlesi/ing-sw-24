package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.Network.DTO.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Used to compose a message and send it to the server
 */
public class MessageDispatcher {
    private NetworkManager networkManager;
    private MessageParser messageParser;
    public MessageDispatcher(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.messageParser=MessageParser.getInstance();
    }
    /**
     * dispatches a message
     * @param type
     * @param params
     * @throws IOException
     */
    public void dispatch(MessageType type, JsonObject params){
        Message message=new Message(type, params);
        networkManager.setOutMessage(messageParser.toJson(message));
    }
    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public void createGame(int numberOfPlayer, String username) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,numberOfPlayer);
        dispatch(MessageType.CREATE,messageParser.toJsonObject(outParamsDTO));
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public void joinGame(String username) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username);
        dispatch(MessageType.JOIN,messageParser.toJsonObject(outParamsDTO));
    }
    public void firstRound(String username, Boolean isFaceUp, String color) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,isFaceUp,color);
        dispatch(MessageType.FIRSTROUND,messageParser.toJsonObject(outParamsDTO));
    }
    public void secondRound(String username, Integer index) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(index,username);
        dispatch(MessageType.SECONDROUND,messageParser.toJsonObject(outParamsDTO));
    }
}
