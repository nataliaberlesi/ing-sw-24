package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * Scene that represents Chat for GUI
 */
public class ChatGUI extends Scene {
    /**
     * Scroll pane for chat conversation
     */
    private final ScrollPane scrollPane;
    /**
     * VBox to contain chat messages
     */
    private final VBox messageContainer;
    /**
     * Text area where player can write messages to send in chat
     */
    private final TextArea messageTextArea;
    /**
     * ChoiceBox to select who player wants to send the message to
     */
    private final ChoiceBox<String> receiverChoiceBox;
    /**
     * Instance of ViewControllerGUI to control chat
     */
    private final ViewControllerGUI viewControllerGUI;

    /**
     * Constructor of chat
     * @param viewControllerGUI viewControllerGUI
     */
    public ChatGUI(ViewControllerGUI viewControllerGUI) {
        super(new BorderPane(), 400, 565);
        this.viewControllerGUI = viewControllerGUI;
        BorderPane root = (BorderPane) this.getRoot();

        messageContainer = new VBox(10);
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);
        scrollPane.setContent(messageContainer);

        messageTextArea = new TextArea();
        messageTextArea.setStyle("-fx-font-size: 14px;");
        messageTextArea.setPromptText("Type your message here...");
        messageTextArea.setWrapText(true);

        Button sendButton = new Button("Send");
        StaticsForGUI.setButtonCharacteristics(sendButton, "System Bold", 16, 0,0,false);
        sendButton.setOnAction(event -> sendChatMessage());
        Label sendToLabel = new Label("  Send message to:");
        StaticsForGUI.setLabelCharacteristics(sendToLabel, "System", 16, 0,0);

        receiverChoiceBox = new ChoiceBox<>();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        HBox sendOptionsContainer = new HBox(30, sendToLabel, receiverChoiceBox, sendButton);
        VBox inputContainer = new VBox(10, messageTextArea, sendOptionsContainer, spacer);

        root.setCenter(scrollPane);
        root.setBottom(inputContainer);
    }

    /**
     * Adds players usernames to receiver choice box
     * @param playerUsernames players usernames to be added
     */
    protected void initializeReceiverChoiceBox(ArrayList<String> playerUsernames) {
        if (playerUsernames.size() > 2) { //If there are more than 2 players in game, also adds the "Everyone" option to choicebox
            receiverChoiceBox.getItems().add("Everyone");
            for (String playerUsername : playerUsernames) {
                if (!playerUsername.equalsIgnoreCase(viewControllerGUI.getMyPlayer().getUsername()))
                    receiverChoiceBox.getItems().add(playerUsername);
            }
        }
        else { //otherwise only adds the other player's username
            for (String playerUsername : playerUsernames) {
                if (!playerUsername.equalsIgnoreCase(viewControllerGUI.getMyPlayer().getUsername()))
                    receiverChoiceBox.getItems().add(playerUsername);
            }
        }
        receiverChoiceBox.getSelectionModel().selectFirst(); //select everyone as default choice
        receiverChoiceBox.setStyle("-fx-font-size: 14px; -fx-font-family: 'System';");
    }

    /**
     * Gets the written message and the player who it is destined to and sends it to server through viewControllerGUI
     */
    private void sendChatMessage(){
        String message = messageTextArea.getText().trim();
        String selectedReceiver = receiverChoiceBox.getValue();
        if (selectedReceiver.equals("Everyone")){
            selectedReceiver = "-all";
        }
        if (!message.isEmpty())
            viewControllerGUI.sendChatMessage(viewControllerGUI.getMyPlayer().getUsername(), selectedReceiver, message);
        messageTextArea.clear();
    }

    /**
     * Adds message to chat
     * @param message message to be added
     */
    protected void addMessageToChat(String message) {
        Text textMessage = new Text(message);
        textMessage.setStyle("-fx-font-size: 14px;");
        messageContainer.getChildren().add(textMessage);
        scrollToBottom();

    }

    /**
     * Scrolls to bottom so that last message is always visible
     */
    private void scrollToBottom() {
        scrollPane.setVvalue(1.0);
    }

}
