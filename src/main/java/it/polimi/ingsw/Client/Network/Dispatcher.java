package it.polimi.ingsw.Client.Network;

import java.io.IOException;

public class Dispatcher {
    private final NetworkManager networkManager;
    public Dispatcher(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Sends to Server the request to place the card
     * @param card
     * @param x
     * @param y
     * @param isFlipped
     * @return
     * @throws IOException
     * @throws MessageHandlerException
     */
    public void placeCard(String card, int x, int y, boolean isFlipped) throws IOException, MessageHandlerException {
        Message message=new Message("GAME","placeCard", card, x, y, isFlipped);
        networkManager.send(message);
    }

    /**
     * Sends to Server the request to draw a card
     * @param cardIndex
     * @param drawingSection
     * @throws IOException
     */
    public void drawCard(int cardIndex, String drawingSection) throws IOException {
        Message message=new Message("GAME","drawCard", cardIndex, drawingSection);
        networkManager.send(message);
    }

    /**
     * Sends to Server the request to create a game
     * @param playersNumber number of players for the game
     * @param masterUsername username of master player
     * */
    public void createGame(int playersNumber, String masterUsername) throws IOException{
        Message message = new Message("SYSTEM","createGame", playersNumber, masterUsername);
        networkManager.send(message);
    }
    /**
     * Sends to Server the request to add a player to current game
     * @param playerUsername username of player to be added
     * */
    public void joinGame(String playerUsername) throws IOException{
        Message message = new Message("SYSTEM","joinGame", playerUsername);
        networkManager.send(message);
    }
    /**
     * Sends to Server the request to know if game can start
     * */
    public void checkWaitForStart() throws IOException {
        Message message = new Message("SYSTEM", "checkWaitForStart");
        networkManager.send(message);
    }
    /**
     * Sends to Server the request to know if player's username is available
     * @param playerUsername player's username
     * */
    public void unavailableUsername(String playerUsername) throws IOException{
        Message message = new Message("SYSTEM", "unavailableUsername", playerUsername);
    }


}
