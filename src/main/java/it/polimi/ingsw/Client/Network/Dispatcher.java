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
        Message message=new Message("GAME","placeCard",card,x,y,isFlipped);
        networkManager.send(message);
    }

    /**
     * Sends to Server the request to draw a card
     * @param cardindex
     * @param drawingSection
     * @throws IOException
     */
    public void drawCard(int cardindex, String drawingSection) throws IOException {
        Message message=new Message("GAME","drawCard",cardindex,drawingSection);
        networkManager.send(message);
    }


}
