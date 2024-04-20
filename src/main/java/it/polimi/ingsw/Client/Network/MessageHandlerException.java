package it.polimi.ingsw.Client.Network;

public class MessageHandlerException extends RuntimeException {
    public MessageHandlerException(String string) {
        super(string);
    }

    public MessageHandlerException(String string, Exception cause) {
        super(string, cause);
    }
}
