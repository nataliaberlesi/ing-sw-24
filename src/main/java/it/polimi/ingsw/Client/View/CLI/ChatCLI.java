package it.polimi.ingsw.Client.View.CLI;


import java.util.ArrayList;

/**
 * contains all messages sent between players
 */
public class ChatCLI {

    /**
     * list of messages sent between players
     */
    private final ArrayList<String> messages = new ArrayList<>();
    /**
     * amount of messages shown in main scene
     */
    private static final int RECENT_MESSAGES=3;

    /**
     * adds new message to chat
     * @param message sent by a player
     */
    public void addMessage(String message) {
        messages.add(message);
    }

    /**
     * shows most recent messages
     */
    public void printRecentMessages() {
        System.out.println("RECENT MESSAGES");
        int size = messages.size();
        for (int i = size-RECENT_MESSAGES; i < size; i++) {
            if(i>=0) {
                System.out.println(messages.get(i));
            }
            else {
                System.out.println("-");
            }
        }
    }

    /**
     * shows all messages
     */
    public void printCompleteChat(){
        System.out.println("COMPLETE CHAT");
        for (String message : messages) {
            System.out.println(message);
        }
    }

}
