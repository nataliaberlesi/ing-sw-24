package it.polimi.ingsw.Server.Network;


import java.io.*;
import java.net.Socket;

public class PlayerConnection{
    /**
     * Channel used to communicate between a single player and the server
     */
    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    /**
     * States if the player has been the first to connect to the server
     */
    boolean isMaster;

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
     * Sends a String into the output channel
     * @param s Is the message to be sent
     */
    public void send(String s) {

    }

    /**
     * Waits for a message to be received
     * @return The message received
     * @throws IOException
     */
    public String receive() throws IOException {
        return "";
    }
}

