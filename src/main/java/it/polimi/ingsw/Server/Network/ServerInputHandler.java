package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Controller.GameController;
import it.polimi.ingsw.Server.Controller.SetUpGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInputHandler implements Runnable{
    private Server server;
    public ServerInputHandler(Server server) {
        this.server=server;
    }

    /**
     * Reads and handles server input in commandline
     */
    private void readServerInput() {
        BufferedReader inKeyboard=new BufferedReader(new InputStreamReader(System.in));
        while(!server.isClosed()) {
            try {
                String input = inKeyboard.readLine();
                if(input.toUpperCase().strip().equals("CLOSE")) {
                    server.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void run() {
        readServerInput();
    }
}
