package it.polimi.ingsw.Server.Controller;


import it.polimi.ingsw.Server.Model.Player;
import it.polimi.ingsw.Server.Network.Gateway;
import it.polimi.ingsw.Server.Network.PlayerConnection;
import it.polimi.ingsw.Server.Network.Server;

import java.io.IOException;

public class GameController {

    private Server server;
    private int playerNumber;
    public void createGame() throws IOException {
        PlayerConnection MasterConnection= server.getConnections().getFirst();
        MasterConnection.getGateway().masterStatus(MasterConnection.isMaster());
        MasterConnection.getGateway().createGame();
        this.playerNumber=(Integer)MasterConnection.getGateway().getLastParams().get(0);
        String nickname=(String)MasterConnection.getGateway().getLastParams().get(1);
        MasterConnection.setPlayer(new Player(nickname));
    }
    public void startGame() throws IOException {
        for(int i=1; i<playerNumber;i++) {
            server.waitPlayer();
            PlayerConnection currenConnection=server.getConnections().getLast();
            currenConnection.getGateway().masterStatus(currenConnection.isMaster());
            String nickname=currenConnection.getGateway().startGame();
            currenConnection.setPlayer(new Player(nickname));
        }
    }
    public void setServer(Server server) {
        this.server = server;
    }
    public void shutDown() {

    }
}
