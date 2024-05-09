package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {
    /**
     * the gson object used to parse
     */
    private static final Gson GSON=new Gson();
    /**
     * the singleton instance
     */
    private static final Parser INSTANCE=new Parser();
    private Parser() {
    }
    public static Parser getInstance() {
        return INSTANCE;
    }

    /**
     * parses a string into a message
     * @param string the string to be parsed
     * @return the message
     */
    public Message toMessage(String string) {
        return GSON.fromJson(string, Message.class);
    }
    /**
     * parses a message into a string
     * @param message the message to be parsed
     * @return the string
     */
    public String toString(Message message) {
        return GSON.toJson(message);
    }
    public JsonObject toJsonObject(String message) {
        JsonElement rootNode= JsonParser.parseString(message);
        return rootNode.getAsJsonObject();
    }

}
