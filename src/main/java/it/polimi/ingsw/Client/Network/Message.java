package it.polimi.ingsw.Client.Network;

public record Message(MessageType type, String nickname, String params) {

}