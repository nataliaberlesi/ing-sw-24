package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class GUISecondaryController {

    private final Stage stage;

    private final MessageParser messageParser;
    private final MessageDispatcher messageDispatcher;

    private final Alert errorAlert;
    private final Alert messageAlert;

    private boolean firstRound;
    private boolean lastRound;

    protected GUISecondaryController(MessageParser messageParser, MessageDispatcher messageDispatcher, Stage stage) {
        this.messageParser = messageParser;
        this.messageDispatcher = messageDispatcher;
        this.stage = stage;
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        this.messageAlert = new Alert(Alert.AlertType.INFORMATION);
    }

}
