package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.View.CLI.CardCLI;
import it.polimi.ingsw.Client.View.ViewController;
import kotlin.NotImplementedError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    private JsonObject currentCard;
    private JsonArray currentResourceDrawableArea;
    private JsonArray currentGoldDrawableArea;


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
        currentCard=messageParams.get("card").getAsJsonObject();
        currentResourceDrawableArea=messageParams.get("resourceDrawableArea").getAsJsonArray();
        currentGoldDrawableArea=messageParams.get("goldDrawableArea").getAsJsonArray();
        viewController.updateView();
    }
    /**
     * Parses the master status from server
     * @return
     * @throws IOException
     */
    public boolean masterStatus(){
        //TODO: To do exception if masterstatus==null
        Boolean masterStatus=messageParams.get("masterStatus").getAsBoolean();
        return masterStatus;
    }

    /**
     * Parses if the given username is unavailable
     * @return
     */
    public Boolean unavailableUsername(){
        return messageParams
                .get("unavailableUsername").getAsJsonPrimitive().getAsBoolean();
    }

    /**
     * Parses the current player username
     * @return
     */
    public String getCurrentPlayer() {
        return messageParams
                .get("currentPlayer").getAsJsonPrimitive().getAsString();
    }
    public String getAffectedPlayer() {
        return messageParams
                .get("affectedPlayer").getAsJsonPrimitive().getAsString();
    }
    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }

    public List<String> getWinners() {
        //TODO
        return null;
    }

    public boolean checkStart() {
        return false;
    }

    public boolean checkFinalRound() {
        return false;
    }

    public ArrayList<String> getPlayers() {
        JsonArray jsonArray=messageParams.getAsJsonObject()
                .get("players").getAsJsonArray();
        ArrayList<String> players=new ArrayList<>();
        for(JsonElement jsonElement:jsonArray) {
            players.add(jsonElement.getAsJsonPrimitive().getAsString());
        }
        return players;
    }

    public ArrayList<String> getAvailableColors() {
        JsonArray jsonArray=messageParams.getAsJsonObject()
                .get("availableColors").getAsJsonArray();
        ArrayList<String> colors=new ArrayList<>();
        for(JsonElement jsonElement:jsonArray) {
            colors.add(jsonElement.getAsJsonPrimitive().getAsString());
        }
        return colors;
    }
    private ArrayList<String> getCardFrontCenterSymbols() {
        JsonArray jsonArray=currentCard
                .get("frontCenterSymbols").getAsJsonArray();
        ArrayList<String> frontCenterSymbols=new ArrayList<>();
        for(JsonElement jsonElement:jsonArray) {
            frontCenterSymbols.add(jsonElement.getAsJsonPrimitive().getAsString());
        }
        return frontCenterSymbols;
    }

    /**
     * Parses the current card ID
     * @return
     */
    private String getCardID(JsonObject jsonCard) {
        return jsonCard
                .get("cardID").getAsJsonPrimitive().getAsString();
    }
    public String getCardID() {
        return this.getCardID(currentCard);
    }
    public String getCardID(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardID(jsonCard);
    }

    /**
     * Parses the current card backSymbol
     * @return
     */
    private String getCardBackSymbol(JsonObject jsonCard) {
        return jsonCard
                .get("backSymbol").getAsJsonPrimitive().getAsString();
    }
    public String getCardBackSymbol() {
        return this.getCardBackSymbol(currentCard);
    }
    public String getCardBackSymbol(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardBackSymbol(jsonCard);
    }

    /**
     * Parses the current card front corners
     * @return
     */
    private String[] getCardFrontCorners(JsonObject jsonCard) {
        String[] frontCorners=new String[4];
        int i=0;
        for(JsonElement corner: jsonCard
                .get("frontCorners").getAsJsonArray())
        {
            frontCorners[i++]=corner.getAsJsonPrimitive().getAsString();
        }
        return frontCorners;
    }
    public String[] getCardFrontCorners() {
        return this.getCardFrontCorners(currentCard);
    }
    public String[] getCardFrontCorners(String drawableArea,int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardFrontCorners(jsonCard);
    }

    /**
     * Parses the current card back corners
     * @return
     */
    private String[] getCardBackCorners(JsonObject jsonCard) {
        String[] frontCorners=new String[4];
        int i=0;
        for(JsonElement corner: jsonCard
                .get("backCorners").getAsJsonArray())
        {
            frontCorners[i++]=(corner.getAsJsonPrimitive().getAsString());
        }
        return frontCorners;
    }
    public String[] getCardBackCorners() {
        return this.getCardBackCorners(currentCard);
    }
    public String[] getCardBackCorners(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardBackCorners(jsonCard);
    }

    private String getCardObjective(JsonObject jsonCard) {
        return jsonCard
                .get("cardObjective").getAsJsonObject()
                .get("type").getAsJsonPrimitive().getAsString();
    }
    public String getCardObjective() {
        return this.getCardObjective(currentCard);
    }
    public String getCardObjective(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardObjective(jsonCard);
    }
    private String getCardObjectiveSymbol(JsonObject jsonCard) {
        return jsonCard
                .get("cardObjective").getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("symbolOfInterest").getAsJsonPrimitive().getAsString();
    }
    public String getCardObjectiveSymbol() {
        return this.getCardObjectiveSymbol(currentCard);
    }
    public String getCardObjectiveSymbol(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardObjectiveSymbol(jsonCard);
    }
    private int getCardObjectivePOINTS(JsonObject jsonCard) {
        return jsonCard
                .get("cardObjective").getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("points").getAsJsonPrimitive().getAsInt();
    }
    public Integer getCardObjectivePOINTS() {
        return this.getCardObjectivePOINTS(currentCard);
    }
    public Integer getCardObjectivePOINTS(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardObjectivePOINTS(jsonCard);
    }
    private ArrayList<String> getCardPrerequisites(JsonObject jsonCard) {
        ArrayList<String> prerequisites=new ArrayList<>();
        for(JsonElement symbol: jsonCard
                .get("prerequisites").getAsJsonArray())
        {
            prerequisites.add(symbol.getAsJsonPrimitive().getAsString());
        }
        return prerequisites;
    }
    public ArrayList<String> getCardPrerequisites(String drawableArea, int index) {
        JsonObject jsonCard=getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        return this.getCardPrerequisites(jsonCard);
    }
    public ArrayList<String> getCardPrerequisites() {
        return this.getCardPrerequisites(currentCard);
    }
    private JsonArray getDrawableAreaArray(String drawableArea) {
        if(drawableArea.equals("resourceDrawableArea")) {
            return this.currentResourceDrawableArea;
        } else if(drawableArea.equals("goldDrawableArea")){
            return this.currentGoldDrawableArea;
        } else {
            throw new NoSuchElementException();
        }

    }
    public CardCLI getCardCLI() {
        return new CardCLI(
                getCardID(),
                getCardFrontCorners(),
                getCardBackCorners(),
                getCardObjective(),
                getCardObjectiveSymbol(),
                getCardObjectivePOINTS(),
                getCardPrerequisites()
        );
    }
    public CardCLI getCardCLI(String drawingSection, int index) {
        return new CardCLI(
                getCardID(drawingSection,index),
                getCardFrontCorners(drawingSection,index),
                getCardBackCorners(drawingSection,index),
                getCardObjective(drawingSection,index),
                getCardObjectiveSymbol(drawingSection,index),
                getCardObjectivePOINTS(drawingSection,index),
                getCardPrerequisites(drawingSection,index)
        );
    }
    public CardCLI getStartingCardCLI() {
        return new CardCLI(
                getCardID(),
                getCardFrontCorners(),
                getCardBackCorners(),
                getCardFrontCenterSymbols()
        );
    }

}

