package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Server.Model.Player;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Runnable{
    /**
     * Channel used to communicate between a single player and the server
     */
    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private Message outMessage;
    private Message inMessage;
    private Message readMessage;
    private final MessageParser messageParser;

    /**
     *
     * @param s Channel of communication between the player and the server
     * @throws IOException
     */
    public PlayerConnection(Socket s) throws IOException {
        socket=s;
        messageParser=MessageParser.getINSTANCE();
        setUpIO();
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
     * Closes the connection
     */
    public void close() throws IOException {
        while(outMessage!=null) {}
        socket.close();
    }

    /**
     * Verifies if the connection is closed
     * @return the state of the connection
     */
    public boolean socketIsClosed() {
        return socket.isClosed();
    }

    /**
     * Sends a Message into outSocket
     */
    public void send() {
        if(!socket.isClosed()) {
            if(outMessage!=null) {
                System.out.println("OUT | "+outMessage);
                outSocket.println(messageParser.toJson(outMessage));
                outMessage=null;
            }
        }
    }
    /**
     * Waits for a String to be received
     */
    public void threadReceiveMethod() {
        while(!socket.isClosed()) {
            try{
                if(inMessage==null) {
                    this.readMessage=messageParser.parseMessage(inSocket.readLine());
                    getInMessage(true);
                }

            } catch(IOException ioe) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Player disconnected");
    }
    /**
     * Gets the actual inMessage
     * @param read if it's reading
     * @return the message read
     */
    public synchronized Message getInMessage(boolean read){
        if(read) {
            this.inMessage=this.readMessage;
            return inMessage;
        } else {
            Message consumedMessage=this.inMessage;
            this.inMessage=null;
            return consumedMessage;
        }
    }

    /**
     * Sets the outMessage and then sends it
     * @param outMessage the message to send
     */
    public synchronized void setOutMessage(Message outMessage) {
        this.outMessage=outMessage;
        send();
    }
    public Message getOutMessage() {
        synchronized (this) {
            return this.outMessage;
        }
    }
    @Override
    public void run() {
        threadReceiveMethod();
    }
}

