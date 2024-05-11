package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;

public record Message(MessageType type, JsonObject params) {
    public MessageType type() {
        return type;
    }

    @Override
    public JsonObject params() {
        return params;
    }
}
