package it.polimi.ingsw.Server.Network;


import com.google.gson.Gson;
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
    boolean isMaster;
    /**
     * The player instance in the model
     */
    private Player player;
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
    }

    /**
     * Setups input stream and output stream for the socket
     * @throws IOException
     */
    private void setUpIO() throws IOException {
        inSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }

    /**
     * Sends a Message into the output channel
     * @param message Is the Message to be sent
     */
    public void send(Message message) {
        outSocket.println(new Gson().toJson(message)); //converts a Message into a json string
    }

    /**
     * Waits for a Message to be received
     * @return The Message received
     * @throws IOException
     */
    public Message receive() throws IOException {
        return new Gson().fromJson(inSocket.readLine(), Message.class); //converts a json String into a Message
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
