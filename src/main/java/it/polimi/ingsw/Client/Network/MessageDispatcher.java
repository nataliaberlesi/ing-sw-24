package it.polimi.ingsw.Client.Network;

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
    public void dispatch(MessageType type, String username, Object...params){
        ArrayList<Object> parameters=new ArrayList<>();
        for(Object param: params) {
            parameters.add(param);
        }
        String param=parser.toString(parameters);
        Message message=new Message(type, username,param);
        networkManager.setOutMessage(parser.toString(message));
    }
    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public void createGame(int playersNumber, String masterUsername) {
        dispatch(MessageType.CREATE,masterUsername,playersNumber);
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public void joinGame(String playerUsername) {
        dispatch(MessageType.JOIN,playerUsername);
    }

    public void startGame(String username) {
    }
}
