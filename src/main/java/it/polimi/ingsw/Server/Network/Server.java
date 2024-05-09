package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Server implements Runnable{
    private Parser parser;
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
    private ArrayList<PlayerConnection> connections;
    private static final String serverSetupAddress = "serverSetupInfo.json";
    /**
     * Creates a server using a custom port, then waits for a master player to connect
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        maxAllowablePlayers=0;
    }
    public ArrayList<PlayerConnection> getConnections() {
        return connections;
    }

    public void openServerSocket() throws IOException {
        this.serverSocket=new ServerSocket(this.port);
    }
    public void closeServerSocket() throws IOException {
        serverSocket.close();
    }

    /**
     * Waits for the master player to connect and add it to the connections
     * @throws IOException
     */
    private void waitMaster() throws IOException {
        this.connections=new ArrayList<PlayerConnection>();
        this.serverSocket=new ServerSocket(port);
        PlayerConnection master=connectPlayer(true);
        this.connections.add(master);
    }

    /**
     * Waits for a player connection
     * @throws IOException
     */
    public void waitPlayer() throws IOException {
        PlayerConnection player=connectPlayer(false);
        this.connections.add(player);
    }

    /**
     * Connects the master to the server
     * @param isMaster true if the player is the first to connect(the master), false otherwise
     * @return The master connection
     * @throws IOException
     */
    private PlayerConnection connectPlayer(boolean isMaster) throws IOException {
        Socket Socket=serverSocket.accept();
        //TODO: thread
        PlayerConnection playerConnection=new PlayerConnection(Socket, isMaster);
        new Thread(playerConnection).start();
        return playerConnection;
    }

    /**
     * Get a connection by his nickname
     * @param nickname
     * @return
     */
    public PlayerConnection getConnection(String nickname) {
        for(PlayerConnection pc:connections) {
            if(pc.getPlayer().getUsername().equals(nickname)) {
                return pc;
            }
        }
        return null;
    }
    public void setMaxAllowablePlayers(int maxAllowablePlayers) {
        this.maxAllowablePlayers=maxAllowablePlayers;
    }
    private void sendMasterStatus(String status) {
        MessageType messageType=MessageType.CONNECTION;
        String params=status;
        String outMessage=parser.toString(new Message(messageType,params));
        connections.getLast().setOutMessage(outMessage);
    }
    public void run() {
        try {
            waitMaster();
            MessageType messageType=MessageType.CONNECTION;
            String params="MASTER";
            String outMessage=parser.toString(new Message(messageType,params));
            connections.getLast().setOutMessage(outMessage);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        while(!serverSocket.isClosed()) {
            if(connections.size()<maxAllowablePlayers) {
                try {
                    waitPlayer();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
                MessageType messageType=MessageType.CONNECTION;
                String params="NOTMASTER";
                String outMessage=parser.toString(new Message(messageType,params));
                connections.getLast().setOutMessage(outMessage);

            }
        }
    }

}