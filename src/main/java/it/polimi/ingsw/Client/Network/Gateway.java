package it.polimi.ingsw.Client.Network;

import it.polimi.ingsw.Client.Network.MessageHandler;
import it.polimi.ingsw.Client.View.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Gateway of communication between client and server to call methods on Model
 * */
public class Gateway {
    private NetworkManager networkManager;
    private MessageHandler messageHandler;

    /**
     * Client chosen view.
     */
    private View view;
    /**
     * Received message type.
     */
    private MessageType messageType;

    public Gateway(NetworkManager networkManager) {
        this.networkManager=networkManager;
    }

    /**
     * Set client chosen view.
     *
     * @param view client chosen view
     */
    public void setView(View view) {
        this.view = view;
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

    public ArrayList<Object> receive(String expectedType, String expectedMethod) throws IOException {
        Message receivedMessage=networkManager.receive();
        messageHandler.handleAndCheck(receivedMessage,expectedType,expectedMethod);
        return receivedMessage.getParams();
    }

    /**
     * Dispatches the placeCard message
     * @param card
     * @param x
     * @param y
     * @param isFlipped
     * @return the response to the message
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean placeCard(String card, int x, int y, boolean isFlipped) throws IOException,MessageHandlerException {
        dispatch("GAME","placeCard",card,x,y,isFlipped);
        return (Boolean)receive("GAME","placeCard").get(0);
    }

    /**
     * Dispatches the drawCard message
     * @param cardIndex
     * @param drawingSection
     * @return the response to the message
     * @throws IOException
     * @throws MessageHandlerException
     */
    public String drawCard(int cardIndex, String drawingSection) throws IOException,MessageHandlerException {
        dispatch("GAME","drawCard",cardIndex,drawingSection);
        return (String)receive("GAME","drawCard").get(0);
    }

    /**
     * receives the master status
     * @return
     * @throws IOException
     */
    public boolean masterStatus() throws IOException {
        return (Boolean)receive("SYSTEM","masterStatus").getFirst();
    }

    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public void createGame(int playersNumber, String masterUsername) throws IOException,MessageHandlerException {
        dispatch("SYSTEM","createGame",playersNumber,masterUsername);
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public void joinGame(String playerUsername) throws IOException {
        dispatch("SYSTEM","joinGame",playerUsername);
    }
    //TODO: server side -> returns true when all players for the game have been created
    public boolean checkWaitForStart() throws IOException {
        dispatch("SYSTEM","checkWaitForStart");
        return (Boolean)receive("SYSTEM","checkWaitForStart").getFirst();
    }
    //TODO: server side -> returns true if chosen username for this player is already used by another player
    public boolean unavailableUsername(String playerUsername) throws IOException {
        dispatch("SYSTEM","unavailableUsername",playerUsername);
        return (Boolean)receive("SYSTEM","unavailableUsername").getFirst();
    }

    public String getInitialCard(String playerUsername) throws IOException {
        dispatch("SYSTEM", "getInitialCard", playerUsername);
        return (String) receive("SYSTEM", "getInitialCard").getFirst();
    }

}
