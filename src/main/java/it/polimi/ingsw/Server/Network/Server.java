package it.polimi.ingsw.Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    /**
     * The port where the server listens
     */
    private final int port;
    /**
     * The server socket that listens for connections
     */
    private ServerSocket serverSocket;

    /**
     * The active connections inside the server
     */
    private ArrayList<PlayerConnection> connections;

    /**
     * Creates a server using a custom port, then waits for a master player to connect
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        openServerSocket();
        waitMaster();
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
    private void waitMaster() throws IOException {this.connections=new ArrayList<PlayerConnection>();
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
        return new PlayerConnection(Socket, isMaster);
    }

}