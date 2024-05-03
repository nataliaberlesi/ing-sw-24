package it.polimi.ingsw.Server.Network;


import com.google.gson.Gson;
import it.polimi.ingsw.Server.Network.Message;
import it.polimi.ingsw.Server.Network.Parser;
import it.polimi.ingsw.Server.Model.Player;

import java.io.*;
import java.net.Socket;

public class PlayerConnection{
    /**
     * Channel used to communicate between a single player and the server
     */
    private Socket socket;
    /**
     * States if the player has been the first to connect to the server
     */
    private boolean isMaster;
    private Gateway gateway;
    /**
     * The player instance in the model
     */
    private Player player;
    private Parser parser;
    private BufferedReader inSocket;
    private PrintWriter outSocket;

    /**
     *
     * @param s Channel of communication between the player and the server
     * @param isMaster States if the player has been the first to connect to the server
     * @throws IOException
     */
    public PlayerConnection(Socket s, boolean isMaster) throws IOException {
        socket=s;
        setUpIO();
        this.isMaster=isMaster;
        this.gateway=new Gateway(this);
    }
    /**
     * Setups input stream and output stream for the socket
     * @throws IOException
     */
    private void setUpIO() throws IOException {
        inSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        parser= Parser.getInstance();
    }

    /**
     * Sends a Message into outSocket
     * @param message Is the Message to be sent
     */
    public void send(Message message) {
        outSocket.println(parser.toString(message)); //parses a Message into a json string
    }

    /**
     * Waits for a String to be received and parses it into a Message
     * @return The Message received
     * @throws IOException
     */
    public Message receive() throws IOException {
        return parser.toMessage(inSocket.readLine());
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isMaster() {
        return this.isMaster;
    }
    public Gateway getGateway() {
        return this.gateway;
    }
}

