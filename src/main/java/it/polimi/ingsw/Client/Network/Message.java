package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;

public record Message(MessageType type, String username, JsonObject params) {

}