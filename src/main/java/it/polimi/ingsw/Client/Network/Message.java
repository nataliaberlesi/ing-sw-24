package it.polimi.ingsw.Client.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.Network.DTO.ParamsDTO;

public record Message(MessageType type, ParamsDTO params) {

}