package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Network.Dispatcher;
import it.polimi.ingsw.Server.Network.PlayerConnection;
import it.polimi.ingsw.Server.Network.Receiver;

public class Gateway {
    private final Dispatcher dispatcher;
    private final Receiver receiver;

    public Gateway(PlayerConnection playerConnection) {
        this.dispatcher=new Dispatcher(playerConnection);
        this.receiver=new Receiver(playerConnection);
    }
}
