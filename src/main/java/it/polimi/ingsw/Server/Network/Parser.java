package it.polimi.ingsw.Server.Network;

import com.google.gson.*;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;

public class Parser {
    /**
     * the gson object used to parse
     */
    private final Gson GSON;
    /**
     * the singleton instance
     */
    private static Parser instance;
    private Parser() {
        GSON = new GsonBuilder().registerTypeAdapter(CardObjective.class, new InterfaceAdapter<CardObjective>())
                .registerTypeAdapter(Objective.class,new InterfaceAdapter<Objective>())
                .create();
    }
    public static Parser getInstance() {
        if(instance==null) {
            instance=new Parser();
        }
        return instance;
    }

    /**
     * parses a string into a message
     * @param string the string to be parsed
     * @return the message
     */
    public Message toMessage(String string) {
        return GSON.fromJson(string, Message.class);
    }
    public JsonObject toJsonObject(String message) {

        JsonObject jsonObject=GSON.fromJson(message,JsonObject.class);
        return jsonObject;
    }
    public String toJson(Object o) {
        return GSON.toJson(o);
    }
    public JsonElement toJsonTree(Object o) {
        return GSON.toJsonTree(o);
    }
}
