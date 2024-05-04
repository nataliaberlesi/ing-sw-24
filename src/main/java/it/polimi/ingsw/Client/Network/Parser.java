package it.polimi.ingsw.Client.Network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.Client.Network.Message;

public class Parser {
    /**
     * the gson object used to parse
     */
    private static final Gson GSON=new Gson();
    /**
     * the singleton instance
     */
    private static Parser instance=null;
    private Parser() {
    }
    public static Parser getInstance() {
        if(instance==null) {
            instance=new Parser();
            return instance;
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
    /**
     * parses a message into a string
     * @param message the message to be parsed
     * @return the string
     */
    public String toString(Object message) {
        return GSON.toJson(message);
    }
    public JsonObject toJsonObject(String message) {
        JsonElement rootNode=JsonParser.parseString(message);
        return rootNode.getAsJsonObject();
    }

}
