package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Server.Controller.GameController;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionsHandler implements Runnable{
    private GameController gameController;
    private MessageParser messageParser;
    private Server server;
    public ConnectionsHandler(Server server) throws IOException {
        this.server=server;
        messageParser=MessageParser.getINSTANCE();
        this.gameController=new GameController(server);
        ServerInputHandler serverInputHandler=new ServerInputHandler(server);
        new Thread(serverInputHandler).start();
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
     * Read every message in input from each playerConnection and handles it. Then answers back to the Client
     * @param playerConnections
     */
    private void handleConnections(ArrayList<PlayerConnection> playerConnections) {
        synchronized (playerConnections) {
            for(PlayerConnection playerConnection: playerConnections) {
                String inMessage = playerConnection.getInMessage(false);
                if(inMessage!=null) {
                    System.out.println("IN | "+inMessage);
                    Message outMessage=handleMessage(inMessage);
                    if(!(outMessage.type().equals(MessageType.JOIN) || outMessage.type().equals(MessageType.CREATE))) {
                        for(PlayerConnection playerConnection1: playerConnections) {
                            playerConnection1.setOutMessage(outMessage);
                    }
                } else {
                        playerConnection.setOutMessage(outMessage);
                    }
            }
        }
        }

    }
    /**
     * If start first round conditions are met, sends in broadcast START_FIRSTROUND
     */
    private void checkStartFirstRound() {
        if(gameController.gameIsFull() && !gameController.firstRoundIsStarted()) {
            server.closeLobby();
            gameController.startFirstRound();
            Message outMessage =gameController.getJSONStartFirstRoundParams();
            for(PlayerConnection pc: server.getConnections()) {
                pc.setOutMessage(outMessage);
            }
        }
    }

    /**
     * If start second round conditions are met, sends in broadcast START_SECONDROUND
     */
    private void checkStartSecondRound() {
        if(gameController.allBoardsAreSet() && !gameController.secondRoundIsStarted()) {
            Message outMessage=gameController.getJSONStartSecondRoundParams();
            gameController.startSecondRound();
            for(PlayerConnection pc:server.getConnections()) {
                pc.setOutMessage(outMessage);
            }
        }
    }

    /**
     * If start game conditions are met, sends in broadcast the first ACTION_DRAWCARD message
     */
    private void checkStartGame() {
        if(gameController.allObjectivesHaveBeenChosen() && !gameController.gameIsStarted()) {
            Message outMessage=gameController.startGame();
            for(PlayerConnection pc: server.getConnections()) {
                while(pc.getOutMessage()!=null){}
                pc.setOutMessage(outMessage);
            }
        }
    }

    /**
     * If endgame conditions are met, send a ENDGAME broadcast message
     */
    private void checkEndgame(){
        if(gameController.gameIsEnded()){
            Message outmessage=MessageCrafter.craftEndgameMessage(gameController.getScoreBoard());
            for(PlayerConnection pc: server.getConnections()) {
                pc.setOutMessage(outmessage);
                try {
                    pc.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Closes connections safely if a player lefts
     */
    private void checkAllPlayerConnected(){
        boolean allPlayerConnected=true;
        synchronized (server.getConnections()) {
            for(PlayerConnection pc: server.getConnections()) {
                if(pc.socketIsClosed()) {
                    allPlayerConnected=false;
                }
            }
        }
        if(!allPlayerConnected) {
            safeCloseConnections("A player has left");
        }
    }

    /**
     * Sends a broadcast ABORT message
     * @param cause
     */
    private void closeConnections(String cause) {
        synchronized (server.getConnections()) {
            for(PlayerConnection pc: server.getConnections()) {
                if(!pc.socketIsClosed()) {
                    pc.setOutMessage(MessageCrafter.craftAbortMessage(cause));
                }
            }
        }
    }

    /**
     * Closes connections and restarts server
     * @param cause
     */
    private void safeCloseConnections(String cause) {
        closeConnections(cause);
        try {
            gameController=new GameController(server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.restart();
    }

    /**
     * Sends a CONTINUE broadcast message when the game is in persistence mode and the lobby is full
     */
    private void checkContinueGame() {
        if(gameController.persistency() && gameController.persistencyGameIsFull()) {
            Message outMessage;
            for(int i=0;i<server.getConnections().size();i++) {
                outMessage=gameController.continueGame();
                for(PlayerConnection pc: server.getConnections()) {
                    pc.setOutMessage(outMessage);
                }
            }
            gameController.unpauseGame();
            gameController.turnOffPersistency();
        }
    }
    /**
     * Handles some network game logic operations, then handles incoming messages from each connection
     */
    public void run() {
        while(!server.isClosed()) {
            if (gameController.gameInstanceExists()) {
                checkContinueGame();
                checkStartFirstRound();
                checkStartSecondRound();
                checkStartGame();
                checkEndgame();
            }
            handleConnections(server.getConnections());
            checkAllPlayerConnected();
        }
    }
}
