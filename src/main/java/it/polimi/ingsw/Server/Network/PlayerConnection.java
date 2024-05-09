package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Server.Model.Player;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Runnable{
    /**
     * Channel used to communicate between a single player and the server
     */
    private Socket socket;
    /**
     * States if the player has been the first to connect to the server
     */
    private boolean isMaster;
    private MessageParser messageParser;
    /**
     * The player instance in the model
     */
    private Player player;
    private Parser parser;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String outMessage;
    private String inMessage;

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
        parser= Parser.getInstance();
    }

    /**
     * Sends a Message into outSocket
     */
    public void threadSendMethod() {
        while(socket.isConnected()) {
            if(outMessage!=null) {
                outSocket.println(outMessage);
                outMessage=null;
            }
        }
    }

    /**
     * Waits for a String to be received and parses it into a Message
     * @throws IOException
     */
    public void threadReceiveMethod() {
        while(socket.isConnected()) {
            try{
                inMessage=inSocket.readLine();
            } catch(IOException ioe) {
                //TODO
            }
        }
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
    public String getInMessage() {
        return this.inMessage;
    }
    public synchronized void setOutMessage(String outMessage) {
        this.outMessage=outMessage;
    }
    @Override
    public void run() {
        new Thread(this::threadReceiveMethod).start();
        new Thread(this::threadSendMethod).start();
    }
}

