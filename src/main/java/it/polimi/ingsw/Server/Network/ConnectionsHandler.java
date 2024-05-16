package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.GameController;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionsHandler implements Runnable{
    public GameController gameController;
    public Parser parser;
    public Server server;
    public Boolean allPlayerConnected;
    public ConnectionsHandler(Server server) {
        this.server=server;
        parser=Parser.getInstance();
        this.gameController=new GameController(server);
        allPlayerConnected=true;
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
        synchronized (playerConnections) {
            for(PlayerConnection playerConnection: playerConnections) {
                String inMessage=null;
                try {
                    inMessage = playerConnection.getInMessage(false);
                } catch (IOException e) {
                    throw new RuntimeException(e); //TODO
                }
                if(inMessage!=null) {
                    System.out.println("IN | "+inMessage);
                    Message outMessage=handleMessage(inMessage);
                    playerConnection.setOutMessage(parser.toJson(outMessage));
                    if(outMessage.type().equals(MessageType.ABORT)) {
                        try{
                            playerConnection.close();
                        } catch (IOException e) {
                            //TODO
                        }
                    }
                }
            }
        }

    }

    /**
     * Handles some network logic operations, then handles incoming messages
     */
    public void run() {
        while(allPlayerConnected) {
            if (gameController.getGameInstance()!=null) {
                if(gameController.gameIsFull() && !gameController.firstRoundIsStarted()) {
                    gameController.startFirstRound();
                    JsonObject jsonParams=gameController.getJSONStartFirstRoundParams();
                    for(PlayerConnection pc: server.getConnections()) {
                        String outMessage=parser.toJson(new Message(MessageType.START_FIRSTROUND,jsonParams));
                        pc.setOutMessage(outMessage);
                    }
                }
                if(gameController.allBoardsAreSet() && !gameController.secondRoundIsStarted()) {
                    JsonObject jsonParams=gameController.getJSONStartSecondRoundParams();
                    gameController.startSecondRound();
                    for(PlayerConnection pc:server.getConnections()) {
                        String outMessage=parser.toJson(new Message(MessageType.START_SECONDROUND,jsonParams));
                    }
                }
            }
            handleConnections(server.getConnections());
        }
    }

}
