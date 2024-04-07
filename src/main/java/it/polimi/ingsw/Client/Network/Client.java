package it.polimi.ingsw.Client.Network;

import java.io.*;
import java.net.Socket;

public class Client {
    private PrintWriter outSocket;
    private BufferedReader inSocket;
    private BufferedReader inKeyboard;
    private Socket socket;
    public Client(String server ,int port) throws IOException {
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
        this.inKeyboard=new BufferedReader(new InputStreamReader(System.in));
    }

}
