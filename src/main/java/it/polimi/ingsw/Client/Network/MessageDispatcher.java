package it.polimi.ingsw.Client.Network;

import it.polimi.ingsw.Client.Network.DTO.*;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CoordinatesDTO;
import it.polimi.ingsw.Server.Model.Coordinates;

/**
 * Used to compose a message and send it to the server
 */
public class MessageDispatcher {
    /**
     * The network manager instance to communicate with the server
     */
    private final NetworkManager networkManager;
    /**
     * The MessageParser used to create json strings
     */
    private final MessageParser messageParser;
    public MessageDispatcher(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.messageParser=MessageParser.getInstance();
    }
    /**
     * dispatches a message
     */
    private void dispatch(MessageType type, ParamsDTO params){
        Message message=new Message(type, params);
        networkManager.setOutMessage(messageParser.toJson(message));
    }

    /**
     * Dispatches a CREATE message
     * @param username the sender
     */
    public void createGame(int numberOfPlayer, String username) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,numberOfPlayer);
        dispatch(MessageType.CREATE,new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a JOIN message
     * @param username the sender
     */
    public void joinGame(String username) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username);
        dispatch(MessageType.JOIN,new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a FIRSTROUND message
     * @param username the sender
     * @param isFaceUp the starting card
     * @param color the chosen color
     */
    public void firstRound(String username, Boolean isFaceUp, String color) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,isFaceUp,color);
        dispatch(MessageType.FIRSTROUND,new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a SECONDROUND message
     * @param username the sender
     * @param index the index of the chosen private objective
     */
    public void secondRound(String username, Integer index) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(index,username);
        dispatch(MessageType.SECONDROUND,new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a ACTION_PLACECARD message
     * @param username the sender
     * @param index of the card in hand
     * @param coordinates where the card is placed
     */
    public void placeCard(String username, Boolean isFaceUp, Integer index, Coordinates coordinates) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, isFaceUp, index, new CoordinatesDTO(coordinates.getX(), coordinates.getY()));
        dispatch(MessageType.ACTION_PLACECARD, new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a ACTION_DRAWCARD message
     * @param username sender
     * @param index of card in the drawablesection
     */
    public void drawCard(String username, Integer index, String drawableSection) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username,index,drawableSection);
        dispatch(MessageType.ACTION_DRAWCARD, new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a ABORT message
     * @param username sender
     * @param cause of abort
     */
    public void abortGame(String username, String cause) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, cause);
        dispatch(MessageType.ABORT, new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a PERSISTENCE message
     * @param persistence yes or no
     */
    public void notifyPersistence(boolean persistence) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(persistence);
        dispatch(MessageType.PERSISTENCE,new ParamsDTO(outParamsDTO));
    }

    /**
     * Dispatches a CHAT message
     * @param username the sender
     * @param affectedPlayer the receiver
     * @param chat the message
     */
    public void chat(String username, String affectedPlayer, String chat) {
        OutParamsDTO outParamsDTO=new OutParamsDTO(username, affectedPlayer,chat);
        dispatch(MessageType.CHAT,new ParamsDTO(outParamsDTO));
    }
    public void poke(){
        dispatch(MessageType.POKE,new ParamsDTO(null,null));
    }
}
