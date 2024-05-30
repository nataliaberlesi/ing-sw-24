package it.polimi.ingsw.Client.Network;

import it.polimi.ingsw.Client.Network.DTO.InParamsDTO;
import it.polimi.ingsw.Client.Network.DTO.ParamsDTO;

import java.io.*;
import java.net.Socket;

public class NetworkManager implements Runnable{
    private PrintWriter outSocket;
    private BufferedReader inSocket;
    private Socket socket;
    private MessageParser messageParser;
    private String inMessage;
    private String outMessage;

    /**
     * setups the network manager getting server and port as input
     * @param server server IP address
     * @param port port of the server
     */
    public NetworkManager(String server , int port) throws IOException {
        connect(server, port);
        setupIO();
    }
    /**
     * Connects the client to a server in a specific port
     * @param server The address of the server
     * @param port The port of the server
     */
    private void connect(String server, int port) throws IOException {
        this.socket=new Socket(server,port);
        socket.setSoTimeout(10000);
    }

    /**
     * Setups outSocket, inSocket and inKeyboard
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
     * Used to receive messages and to build them into the messageParser.
     * Also closes the connection if the inMessage is ABORT or if the connection throws an IOException.
     * If the connection is closed, builds an ABORT message.
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
            } catch(RuntimeException | IOException re) {
                try {
                    socket.close();
                    messageParser.buildMessage(
                            messageParser.toJson(
                                    new Message(
                                            MessageType.ABORT,
                                            new ParamsDTO(
                                                    new InParamsDTO(re.getMessage()),null))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void run() {
        new Thread(this::threadReceiveMethod).start();
        new Thread(this::threadSendMethod).start();
    }

}
