package it.polimi.ingsw.Server.Network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements Runnable{
    /**
     * The port where the server listens
     */
    private final int port;
    /**
     * The server socket that listens for connections
     */
    private ServerSocket serverSocket;
    private int maxAllowablePlayers;

    /**
     * The active connections inside the server
     */
    private ArrayList<PlayerConnection> connections= new ArrayList<>();
    private boolean masterIsConnected;
    private boolean lobbyIsOpen;
    private boolean serverIsOpen;
    /**
     * Creates a server using a custom port, then waits for a master player to connect
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        maxAllowablePlayers=0;
        masterIsConnected=false;
        lobbyIsOpen=false;
        serverIsOpen=true;
        this.serverSocket=new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
    }
    public ArrayList<PlayerConnection> getConnections() {
        return connections;
    }

    public void closeLobby() {lobbyIsOpen=false;}
    public void restart() {
        masterIsConnected=false;
        maxAllowablePlayers=0;
        connections.clear();
        System.out.println("SERVER RESTARTED");
    }
    /**
     * Waits for the master player to connect and add it to the connections
     * @throws IOException
     */
    private void waitMaster() throws IOException {
        PlayerConnection master=connectPlayer(true);
        this.connections.add(master);
    }

    /**
     * Waits for a player connection
     * @throws IOException
     */
    public void waitPlayer() throws IOException {
        PlayerConnection player=connectPlayer(false);
        synchronized (connections){
            this.connections.add(player);
        }
    }

    /**
     * Connects the master to the server
     * @param isMaster true if the player is the first to connect(the master), false otherwise
     * @return The master connection
     * @throws IOException
     */
    private PlayerConnection connectPlayer(boolean isMaster) throws IOException {
        Socket Socket=serverSocket.accept();
        //TODO: thread
        PlayerConnection playerConnection=new PlayerConnection(Socket, isMaster);
        new Thread(playerConnection).start();
        return playerConnection;
    }

    public void setMaxAllowablePlayers(int maxAllowablePlayers) {
        this.maxAllowablePlayers=maxAllowablePlayers;
    }
    /**
     *
     * @param masterStatus
     */
    private void sendMasterStatus(Boolean masterStatus) {
        Message message=MessageCrafter.craftConnectMessage(masterStatus);
        String outMessage=MessageParser.getINSTANCE().toJson(message);
        connections.getLast().setOutMessage(outMessage);
    }
    private void readServerInput() {
        BufferedReader inKeyboard=new BufferedReader(new InputStreamReader(System.in));
        while(!serverSocket.isClosed()) {
            String input= null;
            try {
                input = inKeyboard.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(input.equals("close")) {
                serverIsOpen=false;
            }
        }
    }
    public void run() {
        new Thread(this::readServerInput).start();
        while(!serverSocket.isClosed()) {
            if(!masterIsConnected) {
                try {
                    waitMaster();
                    sendMasterStatus(true);
                    new Thread(new ConnectionsHandler(this)).start();
                    masterIsConnected=true;
                    lobbyIsOpen=true;
                } catch (IOException ioe) {
                    //waiting for master
                }
            }
            if(connections.size()<maxAllowablePlayers && lobbyIsOpen) {
                try {
                    waitPlayer();
                    sendMasterStatus(false);
                } catch (IOException ioe) {
                    //waiting for player
                }
            }
        }
    }

}