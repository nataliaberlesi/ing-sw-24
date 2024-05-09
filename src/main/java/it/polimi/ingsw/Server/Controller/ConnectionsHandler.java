package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.*;

import java.util.ArrayList;

public class ConnectionsHandler implements Runnable{
    public GameController gameController;
    public Parser parser;
    public Server server;
    public Boolean allPlayerConnected;
    public ConnectionsHandler(Server server) {
        this.server=server;
        parser=Parser.getInstance();
    }
    private Message handleMessage(String inMessage) {
        return gameController.dispatchMessage(inMessage);
    }
    private void handleConnections(ArrayList<PlayerConnection> playerConnections) {
        for(PlayerConnection playerConnection: playerConnections) {
            Message outMessage=handleMessage(playerConnection.getInMessage());
            playerConnection.setOutMessage(parser.toString(outMessage));
        }
    }
    public void run() {
        while(allPlayerConnected) {
            if(gameController.gameIsFull() && !gameController.gameIsStarted()) {
                for(PlayerConnection pc: server.getConnections()) {
                    String outMessage=gameController.craftJSONMessage(MessageType.START, gameController.getJSONStartParams());
                    pc.setOutMessage(outMessage);
                }
            }
            handleConnections(server.getConnections());
        }
    }

}
