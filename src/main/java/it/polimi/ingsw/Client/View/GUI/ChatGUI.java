package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatGUI extends Scene {
    private final ScrollPane scrollPane;
    private final VBox messageContainer;
    private final TextArea messageTextArea;
    private final ChoiceBox<String> receiverChoiceBox;
    private final ViewControllerGUI viewControllerGUI;

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

    protected void initializeReceiverChoiceBox(ArrayList<String> playerUsernames) {
        if (playerUsernames.size() > 2) {
            receiverChoiceBox.getItems().add("Everyone");
            for (String playerUsername : playerUsernames) {
                if (!playerUsername.equalsIgnoreCase(viewControllerGUI.getMyPlayer().getUsername()))
                    receiverChoiceBox.getItems().add(playerUsername);
            }
        }
        else {
            for (String playerUsername : playerUsernames) {
                if (!playerUsername.equalsIgnoreCase(viewControllerGUI.getMyPlayer().getUsername()))
                    receiverChoiceBox.getItems().add(playerUsername);
            }
        }
        receiverChoiceBox.getSelectionModel().selectFirst(); //select everyone as default choice
    }

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

    protected void addMessageToChat(String message) {
        Text textMessage = new Text(message);
        textMessage.setStyle("-fx-font-size: 14px;");
        messageContainer.getChildren().add(textMessage);
        scrollToBottom();

    }

    private void scrollToBottom() {
        scrollPane.setVvalue(1.0);
    }

}
