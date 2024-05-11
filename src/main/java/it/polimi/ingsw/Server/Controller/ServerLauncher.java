package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.ConnectionsHandler;
import it.polimi.ingsw.Server.Network.Server;

import java.io.IOException;

public class ServerLauncher {
    private static Server server;
    public static void main(String[] args){
        try {
            server=new Server(60600);
        } catch(IOException ioe) {

        }
        new Thread(server).start();

    }

}
