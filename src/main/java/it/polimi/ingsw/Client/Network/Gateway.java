package it.polimi.ingsw.Client.Network;

import java.io.IOException;

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
}
