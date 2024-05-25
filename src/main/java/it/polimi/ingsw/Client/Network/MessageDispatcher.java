package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.Network.DTO.*;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CoordinatesDTO;
import it.polimi.ingsw.Server.Model.Coordinates;

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
    private void dispatch(MessageType type, JsonObject params){
        Message message=new Message(type, params);
        networkManager.setOutMessage(messageParser.toJson(message));
    }
    public void createGame(int numberOfPlayer, String username) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,numberOfPlayer);
        dispatch(MessageType.CREATE,messageParser.toJsonObject(outParamsDTO));
    }
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
    public void placeCard(String username, Boolean isFaceUp, Integer index, Coordinates coordinates) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, isFaceUp, index, new CoordinatesDTO(coordinates.getX(), coordinates.getY()));
        dispatch(MessageType.ACTION_PLACECARD, messageParser.toJsonObject(outParamsDTO));
    }
    public void drawCard(String username, Integer index, String drawableSection) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,index,drawableSection);
        dispatch(MessageType.ACTION_DRAWCARD, messageParser.toJsonObject(outParamsDTO));
    }
    public void abortGame(String username, String cause) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, cause);
        dispatch(MessageType.ABORT, messageParser.toJsonObject(outParamsDTO));
    }
    public void notifyPersistence(boolean persistence) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(persistence);
        dispatch(MessageType.PERSISTENCE,messageParser.toJsonObject(outParamsDTO));
    }
    public void chat(String username, String affectedPlayer, String chat) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, affectedPlayer,chat);
        dispatch(MessageType.CHAT,messageParser.toJsonObject(outParamsDTO));
    }
}
