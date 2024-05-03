package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Network.PlayerConnection;

import java.io.IOException;
import java.util.ArrayList;

public class Gateway {
    private final PlayerConnection playerConnection;
    private MessageHandler messageHandler;
    private ArrayList<Object> lastParams;
    public Gateway(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
        this.messageHandler=new MessageHandler();
    }
    public void dispatch(String type, String method, Object...params) throws IOException{
        Message message=new Message(type,method);
        for(Object param: params) {
            message.addParam(param);
        }
        playerConnection.send(message);
    }
    public ArrayList<Object> receive(String expectedType, String expectedMethod) throws IOException {
        Message receivedMessage=playerConnection.receive();
        messageHandler.handleAndCheck(receivedMessage,expectedType,expectedMethod);
        return receivedMessage.getParams();
    }

    /**
     * Says to the corresponding playerConnection if it's a master connection
     * @param isMaster
     * @throws IOException
     */
    public void masterStatus(boolean isMaster) throws IOException {
        dispatch("SYSTEM","masterStatus",isMaster);
    }

    /**
     * Creates the game using the given info
     * @throws IOException
     */
    public void createGame() throws IOException {
        this.lastParams=receive("SYSTEM","createGame");
    }
    public String startGame() throws IOException {
        this.lastParams=receive("SYSTEM","startGame");
        return (String)lastParams.get(0);
    }
    public ArrayList<Object> getLastParams() {
        return lastParams;
    }
}
