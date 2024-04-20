package it.polimi.ingsw.Client.Network;
import it.polimi.ingsw.Client.Network.Message;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MessageHandler {
    /**
     * the map from types to their dispatchers
     */
    private final Map<String, BiConsumer<String,ArrayList<Object>>> types;
    /**
     * the map from game methods to their handlers
     */
    private final Map<String, Consumer<ArrayList<Object>>> gameMethods;
    /**
     * the map from system methods to their handlers
     */
    private final Map<String, Consumer<ArrayList<Object>>> systemMethods;
    public MessageHandler() {
        gameMethods =new HashMap<String, Consumer<ArrayList<Object>>>();
        gameMethods.put("placeCard",this::handlePlaceCard);

        systemMethods =new HashMap<String, Consumer<ArrayList<Object>>>();

        types=new HashMap<String, BiConsumer<String,ArrayList<Object>>>();
        types.put("GAME",this::handleGame);
        types.put("SYSTEM",this::handleSystem);
    }

    /**
     * handle a message
     * @param message
     */
    public void handle(Message message, String type, String method) throws MessageHandlerException {
        if(message.getMethod().equals(method) && message.getType().equals(type)) {
            handle(message);
        } else {
            throw new MessageHandlerException("Wrong message");
        }
    }
    /**
     * handle a message
     * @param message
     */
    public void handle(Message message) throws MessageHandlerException {
        String type= message.getType();
        String method= message.getMethod();
        ArrayList<Object> params=message.getParams();

        BiConsumer<String,ArrayList<Object>> dispatchType=types.get(type);
        if(dispatchType!=null) {
            dispatchType.accept(method,params);
        } else {
            throw new MessageHandlerException("No such type found: "+type);
        }
    }

    /**
     * handles game methods only
     * @param method the method to execute
     * @param params the method's parameters
     */
    private void handleGame(String method, ArrayList<Object> params) throws MessageHandlerException {
        Consumer<ArrayList<Object>> handleMethod= gameMethods.get(method);
        if(handleMethod!=null) {
            handleMethod.accept(params);
        } else {
            throw new MessageHandlerException("No such method found: "+method);
        }
    }

    /**
     * handles system methods only
     * @param method the method to execute
     * @param params the method's parameters
     */
    private void handleSystem(String method, ArrayList<Object> params) throws MessageHandlerException {
        Consumer<ArrayList<Object>> handleMethod= systemMethods.get(method);
        if(handleMethod!=null) {
            handleMethod.accept(params);
        } else {
            throw new MessageHandlerException("No such method found: "+method);
        }

    }

    /**
     * handles the placeCard method
     * @param params
     */
    private void handlePlaceCard(ArrayList<Object> params) throws MessageHandlerException{
        if(!(params.get(0) instanceof String)||!(params.get(1) instanceof Integer || !(params.get(2) instanceof Integer) || !(params.get(3) instanceof Boolean))) {
            throw new MessageHandlerException("Wrong parameters exception");
        };
    }

}
