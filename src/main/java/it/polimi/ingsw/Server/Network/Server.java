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
    private static final String serverSetupAddress = "serverSetupInfo.json";
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
    public Server() throws IOException {
        this.port=getPortFromSettings();
        openServerSocket();
        waitMaster();
    }
    private int getPortFromSettings() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(serverSetupAddress));
        Map<String, String> settings = gson.fromJson(reader, Map.class);
        reader.close();
        return Integer.parseInt(settings.get("port"));
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
        PlayerConnection master=connectPlayer(true);
        this.connections.add(master);
    }

    /**
     * Waits for a player connection
     * @throws IOException
     */
    public void waitPlayer() throws IOException {
        openServerSocket();
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
        closeServerSocket();
        return new PlayerConnection(Socket, isMaster);
    }

}