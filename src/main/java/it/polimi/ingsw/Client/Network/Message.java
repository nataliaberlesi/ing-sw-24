package it.polimi.ingsw.Client.Network;


import it.polimi.ingsw.Client.Network.DTO.ParamsDTO;

public record Message(MessageType type, ParamsDTO params) {

}