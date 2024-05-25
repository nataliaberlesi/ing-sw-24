package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.DTO.ParamsDTO;

public record Message(MessageType type, ParamsDTO params) {
    public MessageType type() {
        return type;
    }

    @Override
    public ParamsDTO params() {
        return params;
    }
}
