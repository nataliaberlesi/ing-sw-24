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
        ServerInputHandler serverInputHandler=new ServerInputHandler(server, gameController);
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
                    if(!(outMessage.type().equals(MessageType.JOIN) || outMessage.type().equals(MessageType.CREATE))) {
                        for(PlayerConnection playerConnection1: playerConnections) {
                            playerConnection1.setOutMessage(messageParser.toJson(outMessage));
                    }
                } else {
                        playerConnection.setOutMessage(messageParser.toJson(outMessage));
                    }
            }
        }
        }

    }
    private void checkStartFirstRound() {
        if(gameController.gameIsFull() && !gameController.firstRoundIsStarted()) {
            server.closeLobby();
            gameController.startFirstRound();
            Message outMessage =gameController.getJSONStartFirstRoundParams();
            for(PlayerConnection pc: server.getConnections()) {
                String outMessageJSON=messageParser.toJson(outMessage);
                pc.setOutMessage(outMessageJSON);
            }
        }
    }
    private void checkStartSecondRound() {
        if(gameController.allBoardsAreSet() && !gameController.secondRoundIsStarted()) {
            Message outMessage=gameController.getJSONStartSecondRoundParams();
            gameController.startSecondRound();
            for(PlayerConnection pc:server.getConnections()) {
                String outMessageJSON=messageParser.toJson(outMessage);
                pc.setOutMessage(outMessageJSON);
            }
        }
    }
    private void checkStartGame() {
        if(gameController.allObjectivesHaveBeenChosen() && !gameController.gameIsStarted()) {
            Message outMessage=gameController.startGame();
            for(PlayerConnection pc: server.getConnections()) {
                String outMessageString=messageParser.toJson(outMessage);
                while(pc.getOutMessage()!=null){}
                pc.setOutMessage(outMessageString);
            }
        }
    }
    private void checkEndgame(){
        if(gameController.gameIsEnded()){
            Message outmessage=MessageCrafter.craftWinnersMessage(gameController.getScoreBoard());
            for(PlayerConnection pc: server.getConnections()) {
                String outMessageString=messageParser.toJson(outmessage);
                pc.setOutMessage(outMessageString);
                try {
                    pc.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
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
    private void closeConnections(String cause) {
        synchronized (server.getConnections()) {
            for(PlayerConnection pc: server.getConnections()) {
                if(!pc.socketIsClosed()) {
                    pc.setOutMessage(messageParser.toJson(MessageCrafter.craftAbortMessage(cause)));
                }
            }
        }
    }
    private void safeCloseConnections(String cause) {
        closeConnections(cause);
        try {
            gameController=new GameController(server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.restart();
    }
    private void checkContinueGame() {
        if(gameController.persistency() && gameController.persistencyGameIsFull()) {
            Message outMessage;
            for(int i=0;i<server.getConnections().size();i++) {
                outMessage=gameController.continueGame();
                for(PlayerConnection pc: server.getConnections()) {
                    String outMessageString=messageParser.toJson(outMessage);
                    pc.setOutMessage(outMessageString);
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
            if (gameController.getGameInstance()!=null) {
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
