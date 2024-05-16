package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.CreateGame;
import it.polimi.ingsw.Server.Controller.DTO.FirstRound;
import it.polimi.ingsw.Server.Controller.DTO.JoinGame;
import it.polimi.ingsw.Server.Controller.DTO.PlaceCard;
import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Coordinates;

public class MessageParser {
    private final Gson parser;
    public MessageParser() {
        parser=new GsonBuilder().registerTypeAdapter(CardObjective.class, new InterfaceAdapter<CardObjective>())
                .registerTypeAdapter(Objective.class,new InterfaceAdapter<Objective>())
                .create();
    }
    public CreateGame parseCreateGame(JsonObject jsonParams) {
        return parser.fromJson(jsonParams, CreateGame.class);
    }
    public PlaceCard parsePlaceCard(String params) {
        return parser.fromJson(params,PlaceCard.class);
    }
    public FirstRound parseFirstRound(JsonObject jsonParams) {
        return parser.fromJson(jsonParams,FirstRound.class);
    }
    public String parseDrawCard(String params) {
        return null;
    }
    public Message parseMessage(String message) {
        return parser.fromJson(message,Message.class);
    }
    public JoinGame parseJoinGame(JsonObject jsonParams) {
        return parser.fromJson(jsonParams, JoinGame.class);
    }
}
