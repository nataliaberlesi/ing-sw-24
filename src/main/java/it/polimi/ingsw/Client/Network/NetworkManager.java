package it.polimi.ingsw.Client.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Server.Network.MessageCrafter;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class NetworkManager implements Runnable{
    private PrintWriter outSocket;
    private BufferedReader inSocket;
    private Socket socket;
    private MessageParser messageParser;
    private String inMessage;
    private String outMessage;

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
    }
    public void setMessageParser(MessageParser messageParser) {
        this.messageParser = messageParser;
    }
    public void setOutMessage(String outMessage) {
        this.outMessage=outMessage;
    }

    /**
     * Sends a message into outSocket if it's present
     */
    public void threadSendMethod() {
        while(!socket.isClosed()) {
            if(outMessage!=null) {
                outSocket.println(outMessage);
                outMessage=null;
            }
        }
    }

    /**
     * Used to receive messages and to build them into the messageParser. Also closes the connection if the inMessage is
     * ABORT or if the connection throws an IOException. If the connection is closed, builds an ABORT message.
     */
    public void threadReceiveMethod(){
        while(!socket.isClosed()) {
            try{
                inMessage=inSocket.readLine();
                if(inMessage!=null) {
                    messageParser.buildMessage(inMessage);
                    if(messageParser.getMessageType()==MessageType.ABORT) {
                        socket.close();
                    }
                    inMessage=null;
                }
            } catch(IOException ioe) {
                try {
                    socket.close();
                } catch(IOException ioe1) {
                    throw new RuntimeException();
                }
            }
        }
        messageParser.buildMessage(messageParser.toJson(MessageCrafter.craftAbortMessage("Server is unreachable")));
    }
    public void run() {
        new Thread(this::threadReceiveMethod).start();
        new Thread(this::threadSendMethod).start();

    }

}
