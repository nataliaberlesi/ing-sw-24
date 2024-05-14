package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.View.ViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to parse data from the incoming message from server
 * */
public class MessageParser {
    private NetworkManager networkManager;
    private ViewController viewController;
    private Parser parser;
    private JsonObject currentMessage;
    private JsonObject messageParams;
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
        messageType=MessageType.valueOf(currentMessage.get("type").getAsJsonPrimitive().getAsString());
        messageParams=currentMessage.getAsJsonObject("params");
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
        Boolean masterStatus=messageParams.get("masterStatus").getAsBoolean();
        return masterStatus;
    }
    //TODO: server side -> returns true when all players for the game have been created
    public boolean checkWaitForStart(){
        return true;
    }
    //TODO: server side -> returns true if chosen username for this player is already used by another player
    public Boolean unavailableUsername(){
        return messageParams.get("unavailableUsername").getAsJsonPrimitive().getAsBoolean();
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

    public String getUsername() {
        return null;
    }

    public boolean checkStart() {
        return false;
    }

    public boolean checkFinalRound() {
        return false;
    }

    public ArrayList<String> getPlayers() {
        JsonArray jsonArray=messageParams.getAsJsonObject().get("players").getAsJsonArray();
        ArrayList<String> players=new ArrayList<>();
        for(JsonElement jsonElement:jsonArray) {
            players.add(jsonElement.getAsJsonPrimitive().getAsString());
        }
        return players;
    }

    public ArrayList<String> getAvailableColors() {
        JsonArray jsonArray=messageParams.getAsJsonObject().get("availableColors").getAsJsonArray();
        ArrayList<String> colors=new ArrayList<>();
        for(JsonElement jsonElement:jsonArray) {
            colors.add(jsonElement.getAsJsonPrimitive().getAsString());
        }
        return colors;
    }

    /**
     * Parses the current card ID
     * @return
     */
    public String getCardID() {
        return messageParams.get("card").getAsJsonObject().get("cardID").getAsJsonPrimitive().getAsString();
    }

    /**
     * Parses the current card backSymbol
     * @return
     */
    public String getCardBackSymbol() {
        return messageParams.get("card").getAsJsonObject().get("backSymbol").getAsJsonPrimitive().getAsString();
    }

    /**
     * Parses the current card front corners
     * @return
     */
    public ArrayList<String> getCardFrontCorners() {
        ArrayList<String> frontCorners=new ArrayList<>();
        for(JsonElement corner: messageParams.get("card").getAsJsonObject().get("frontCorners").getAsJsonArray())
        {
            frontCorners.add(corner.getAsJsonPrimitive().getAsString());
        }
        return frontCorners;
    }

    /**
     * Parses the current card back corners
     * @return
     */
    public ArrayList<String> getCardBackCorners() {
        ArrayList<String> frontCorners=new ArrayList<>();
        for(JsonElement corner: messageParams.get("card").getAsJsonObject().get("backCorners").getAsJsonArray())
        {
            frontCorners.add(corner.getAsJsonPrimitive().getAsString());
        }
        return frontCorners;
    }
    public String getCardObjective() {
        return messageParams.get("card").getAsJsonObject().get("cardObjective").getAsJsonObject().get("type").getAsJsonPrimitive().getAsString();
    }
    public String getCardObjectiveSymbol() {
        return messageParams.get("card").getAsJsonObject().get("cardObjective").getAsJsonObject().get("data").getAsJsonObject().get("symbolOfInterest").getAsJsonPrimitive().getAsString();
    }
    public int getCardObjectivePOINTS() {
        return messageParams.get("card").getAsJsonObject().get("cardObjective").getAsJsonObject().get("data").getAsJsonObject().get("POINTS").getAsJsonPrimitive().getAsInt();
    }
    public ArrayList<String> getCardPrerequisites() {
        ArrayList<String> prerequisites=new ArrayList<>();
        for(JsonElement symbol: messageParams.get("card").getAsJsonObject().get("prerequisites").getAsJsonArray())
        {
            prerequisites.add(symbol.getAsJsonPrimitive().getAsString());
        }
        return prerequisites;
    }


}

