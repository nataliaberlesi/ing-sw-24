package it.polimi.ingsw.Client.Network;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Used to compose a message and send it to the server
 */
public class MessageDispatcher {
    private NetworkManager networkManager;
    private Parser parser;
    /**
     * dispatches a message
     * @param type
     * @param params
     * @throws IOException
     */
    public void dispatch(MessageType type, String username, Object...params) throws IOException{
        ArrayList<Object> parameters=new ArrayList<>();
        for(Object param: params) {
            parameters.add(param);
        }
        String param=parser.toString(parameters);
        Message message=new Message(type, username,param);
        networkManager.setOutMessage(parser.toString(message));
    }
}
