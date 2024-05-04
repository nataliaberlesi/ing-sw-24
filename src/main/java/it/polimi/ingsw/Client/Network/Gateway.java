package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;
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
    private View view;
    private Parser parser;
    private JsonObject currentMessage;
    private JsonObject messageParams;
    private MessageType messageType;


    public Gateway(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.parser=Parser.getInstance();
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public String getMessageParams(){
        return messageParams.get("params").getAsString();
    }
    public void buildMessage(String inMessage) {
        currentMessage=parser.toJsonObject(inMessage);
        messageType=MessageType.valueOf(currentMessage.get("type").getAsString());
        messageParams=parser.toJsonObject(currentMessage.get("params").getAsString());
        view.updateView();
    }
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
        //TODO
        return true;
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
        //TODO
        return "";
    }

    /**
     * receives the master status
     * @return
     * @throws IOException
     */
    public boolean masterStatus() throws IOException {
        //TODO
        return true;
    }

    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public void createGame(int playersNumber, String masterUsername) throws IOException,MessageHandlerException {
        //TODO
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public void joinGame(String playerUsername) throws IOException {
        //TODO
    }
    //TODO: server side -> returns true when all players for the game have been created
    public boolean checkWaitForStart() throws IOException {
        //TODO
        return true;
    }
    //TODO: server side -> returns true if chosen username for this player is already used by another player
    public boolean unavailableUsername(String playerUsername) throws IOException {
        //TODO
        return true;
    }

    public String getInitialCard(String playerUsername) throws IOException {
        //TODO
        return "";
    }

}
