package it.polimi.ingsw.Client.Network;

import java.io.IOException;

/**
 * Contains methods to receive and handle a message from the NetworkManager, also extracting his valuable data
 */
public class Receiver {

    private NetworkManager networkManager;
    private MessageHandler messageHandler;
    public Receiver(NetworkManager networkManager) {
        this.networkManager=networkManager;

    }

    public String drawCard() throws IOException, MessageHandlerException {
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (String)receivedmessage.getParams().get(0);
    }
    public boolean placeCard() throws IOException, MessageHandlerException {
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().get(0);
    }
    public boolean masterStatus() throws IOException, MessageHandlerException {
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().get(0);
    }
    public boolean createGame() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }
    public boolean joinGame() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }
    public boolean checkWaitForStart() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }

}
