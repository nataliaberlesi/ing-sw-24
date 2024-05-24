package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Server.Controller.GameController;
import it.polimi.ingsw.Server.Controller.SetUpGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInputHandler implements Runnable{
    private Server server;
    private GameController gameController;
    public ServerInputHandler(Server server, GameController gameController) {
        this.server=server;
        this.gameController=gameController;
    }
    public void readServerInput() {
        BufferedReader inKeyboard=new BufferedReader(new InputStreamReader(System.in));
        while(!server.isClosed()) {
            try {
                String input = inKeyboard.readLine();
                if(input.toUpperCase().strip().equals("CLOSE")) {
                    server.close();
                }
                if(input.toUpperCase().strip().equals("ENDGAME")){

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
