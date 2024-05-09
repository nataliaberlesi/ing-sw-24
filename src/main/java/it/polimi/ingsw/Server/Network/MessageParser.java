package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.PlaceCard;
import it.polimi.ingsw.Server.Controller.GameController;
import it.polimi.ingsw.Server.Model.Coordinates;

import java.io.IOException;
import java.util.ArrayList;

public class MessageParser {
    private Parser parser;
    public MessageParser() {
        parser=Parser.getInstance();
    }
    public CreateGame parseCreateGame(String params) {
        JsonObject jsonObject=parser.toJsonObject(params);
        int playersNumber=jsonObject.get("numberOfPlayers").getAsInt();
        String username=jsonObject.get("username").getAsString();
        return new CreateGame(playersNumber,username);
    }
    public PlaceCard parsePlaceCard(String params) {
        JsonObject jsonObject=parser.toJsonObject(params);
        String card=jsonObject.get("card").getAsString();
        JsonObject jsonCoordinates=jsonObject.get("coordinates").getAsJsonObject();
        int x=jsonCoordinates.get("x").getAsInt();
        int y=jsonCoordinates.get("y").getAsInt();
        Coordinates coordinates=new Coordinates(x,y);
        PlaceCard placeCard=new PlaceCard(card,coordinates);
        return placeCard;
    }
    public String parseDrawCard(String params) {
        JsonObject jsonObject=parser.toJsonObject(params);
        String card=jsonObject.get("card").getAsString();
        return card;
    }
    public Parser getParser() {
        return parser;
    }
    public MessageType getMessageType(String message) {
        JsonObject jsonMessage=parser.toJsonObject(message);
        return MessageType.valueOf(jsonMessage.get("type").getAsString());
    }
    public String getMessageParams(String message) {
        JsonObject jsonMessage=parser.toJsonObject(message);
        return jsonMessage.get("params").getAsString();
    }
    public String getUsername(String message) {
        JsonObject jsonMessage=parser.toJsonObject(message);
        return jsonMessage.get("username").getAsString();
    }

}
