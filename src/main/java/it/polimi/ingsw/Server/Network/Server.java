package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private final Parser parser;
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
    private boolean masterIsConnected;
    /**
     * Creates a server using a custom port, then waits for a master player to connect
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.parser=Parser.getInstance();
        this.port = port;
        maxAllowablePlayers=1;
        masterIsConnected=false;
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
        this.connections=new ArrayList<>();
        this.serverSocket=new ServerSocket(port);
        PlayerConnection master=connectPlayer(true);
        this.connections.add(master);
        masterIsConnected=true;
    }

    /**
     * Waits for a player connection
     * @throws IOException
     */
    public void waitPlayer() throws IOException {
        PlayerConnection player=connectPlayer(false);
        synchronized (connections) {
            this.connections.add(player);
        }
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

    /**
     *
     * @param masterStatus
     */
    private void sendMasterStatus(Boolean masterStatus) {
        Message message=MessageCrafter.craftConnectMessage(masterStatus);
        Gson gson=new Gson();
        String outMessage=gson.toJson(message);
        connections.getLast().setOutMessage(outMessage);
    }
    public void run() {
        if(!masterIsConnected) {
            try {
                waitMaster();
                sendMasterStatus(true);
                new Thread(new ConnectionsHandler(this)).start();
            } catch (IOException ioe) {
                System.out.println("LOG: Failed to accept master");
            }
        }
        while(!serverSocket.isClosed()) {
            if(connections.size()<maxAllowablePlayers) {
                try {
                    waitPlayer();
                } catch (IOException ioe) {
                    System.out.println("LOG: Failed to accept player");
                }
                sendMasterStatus(false);
            }
        }
    }

}