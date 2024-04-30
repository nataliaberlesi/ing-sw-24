package it.polimi.ingsw.Client.Network;

import java.io.IOException;

public class Dispatcher {
    private final NetworkManager networkManager;
    public Dispatcher(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * dispatches a message
     * @param type
     * @param method
     * @param params
     * @throws IOException
     */
    public void dispatch(String type, String method, Object...params) throws IOException{
        Message message=new Message(type,method);
        for(Object param: params) {
            message.addParam(param);
        }
        networkManager.send(message);
    }
}
