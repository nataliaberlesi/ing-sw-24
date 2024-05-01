package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Server.Network.Message;

import java.io.IOException;

public class Dispatcher {
    private PlayerConnection playerConnection;
    public Dispatcher(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
    }
    public void setPlayerConnection(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
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
        playerConnection.send(message);
    }

}
