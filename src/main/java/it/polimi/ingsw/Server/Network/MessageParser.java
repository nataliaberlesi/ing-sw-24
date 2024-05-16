package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.*;
import it.polimi.ingsw.Server.Controller.DTO.InParamsDTO;
import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;

public class MessageParser {
    private final Gson parser;
    private static final MessageParser INSTANCE=new MessageParser();
    private MessageParser() {
        parser=new GsonBuilder().registerTypeAdapter(CardObjective.class, new InterfaceAdapter<CardObjective>())
                .registerTypeAdapter(Objective.class,new InterfaceAdapter<Objective>())
                .create();
    }
    public static MessageParser getINSTANCE() {
        return INSTANCE;
    }
    public InParamsDTO parseInParamsDTO(JsonObject jsonParams) {
        return parser.fromJson(jsonParams, InParamsDTO.class);
    }
    public Message parseMessage(String message) {
        return parser.fromJson(message,Message.class);
    }
    public JsonObject toJsonObject(Object object) {
        return parser.toJsonTree(object).getAsJsonObject();
    }

    public String toJson(Object object) {
        return parser.toJson(object);
    }
}
