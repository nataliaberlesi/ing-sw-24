package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Controller.GameController;

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

    /**
     * Dispatches the message to the GameController
     * @param inMessage
     * @return the GameController's answer
     */
    private Message handleMessage(String inMessage) {
        return gameController.dispatchMessage(inMessage);
    }

    /**
     * Read every message in input from each playerConnection and then handles it. Then answers back to the Client
     * @param playerConnections
     */
    private void handleConnections(ArrayList<PlayerConnection> playerConnections) {
        for(PlayerConnection playerConnection: playerConnections) {
            Message outMessage=handleMessage(playerConnection.getInMessage());
            playerConnection.setOutMessage(parser.toJson(outMessage));
        }
    }

    /**
     * Handles some network logic operations, then handles incoming messages
     */
    public void run() {
        while(allPlayerConnected) {
            if(gameController.gameIsFull() && !gameController.gameIsStarted()) {
                for(PlayerConnection pc: server.getConnections()) {
                    String outMessage=parser.toJson(gameController.craftJSONMessage(MessageType.START_FIRSTROUND, gameController.getJSONStartFirstRoundParams()));
                    pc.setOutMessage(outMessage);
                }
            }
            handleConnections(server.getConnections());
        }
    }

}
