package it.polimi.ingsw.Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() throws IOException {
        ServerSocket ss = new ServerSocket(3000);
        while (true) {
            Socket s = ss.accept();
            new Thread(new ServerxClient(s)).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}