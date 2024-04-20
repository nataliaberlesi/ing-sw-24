package it.polimi.ingsw.Server.Network;

public class Receiver {
    private PlayerConnection playerConnection;
    public Receiver(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
    }
    public void setPlayerConnection(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
    }

}
