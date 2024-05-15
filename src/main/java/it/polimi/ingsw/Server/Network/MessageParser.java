package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
import it.polimi.ingsw.Server.Controller.DTO.PlaceCard;
import it.polimi.ingsw.Server.Model.Coordinates;

public class MessageParser {
    private final Parser parser;
    public MessageParser() {
        parser=Parser.getInstance();
    }
    public CreateGame parseCreateGame(String username, JsonObject jsonParams) {
        int playersNumber=jsonParams.get("numberOfPlayers").getAsJsonPrimitive().getAsInt();
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
    public FirstRound parseFirstRound(JsonObject jsonParams) {
        //TODO
        boolean flipStartingCard=jsonParams.get("flipStartingCard").getAsJsonPrimitive().getAsBoolean();
        String color=jsonParams.get("color").getAsJsonPrimitive().getAsString();
        return new FirstRound(flipStartingCard,color);
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
    public JsonObject getMessageParams(String message) {
        JsonObject jsonMessage=parser.toJsonObject(message);
        return jsonMessage.get("params").getAsJsonObject();
    }
    public String getUsername(String message) {
        JsonObject jsonMessage=parser.toJsonObject(message);
        return jsonMessage.get("username").getAsJsonPrimitive().getAsString();
    }

}
