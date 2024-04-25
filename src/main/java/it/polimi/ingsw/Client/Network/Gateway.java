package it.polimi.ingsw.Client.Network;

import java.io.IOException;

/**
 * Gateway of communication between client and server to call methods on Model
 * */
public class Gateway {
    private final Dispatcher dispatcher;
    private final Receiver receiver;

    public Gateway(NetworkManager networkManager) {
        this.dispatcher=new Dispatcher(networkManager);
        this.receiver=new Receiver(networkManager);
    }

    /**
     * Dispatches the placeCard message
     * @param card
     * @param x
     * @param y
     * @param isFlipped
     * @return the response to the message
     * @throws IOException
     * @throws MessageHandlerException
     */
    public boolean placeCard(String card, int x, int y, boolean isFlipped) throws IOException,MessageHandlerException {
        dispatcher.placeCard(card,x,y,isFlipped);
        return receiver.placeCard();
    }

    /**
     * Dispatches the drawCard message
     * @param cardIndex
     * @param drawingSection
     * @return the response to the message
     * @throws IOException
     * @throws MessageHandlerException
     */
    public String drawCard(int cardIndex, String drawingSection) throws IOException,MessageHandlerException {
        dispatcher.drawCard(cardIndex,drawingSection);
        return receiver.drawCard();
    }
    public boolean masterStatus() throws IOException {
        return receiver.masterStatus();
    }

    //TODO: server side -> returns true at the end of the creation of the game for the master player
    public boolean createGame(int playersNumber, String masterUsername) throws IOException,MessageHandlerException {
        dispatcher.createGame(playersNumber,masterUsername);
        return receiver.createGame();
    }
    //TODO: server side -> returns true at the end of the creation of the game for additional players
    public boolean joinGame(String playerUsername) throws IOException {
        dispatcher.joinGame(playerUsername);
        return receiver.joinGame();
    }
    //TODO: server side -> returns true when all players for the game have been created
    public boolean checkWaitForStart() throws IOException {
        dispatcher.checkWaitForStart();
        return receiver.checkWaitForStart();
    }
    //TODO: server side -> returns true if chosen username for this player is already used by another player
    public boolean unavailableUsername(String playerUsername) throws IOException {
        dispatcher.unavailableUsername(playerUsername);
        return receiver.unavailableUsername();
    }

}
