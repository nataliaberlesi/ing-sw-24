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
    private String readMessage;

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

    public void close() throws IOException {
        while(outMessage!=null) {}
        socket.close();
    }

    /**
     * Sends a Message into outSocket
     */
    public void threadSendMethod() {
        while(socket.isConnected()) {
            if(outMessage!=null) {
                System.out.println("OUT | "+outMessage);
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
                if(inMessage==null) {
                    this.readMessage=inSocket.readLine();
                    System.out.println("IN | "+readMessage);
                    getInMessage(true);
                }

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
    public synchronized String getInMessage(boolean read) throws IOException {
        if(read) {
            this.inMessage=this.readMessage;
            return inMessage;
        } else {
            String consumedMessage=this.inMessage;
            this.inMessage=null;
            return consumedMessage;
        }
    }
    public void setOutMessage(String outMessage) {
        this.outMessage=outMessage;
    }
    @Override
    public void run() {
        new Thread(this::threadReceiveMethod).start();
        new Thread(this::threadSendMethod).start();
    }
}

