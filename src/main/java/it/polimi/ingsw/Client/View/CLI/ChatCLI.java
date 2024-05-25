package it.polimi.ingsw.Client.View.CLI;


import java.util.ArrayList;
import java.util.Objects;

public class ChatCLI {

    private final ArrayList<ChatMessageCLI> messages = new ArrayList<>();

    private static final int RECENT_MESSAGES=3;

    public void addMessage(String sender,String receiver, String message, boolean isPrivate) {
        messages.add(new ChatMessageCLI(sender, receiver, message, isPrivate));
    }

    public void printRecentMessages() {
        System.out.println("RECENT MESSAGES");
        int size = messages.size();
        for (int i = size-RECENT_MESSAGES; i < size; i++) {
            if(i>=0) {
                messages.get(i).printMessage();
            }
            else {
                System.out.println("-");
            }
        }
    }

    public void printCompleteChat(){
        System.out.println("COMPLETE CHAT");
        for (ChatMessageCLI message : messages) {
            message.printMessage();
        }
    }
    private class ChatMessageCLI{
        private final String sender;
        private final String message;
        private final String receiver;
        private final boolean isPrivate;

        public ChatMessageCLI(String sender, String receiver, String message, boolean isPrivate){
            this.isPrivate=isPrivate;
            this.message=message;
            this.sender=sender;
            this.receiver=receiver;
        }

        private void printMessage(){
            String prefix="PUBLIC";
            if(isPrivate){
                prefix="PRIVATE";
                if(!Objects.equals(receiver, "")){
                    prefix+=" TO "+receiver;
                }
            }
            System.out.println(prefix+" FROM "+sender+": "+message);
        }
    }
}
