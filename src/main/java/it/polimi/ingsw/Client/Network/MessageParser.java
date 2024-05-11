package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.View.ViewController;

import java.io.IOException;
import java.util.List;

/**
 * Used to parse data from the incoming message from server
 * */
public class MessageParser {
    private NetworkManager networkManager;
    private ViewController viewController;
    private Parser parser;
    private JsonObject currentMessage;
    private JsonElement messageParams;
    private MessageType messageType;


    public MessageParser(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.parser=Parser.getInstance();
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public void buildMessage(String inMessage) {
        currentMessage=parser.toJsonObject(inMessage);
        messageType=MessageType.valueOf(currentMessage.get("type").getAsString());
        messageParams=currentMessage.get("params");
        viewController.updateView();
    }
    /**
     * Dispatches the placeCard message
     * @param card
     * @param x
     * @param y
     * @param isFlipped
     * @return the response to the message
     * @throws IOException
     */
    public boolean placeCard(String card, int x, int y, boolean isFlipped){
        //TODO
        return true;
    }

    /**
     * Dispatches the drawCard message
     * @param cardIndex
     * @param drawingSection
     * @return the response to the message
     * @throws IOException
     */
    public String drawCard(int cardIndex, String drawingSection){
        //TODO
        return "";
    }

    /**
     * receives the master status
     * @return
     * @throws IOException
     */
    public boolean masterStatus(){
        //TODO: To do exception if masterstatus==null
        return messageParams.getAsJsonPrimitive().getAsBoolean();
    }
    //TODO: server side -> returns true when all players for the game have been created
    public boolean checkWaitForStart(){
        return true;
    }
    //TODO: server side -> returns true if chosen username for this player is already used by another player
    public Boolean unavailableUsername(){
        return messageParams.getAsBoolean();
    }

    public String getInitialCard(){
        return messageParams.getAsJsonObject().get("initialCard").getAsString();
    }
    public String getCurrentPlayer() {
        return messageParams.getAsJsonObject().get("currentPlayer").getAsString();
    }
    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }

    public List<String> getWinners() {
        //TODO
        return null;
    }

    public Object getUsername() {
        return null;
    }

    public boolean checkStart() {
        return false;
    }

    public boolean checkFinalRound() {
        return false;
    }
}

