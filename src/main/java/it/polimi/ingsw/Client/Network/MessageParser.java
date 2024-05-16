package it.polimi.ingsw.Client.Network;

import com.google.gson.*;
import it.polimi.ingsw.Client.Network.DTO.CardDTO;
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
    private Gson parser;
    private JsonObject currentMessage;
    private JsonObject messageParams;
    private MessageType messageType;


    public MessageParser(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.parser=new Gson();
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public void buildMessage(String inMessage) {
        currentMessage= JsonParser.parseString(inMessage).getAsJsonObject();
        try {
            try {
                messageType=MessageType.valueOf(currentMessage.get("type").getAsJsonPrimitive().getAsString());
            } catch(NullPointerException npe) {
                messageType=null;
            }
            try {
                messageParams=currentMessage.getAsJsonObject("params");
            } catch(NullPointerException npe) {
                messageParams=null;
            }
        } catch(IllegalStateException ise) {
            throw new MessageParserException("Illegal message");
        }
        viewController.updateView();
    }
    /**
     * Parses the master status from server
     * @return
     * @throws IOException
     */
    public boolean masterStatus(){
        try {
            return messageParams.get("masterStatus").getAsBoolean();
        } catch(NullPointerException npe) {
            throw new MessageParserException("Unexpected null masterstatus");
        } catch(IllegalStateException ise) {
            throw new MessageParserException("masterStatus is not Boolean");
        }
    }

    /**
     * Parses if the given username is unavailable
     * @return
     */
    public Boolean unavailableUsername(){
        try {
            return messageParams
                    .get("unavailableUsername").getAsBoolean();
        } catch(NullPointerException npe) {
            throw new MessageParserException("Unexpected null unavailableUsername");
        } catch(IllegalStateException ise) {
            throw new MessageParserException("unavailableUsername is not Boolean");
        }
    }

    /**
     * Parses the current player username
     * @return
     */
    public String getCurrentPlayer() {
        try {
            return messageParams
                    .get("currentPlayer").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("currentPlayer is not a String");
        }
    }

    /**
     * Parses the affected player username
     * @return
     */
    public String getAffectedPlayer() {
        try {
            return messageParams
                    .get("affectedPlayer").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("affectedPlayer is not a String");
        }
    }

    /**
     * Sets the controlled view
     * @param viewController
     */
    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }

    /**
     * Parses the list of winners
     * @return
     */
    public List<String> getWinners() {
        //TODO
        return null;
    }

    /**
     * Parses the ordered list of players
     * @return
     */
    public ArrayList<String> getPlayers() {
        ArrayList<String> players=new ArrayList<>();
        try {
            JsonArray jsonArray=messageParams.getAsJsonObject()
                    .get("players").getAsJsonArray();
            for(JsonElement jsonElement:jsonArray) {
                players.add(jsonElement.getAsJsonPrimitive().getAsString());
            }
        } catch(NullPointerException npe){
            return players;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("Players are not an array");
        }
        return players;
    }

    /**
     * Parses the list of available colors to choose
     * @return
     */
    public ArrayList<String> getAvailableColors() {
        ArrayList<String> colors=new ArrayList<>();
        try{
            JsonArray jsonArray=messageParams.getAsJsonObject()
                    .get("availableColors").getAsJsonArray();
            for(JsonElement jsonElement:jsonArray) {
                colors.add(jsonElement.getAsJsonPrimitive().getAsString());
            }
            return colors;
        } catch(NullPointerException npe) {
            return colors;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("Colors are not an array");
        }
    }

    /**
     * Parses the chosen color by the affected player
     * @return
     */
    public String getColor() {
        try {
            return messageParams.get("color").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        }
    }

    /**
     * Parses the starting card front center symbols
     * @return
     */
    private ArrayList<String> getCardFrontCenterSymbols() {
        ArrayList<String> frontCenterSymbols=new ArrayList<>();
        try {
            JsonArray jsonArray=getCard()
                    .get("frontCenterSymbols").getAsJsonArray();
            for(JsonElement jsonElement:jsonArray) {
                frontCenterSymbols.add(jsonElement.getAsJsonPrimitive().getAsString());
            }
        } catch(NullPointerException npe) {
            return frontCenterSymbols;
        }
        return frontCenterSymbols;
    }
    private JsonObject getCard() {
        try{
            return messageParams.get("card").getAsJsonObject();
        }catch(NullPointerException npe) {
            return null;
        }catch(IllegalStateException ise) {
            throw new MessageParserException("Card is not serialized");
        }
    }
    private JsonObject getCard(String drawableArea, int index) {
        if(getDrawableAreaArray(drawableArea).get(index).isJsonNull()) {
            return null;
        } else {
            return getDrawableAreaArray(drawableArea).get(index).getAsJsonObject();
        }
    }
    /**
     * Parses the current card ID
     * @return
     */
    private String getCardID(JsonObject jsonCard) {
        try {
            return jsonCard
                    .get("cardID").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        }
    }
    public String getCardID() {
        return this.getCardID(getCard());
    }
    public String getCardID(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardID(jsonCard);
    }
    public String getCardID(int index) {
        return this.getCardID(getPlacedCard(index));
    }

    /**
     * Parses the current card backSymbol
     * @return
     */
    private String getCardBackSymbol(JsonObject jsonCard) {
        try {
            return jsonCard
                    .get("backSymbol").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        }
    }
    public String getCardBackSymbol() {
        return this.getCardBackSymbol(getCard());
    }
    public String getCardBackSymbol(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardBackSymbol(jsonCard);
    }
    public String getCardBackSymbol(int index) {
        return this.getCardBackSymbol(getPlacedCard(index));
    }

    /**
     * Parses the current card front corners
     * @return
     */
    private String[] getCardFrontCorners(JsonObject jsonCard) {
        String[] frontCorners=new String[4];
        int i=0;
        try{
            for(JsonElement corner: jsonCard
                    .get("frontCorners").getAsJsonArray())
            {
                frontCorners[i++]=corner.getAsJsonPrimitive().getAsString();
            }
        } catch(NullPointerException npe) {
            return frontCorners;
        }
        return frontCorners;
    }
    public String[] getCardFrontCorners() {
        return this.getCardFrontCorners(getCard());
    }
    public String[] getCardFrontCorners(String drawableArea,int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardFrontCorners(jsonCard);
    }
    public String[] getCardFrontCorners(int index) {
        return this.getCardFrontCorners(getPlacedCard(index));
    }
    /**
     * Parses the current card back corners
     * @return
     */
    private String[] getCardBackCorners(JsonObject jsonCard) {
        String[] backCorners=new String[4];
        int i=0;
        try{
            for(JsonElement corner: jsonCard
                    .get("frontCorners").getAsJsonArray())
            {
                backCorners[i++]=corner.getAsJsonPrimitive().getAsString();
            }
            return backCorners;
        } catch(NullPointerException npe) {
            return backCorners;
        }catch(IllegalStateException ise) {
            throw new MessageParserException("frontCorners is not a string");
        }
    }
    public String[] getCardBackCorners() {
        return this.getCardBackCorners(getCard());
    }
    public String[] getCardBackCorners(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardBackCorners(jsonCard);
    }
    public String[] getCardBackCorners(int index) {
        return this.getCardBackCorners(getPlacedCard(index));
    }

    private String getCardObjective(JsonObject jsonCard) {
        try{
            return jsonCard
                    .get("cardObjective").getAsJsonObject()
                    .get("type").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("cardObjective is not String");
        }
    }
    public String getCardObjective() {
        return this.getCardObjective(getCard());
    }
    public String getCardObjective(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardObjective(jsonCard);
    }
    public String getCardObjective(int index) {
        return this.getCardObjective(getPlacedCard(index));
    }
    private String getCardObjectiveSymbol(JsonObject jsonCard) {
        try {
            return jsonCard
                    .get("cardObjective").getAsJsonObject()
                    .get("data").getAsJsonObject()
                    .get("symbolOfInterest").getAsJsonPrimitive().getAsString();
        } catch(NullPointerException npe) {
            return null;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("objectiveSymbol is not string");
        }
    }
    public String getCardObjectiveSymbol() {
        return this.getCardObjectiveSymbol(getCard());
    }
    public String getCardObjectiveSymbol(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardObjectiveSymbol(jsonCard);
    }
    public String getCardObjectiveSymbol(int index) {
        return this.getCardObjectiveSymbol(getPlacedCard(index));
    }
    private Integer getCardObjectivePOINTS(JsonObject jsonCard) {
        try {
            return jsonCard
                    .get("cardObjective").getAsJsonObject()
                    .get("data").getAsJsonObject()
                    .get("points").getAsJsonPrimitive().getAsInt();
        } catch(NullPointerException npe) {
            throw new MessageParserException("points are null");
        } catch(IllegalStateException ise) {
            throw new MessageParserException("points are not an integer");
        }

    }
    public Integer getCardObjectivePOINTS() {
        return this.getCardObjectivePOINTS(getCard());
    }
    public Integer getCardObjectivePOINTS(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardObjectivePOINTS(jsonCard);
    }
    public Integer getCardObjectivePOINTS(int index) {
        return this.getCardObjectivePOINTS(getPlacedCard(index));
    }
    private ArrayList<String> getCardPrerequisites(JsonObject jsonCard) {
        ArrayList<String> prerequisites=new ArrayList<>();
        try{
            for(JsonElement symbol: jsonCard
                    .get("prerequisites").getAsJsonArray())
            {
                prerequisites.add(symbol.getAsJsonPrimitive().getAsString());
            }
            return prerequisites;
        } catch(NullPointerException npe){
            return new ArrayList<>();
        } catch(IllegalStateException ise) {
            throw new MessageParserException("prerequisites are not arrays of Strings");
        }
    }
    public ArrayList<String> getCardPrerequisites(String drawableArea, int index) {
        JsonObject jsonCard=getCard(drawableArea,index);
        return this.getCardPrerequisites(jsonCard);
    }
    public ArrayList<String> getCardPrerequisites() {
        return this.getCardPrerequisites(getCard());
    }
    public ArrayList<String> getCardPrerequisites(int index) {
        return this.getCardPrerequisites(getPlacedCard(index));
    }
    private JsonArray getDrawableAreaArray(String drawableArea) {
        try {

            try {
                JsonArray currentResourceDrawableArea = messageParams.get("resourceDrawableArea").getAsJsonArray();
                if (drawableArea.equals("resourceDrawableArea")) {
                    return currentResourceDrawableArea;
                }
            } catch (NullPointerException npe) {
                return null;
            } catch (IllegalStateException ise) {
                throw new MessageParserException("resourceDrawableArea is not an array");
            }
            try {
                JsonArray currentResourceDrawableArea = messageParams.get("goldDrawableArea").getAsJsonArray();
                if (drawableArea.equals("goldDrawableArea")) {
                    return currentResourceDrawableArea;
                }
            } catch (NullPointerException npe) {
                return null;
            }
        } catch (IllegalStateException ise) {
            throw new MessageParserException("goldDrawableArea is not an array");
        }
        throw new NoSuchElementException();
    }
    public Integer getPlacedCardsSize() {
        try {
            return getPlacedCards().size();
        } catch(NullPointerException npe) {
            return 0;
        }

    }
    private JsonObject getPlacedCard(int index) {
        try {
            return getPlacedCards()
                    .get(index).getAsJsonObject();
        }catch(NullPointerException npe) {
            return null;
        }catch(IllegalStateException ise) {
            throw new MessageParserException("PlacedCard is not a JsonObject");
        }
    }
    private JsonArray getPlacedCards() {
        try {
            return messageParams.get("placedCards").getAsJsonArray();
        } catch(NullPointerException npe) {
            return null;
        } catch(IllegalStateException ise) {
            throw new MessageParserException("placedCards is not an array");
        }
    }
    private CardDTO getCardDTO(JsonObject jsonCard) {
        return parser.fromJson(jsonCard,CardDTO.class);
    }
    private CardDTO getCardDTO() {
        return getCardDTO(getCard());
    }
    private CardDTO getCardDTO(String drawingArea, int index) {
        return getCardDTO(getCard(drawingArea,index));
    }
    
    public CardCLI getCardCLI() {
        CardDTO cardDTO=getCardDTO();
        ArrayList<String> prerequisites=new ArrayList<>();
        if (cardDTO.prerequisites()!=null) {
            prerequisites=cardDTO.prerequisites();
        }
        return new CardCLI(
                cardDTO.cardID(),
                cardDTO.frontCorners(),
                cardDTO.backCorners(),
                cardDTO.cardObjective().type(),
                cardDTO.cardObjective().data().symbolOfInterest(),
                cardDTO.cardObjective().data().points(),
                prerequisites
        );
    }
    public CardCLI getCardCLI(String drawingSection, int index) {
        /*return new CardCLI(
                getCardID(drawingSection,index),
                getCardFrontCorners(drawingSection,index),
                getCardBackCorners(drawingSection,index),
                getCardObjective(drawingSection,index),
                getCardObjectiveSymbol(drawingSection,index),
                getCardObjectivePOINTS(drawingSection,index),
                getCardPrerequisites(drawingSection,index)
        );*/
        CardDTO cardDTO=getCardDTO(drawingSection,index);
        ArrayList<String> prerequisites=new ArrayList<>();
        if (cardDTO.prerequisites()!=null) {
            prerequisites=cardDTO.prerequisites();
        }
        return new CardCLI(
                cardDTO.cardID(),
                cardDTO.frontCorners(),
                cardDTO.backCorners(),
                cardDTO.cardObjective().type(),
                cardDTO.cardObjective().data().symbolOfInterest(),
                cardDTO.cardObjective().data().points(),
                prerequisites
        );
    }
    public CardCLI getStartingCardCLI() {
        /*return new CardCLI(
                getCardID(),
                getCardFrontCorners(),
                getCardBackCorners(),
                getCardFrontCenterSymbols()
        );*/
        CardDTO cardDTO=getCardDTO();
        return new CardCLI(
                cardDTO.cardID(),
                cardDTO.frontCorners(),
                cardDTO.backCorners(),
                cardDTO.frontCenterSymbols()
        );
    }

}

