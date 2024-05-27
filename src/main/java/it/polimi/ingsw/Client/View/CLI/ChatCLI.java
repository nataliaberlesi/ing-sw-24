package it.polimi.ingsw.Client.View.CLI;


import java.util.ArrayList;

public class ChatCLI {

    private final ArrayList<String> messages = new ArrayList<>();

    private static final int RECENT_MESSAGES=3;

    public void addMessage(String message) {
        messages.add(message);
    }

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

    public void printCompleteChat(){
        System.out.println("COMPLETE CHAT");
        for (String message : messages) {
            System.out.println(message);
        }
    }

}
