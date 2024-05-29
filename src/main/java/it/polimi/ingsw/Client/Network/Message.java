package it.polimi.ingsw.Client.Network;


import it.polimi.ingsw.Client.Network.DTO.ParamsDTO;

/**
 * represents the generic message sent in the network from the client
 * @param type the message type
 * @param params the message params
 */
public record Message(MessageType type, ParamsDTO params) {

}