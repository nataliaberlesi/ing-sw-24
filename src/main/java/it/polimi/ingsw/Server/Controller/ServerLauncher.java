package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.ConnectionsHandler;
import it.polimi.ingsw.Server.Network.Server;
import it.polimi.ingsw.Server.Network.ServerInputHandler;

import java.io.IOException;

public class ServerLauncher {
    private static Server server;
    public static void main(String[] args){
        try {
            server=new Server(60600);
        } catch(IOException ioe) {
            System.out.println("ERROR: "+ioe);
            System.exit(0);
        }
        new Thread(server).start();
        new Thread(new ConnectionsHandler(server)).start();
        new Thread(new ServerInputHandler(server)).start();
    }

}
