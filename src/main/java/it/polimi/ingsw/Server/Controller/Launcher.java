package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.Server;

import java.io.IOException;

public class Launcher {
    private static Server server;
    public static void main(String[] args){
        try {
            server=new Server(60600);
        } catch(IOException ioe) {

        }
        new Thread(server).start();
        Launcher.openConnectionsHandler();

    }
    private static void openConnectionsHandler() {
        new Thread(new ConnectionsHandler(server));
    }
}
