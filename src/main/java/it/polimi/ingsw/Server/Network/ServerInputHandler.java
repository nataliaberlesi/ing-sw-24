package it.polimi.ingsw.Server.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInputHandler implements Runnable{
    private final Server server;
    public ServerInputHandler(Server server) {
        this.server=server;
    }

    /**
     * Reads and handles server input in commandline
     */
    private void readServerInput() throws IOException {
        BufferedReader inKeyboard=new BufferedReader(new InputStreamReader(System.in));
        while(!server.isClosed()) {
            String input = inKeyboard.readLine();
            if(input.toUpperCase().strip().equals("CLOSE")) {
                server.close();
            }
        }
    }
    public void run() {
        try {
            readServerInput();
        } catch (IOException e) {
            System.out.println("WARNING: Server input no longer available");
        }
    }
}
