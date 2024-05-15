package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Model.Color;

import java.util.ArrayList;

public class MessageCrafter {
    private static final Parser parser=Parser.getInstance();
    public static Message craftErrorMessage(MessageType messageType, String currentPlayer) {
        JsonObject params=new JsonObject();
        params.addProperty("currentPlayer",currentPlayer);
        return new Message(messageType,params);
    }
    public static Message craftConnectMessage(boolean masterStatus) {
        MessageType messageType=MessageType.CONNECT;
        JsonObject params=new JsonObject();
        params.addProperty("masterStatus",masterStatus);
        return new Message(messageType,params);
    }
    public static Message craftAbortMessage(String cause) {
        MessageType messageType=MessageType.ABORT;
        JsonObject params= new JsonObject();
        params.addProperty("cause",cause);
        return new Message(messageType,params);
    }
    public static Message craftCreateMessage(String username) {
        MessageType messageType=MessageType.CREATE;
        JsonObject params= new JsonObject();
        params.addProperty("currentPlayer",username);
        return new Message(messageType,params);
    }
    public static Message craftJoinMessage(String username,Boolean unavailableUsername) {
        MessageType messageType=MessageType.JOIN;
        JsonObject params=new JsonObject();
        params.addProperty("currentPlayer",username);
        params.addProperty("unavailableUsername",unavailableUsername);
        return new Message(messageType,params);
    }

    public static Message craftFirstRoundMessage(String card, String currentPlayer, ArrayList<Color> availableColors, String affectedPlayer, boolean flip, String color) {
        MessageType messageType=MessageType.FIRSTROUND;
        JsonObject params=new JsonObject();
        if(card!=null) {
            params.add("card",parser.toJsonTree(StartingCardFactory.makeStartingCard(card)));
        }
        params.addProperty("currentPlayer", currentPlayer);
        JsonArray availableColorsArray= new JsonArray();
        for(Color availableColor: availableColors) {
            availableColorsArray.add(availableColor.toString());
        }
        params.add("availableColors", availableColorsArray);
        params.addProperty("affectedPlayer",affectedPlayer);
        params.addProperty("flip",flip);
        params.addProperty("color",color);
        return new Message(messageType,params);
    }
}
