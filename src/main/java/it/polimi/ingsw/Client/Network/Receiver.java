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

    /**
     * receives the drawCard response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public String drawCard() throws IOException, MessageHandlerException {
        Message receivedMessage=networkManager.receive();
        messageHandler.handleAndCheck(receivedMessage,"GAME","drawCard");
        return (String)receivedMessage.getParams().get(0);
    }

    /**
     * receives the placeCard response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean placeCard() throws IOException, MessageHandlerException {
        Message receivedMessage=networkManager.receive();
        messageHandler.handleAndCheck(receivedMessage,"GAME","placeCard");
        return (Boolean)receivedMessage.getParams().get(0);
    }

    /**
     * receives the masterstatus response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean masterStatus() throws IOException, MessageHandlerException {
        Message receivedMessage=networkManager.receive();
        messageHandler.handleAndCheck(receivedMessage,"GAME","masterStatus");
        return (Boolean)receivedMessage.getParams().get(0);
    }

    /**
     * receives the createGame response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean createGame() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }

    /**
     * receives the joinGame response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean joinGame() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }

    /**
     * receives the checkWaitForStart response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean checkWaitForStart() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }

    /**
     * receives the unavailableUsername response
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean unavailableUsername() throws IOException, MessageHandlerException{
        Message receivedmessage=networkManager.receive();
        messageHandler.handle(receivedmessage);
        return (Boolean)receivedmessage.getParams().getFirst();
    }

}
