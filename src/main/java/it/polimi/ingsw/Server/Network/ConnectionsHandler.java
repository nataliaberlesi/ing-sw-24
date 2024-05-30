package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Server.Controller.GameController;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionsHandler implements Runnable{
    private GameController gameController;
    private Server server;
    public ConnectionsHandler(Server server){
        this.server=server;
        this.gameController=new GameController(server);
    }

    /**
     * Dispatches the message to the GameController
     * @param inMessage
     * @return the GameController's answer
     */
    private Message handleMessage(Message inMessage) {
        return gameController.dispatchMessage(inMessage);
    }

    /**
     * Read every message in input from each playerConnection and handles it. Then answers back to the Client
     * @param playerConnections
     */
    private void handleConnections(ArrayList<PlayerConnection> playerConnections) {
        synchronized (playerConnections) {
            for(PlayerConnection playerConnection: playerConnections) {
                Message inMessage = playerConnection.getInMessage(false);
                if(inMessage!=null) {
                    if (!inMessage.type().equals(MessageType.POKE)){
                        System.out.println("IN | " + inMessage);
                    }
                    Message outMessage=handleMessage(inMessage);
                    if(!outMessage.type().equals(MessageType.POKE)){
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

    }
    /**
     * If start first round conditions are met, sends in broadcast START_FIRSTROUND
     */
    private void checkStartFirstRound() {
        if(gameController.gameIsFull() && !gameController.firstRoundIsStarted()) {
            server.closeLobby();
            gameController.startFirstRound();
            Message outMessage =gameController.doStartFirstRound();
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
            Message outMessage=gameController.doStartSecondRound();
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
    private void checkEndgame() throws IOException {
        if(gameController.gameIsEnded()){
            Message outmessage=MessageCrafter.craftEndgameMessage(gameController.getScoreBoard());
            for(PlayerConnection pc: server.getConnections()) {
                pc.setOutMessage(outmessage);
                pc.close();
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
                synchronized (pc) {
                    if(!pc.socketIsClosed()) {
                        pc.setOutMessage(MessageCrafter.craftAbortMessage(cause));
                    }
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
        gameController=new GameController(server);
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
                try {
                    checkEndgame();
                } catch (IOException e) {
                    System.out.println("WARNING: Can't close a player connection");
                }
            }
            try{
                handleConnections(server.getConnections());
            } catch(RuntimeException re) {
                System.out.println("ERROR: "+re.getMessage());
                safeCloseConnections(re.getMessage());
            }
            checkAllPlayerConnected();
        }
    }
}
