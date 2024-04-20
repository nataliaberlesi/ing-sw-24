package it.polimi.ingsw.Server.Network;



public class Dispatcher {
    private PlayerConnection playerConnection;
    public Dispatcher(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
    }
    public void setPlayerConnection(PlayerConnection playerConnection) {
        this.playerConnection=playerConnection;
    }

}
