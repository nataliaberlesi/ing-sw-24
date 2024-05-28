package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.*;
import it.polimi.ingsw.Server.Controller.DTO.InParamsDTO;
import it.polimi.ingsw.Server.Controller.GameInstance;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;

public class MessageParser {
    private final Gson parser;
    /**
     * The singleton instance
     */
    private static final MessageParser INSTANCE=new MessageParser();
    private MessageParser() {
        parser=new GsonBuilder().registerTypeAdapter(CardObjective.class, new InterfaceAdapter<CardObjective>())
                .registerTypeAdapter(Objective.class,new InterfaceAdapter<Objective>())
                .registerTypeAdapter(Card.class,new InterfaceAdapter<Card>())
                .enableComplexMapKeySerialization()
                .create();
    }

    /**
     * Used to get the singleton instance of MessageParser
     * @return
     */
    public static MessageParser getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Used to deserialize a message from a json string
     * @param message
     * @return
     */
    public Message parseMessage(String message) {
        return parser.fromJson(message,Message.class);
    }

    /**
     * Used to get a json serialization of a object
     * @param object the object to serialize
     * @return a json string
     */
    public String toJson(Object object) {
        return parser.toJson(object);
    }

    /**
     * Used to deserialize a GameInstance in json format
     * @param state
     * @return
     */
    public GameInstance parseGameInstance(String state) {
        return parser.fromJson(state, GameInstance.class);
    }
}
