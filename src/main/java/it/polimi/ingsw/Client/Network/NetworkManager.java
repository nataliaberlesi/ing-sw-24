package it.polimi.ingsw.Client.Network;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class NetworkManager {
    private PrintWriter outSocket;
    private BufferedReader inSocket;
    private BufferedReader inKeyboard;
    private Socket socket;
    private Parser parser;
    private static final String networkManagerSetupAddress="networkManagerSetup.json";

    /**
     * setups the network manager getting server and port as input
     * @param server
     * @param port
     * @throws IOException
     */
    public NetworkManager(String server , int port) throws IOException {
        connect(server, port);
        setupIO();
    }

    /**
     * setups the network manager getting server and port from the resources
     * @throws IOException
     */
    public NetworkManager() throws IOException {
        Map <String,String> settings= getSettings();
        connect(settings.get("Server"),Integer.parseInt(settings.get("Port")));
        setupIO();
    }

    /**
     * reads networkManagerSetupAddress and extracts the settings
     * @return the server's settings
     * @throws IOException
     */
    private Map<String,String> getSettings() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(networkManagerSetupAddress));
        Map<String, String> settings = gson.fromJson(reader, Map.class);
        reader.close();
        return settings;
    }

    /**
     * Connects the client to a server in a specific port
     * @param server The address of the server
     * @param port The port of the server
     * @throws IOException
     */
    private void connect(String server, int port) throws IOException {
        this.socket=new Socket(server,port);
    }

    /**
     * Setups outSocket, inSocket and inKeyboard
     * @throws IOException
     */
    private void setupIO() throws IOException {
        this.outSocket=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        this.inSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.inKeyboard=new BufferedReader(new InputStreamReader(System.in));
    }
    /**
     * Waits for a String to be received and parses it into a Message
     * @return The Message received
     * @throws IOException
     */
    public Message receive() throws IOException {
        return parser.toMessage(inSocket.readLine());
    }
    /**
     * Sends a Message into outSocket
     * @param message Is the Message to be sent
     */
    public void send(Message message) {
        outSocket.println(parser.toString(message)); //parses a Message into a json string
    }

}
