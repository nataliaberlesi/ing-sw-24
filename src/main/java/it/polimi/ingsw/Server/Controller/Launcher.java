package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.Server;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Server server = null;
        try {
            server = new Server();
        } catch (IOException ioe) {
            gameController.shutDown();
        }
        gameController.setServer(server);
        try {
            gameController.createGame();
        } catch(IOException ioe) {
            gameController.shutDown();
        }
        try {
            gameController.startGame();
        } catch(IOException ioe){
            gameController.shutDown();
        }

    }
}
