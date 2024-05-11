package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        GSON=new Gson();
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
    public JsonElement toJsonElement(Object o) {
        return GSON.toJsonTree(o);
    }

}
