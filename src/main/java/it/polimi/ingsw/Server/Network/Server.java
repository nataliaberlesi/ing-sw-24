package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Controller.PersistencyHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    /**
     * The port where the server listens
     */
    private final int port;
    /**
     * The server socket that listens for connections
     */
    private ServerSocket serverSocket;
    private int maxAllowablePlayers;

    /**
     * The active connections inside the server
     */
    private ArrayList<PlayerConnection> connections= new ArrayList<>();
    private boolean masterIsConnected;
    private boolean lobbyIsOpen;
    private boolean aGameAlreadyExist;
    private boolean chooseContinueGame;
    /**
     * Creates a server using a custom port, then waits for a master player to connect
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        maxAllowablePlayers=0;
        masterIsConnected=false;
        lobbyIsOpen=false;
        chooseContinueGame=false;
        aGameAlreadyExist= PersistencyHandler.gameAlreadyExists();
        this.serverSocket=new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
    }
    public ArrayList<PlayerConnection> getConnections() {
        return connections;
    }

    public void closeLobby() {lobbyIsOpen=false;}

    /**
     * Opens lobby and sets max allowable players
     * @param maxAllowablePlayers
     */
    public void openLobby(Integer maxAllowablePlayers) {
        this.maxAllowablePlayers=maxAllowablePlayers;
        lobbyIsOpen=true;
    }

    /**
     * Restarts server with default configuration
     */
    public void restart() {
        masterIsConnected=false;
        maxAllowablePlayers=0;
        connections.clear();
        System.out.println("SERVER RESTARTED");
    }
    /**
     * Waits for a player connection
     * @throws IOException
     */
    public void waitPlayer() throws IOException {
        PlayerConnection player=connectPlayer();
        synchronized (connections){
            this.connections.add(player);
        }
    }

    /**
     * Connects the master to the server
     * @return The master connection
     * @throws IOException
     */
    private PlayerConnection connectPlayer() throws IOException {
        Socket Socket=serverSocket.accept();
        //TODO: thread
        PlayerConnection playerConnection=new PlayerConnection(Socket);
        new Thread(playerConnection).start();
        return playerConnection;
    }
    /**
     *Sends CONNECT message
     * @param masterStatus
     */
    private void sendMasterStatus(Boolean masterStatus) {
        Message message=MessageCrafter.craftConnectMessage(masterStatus);
        connections.getLast().setOutMessage(message);
    }

    /**
     * Sends PERSISTENCE message
     */
    private void sendPersistencyNotification() {
        Message message=MessageCrafter.craftPersistencyNotification();
        connections.getLast().setOutMessage(message);
    }
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    /**
     * Sends in broadcast an ABORT message and closes serversocket and all the connections.
     * @throws IOException
     */
    public void close() throws IOException {
        for(PlayerConnection playerConnection: connections) {
            playerConnection.setOutMessage(MessageCrafter.craftAbortMessage("Server has been closed"));
            playerConnection.close();
        }
        serverSocket.close();
    }

    /**
     * Waits for a game master to join if it is not connected
     */
    private void checkMasterConnection() {
        if(!masterIsConnected) {
            try {
                waitPlayer();
                masterIsConnected=true;
                chooseContinueGame=true;
                PersistencyHandler.startPersistence();
            } catch (IOException e) {
                //socket timed out
            }
        }
    }

    /**
     * Sends a PERSISTENCE message if a game already exist
     */
    private void checkContinueGame() {
        if(chooseContinueGame) {
            if(!aGameAlreadyExist) {
                sendMasterStatus(true);
                chooseContinueGame=false;
            }
            if(aGameAlreadyExist) {
                sendPersistencyNotification();
                chooseContinueGame=false;
            }
        }
    }

    /**
     * Accepts a player in the connections' lobby if it is open
     */
    private void checkLobby() {
        if(connections.size()<maxAllowablePlayers && lobbyIsOpen) {
            try {
                waitPlayer();
                sendMasterStatus(false);
            } catch (IOException ioe) {
                //waiting for player
            }
        }
    }
    public void run() {
        while(!serverSocket.isClosed()) {
            checkMasterConnection();
            checkContinueGame();
            checkLobby();
        }
    }
}