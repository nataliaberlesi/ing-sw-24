package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Used to compose a message and send it to the server
 */
public class MessageDispatcher {
    private NetworkManager networkManager;
    private Parser parser;
    public MessageDispatcher(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.parser=Parser.getInstance();
    }
    /**
     * dispatches a message
     * @param type
     * @param params
     * @throws IOException
     */
    public void dispatch(MessageType type, JsonObject params){
        Message message=new Message(type, params);
        networkManager.setOutMessage(parser.toString(message));
    }
    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public void createGame(int numberOfPlayer, String username) {
        JsonObject params=new JsonObject();
        params.addProperty("username",username);
        params.addProperty("numberOfPlayers",numberOfPlayer);
        dispatch(MessageType.CREATE,params);
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public void joinGame(String username) {
        JsonObject params=new JsonObject();
        params.addProperty("username",username);
        dispatch(MessageType.JOIN,params);
    }
    public void firstRound(String username, boolean isFaceUp, String color) {
        JsonObject params=new JsonObject();
        params.addProperty("username",username);
        params.addProperty("isFaceUp",isFaceUp);
        params.addProperty("color",color);
        dispatch(MessageType.CREATE,params);
    }
}
