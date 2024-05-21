package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Server.Controller.GameController;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionsHandler implements Runnable{
    public GameController gameController;
    public MessageParser messageParser;
    public Server server;
    public Boolean allPlayerConnected;
    public ConnectionsHandler(Server server) {
        this.server=server;
        messageParser=MessageParser.getINSTANCE();
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
            JsonObject jsonParams=gameController.getJSONStartFirstRoundParams();
            for(PlayerConnection pc: server.getConnections()) {
                String outMessage=messageParser.toJson(new Message(MessageType.START_FIRSTROUND,jsonParams));
                pc.setOutMessage(outMessage);
            }
        }
    }
    private void checkStartSecondRound() {
        if(gameController.allBoardsAreSet() && !gameController.secondRoundIsStarted()) {
            JsonObject jsonParams=gameController.getJSONStartSecondRoundParams();
            gameController.startSecondRound();
            for(PlayerConnection pc:server.getConnections()) {
                String outMessage=messageParser.toJson(new Message(MessageType.START_SECONDROUND,jsonParams));
                pc.setOutMessage(outMessage);
            }
        }
    }
    private void checkStartGame() {
        if(gameController.allObjectivesHaveBeenChosen() && !gameController.gameIsStarted()) {
            Message outMessage=MessageCrafter.craftDrawCardMessage(
                    gameController.getGameInstance().getTurn(),
                    gameController.getGameInstance().getTurn(),
                    gameController.getGameInstance().getHand(gameController.getGameInstance().getTurn()),
                    gameController.getGameInstance().getResourceDrawableArea(),
                    gameController.getGameInstance().getGoldDrawableArea());
            gameController.startGame();
            for(PlayerConnection pc: server.getConnections()) {
                String outMessageString=messageParser.toJson(outMessage);
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
    private void checkAllPlayerConnected() throws IOException {
        synchronized (server.getConnections()) {
            for(PlayerConnection pc: server.getConnections()) {
                if(pc.socketIsClosed()) {
                    allPlayerConnected=false;
                }
            }
        }
    }
    private void closeConnections() {
        synchronized (server.getConnections()) {
            for(PlayerConnection pc: server.getConnections()) {
                if(!pc.socketIsClosed()) {
                    pc.setOutMessage(messageParser.toJson(MessageCrafter.craftAbortMessage("A player has exit")));
                }
            }
            /*for(PlayerConnection pc:server.getConnections()) {
                try {
                    pc.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }*/
            server.restart();
        }
    }
    /**
     * Handles some network game logic operations, then handles incoming messages from each connection
     */
    public void run() {
        while(allPlayerConnected) {
            if (gameController.getGameInstance()!=null) {
                checkStartFirstRound();
                checkStartSecondRound();
                checkStartGame();
                checkEndgame();
            }
            handleConnections(server.getConnections());
            try {
                checkAllPlayerConnected();
            } catch(IOException ioe) {
            }
        }
        closeConnections();
    }

}
