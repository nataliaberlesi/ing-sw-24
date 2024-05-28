package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class representing the JavaFX main application thread.
 */
public class GUIApplication extends Application {

    /**
     * Instance of MessageParser to read messages from server
     */
    private static MessageParser messageParser;
    /**
     * Instance of MessageDispatcher to send messages to server
     */
    private static MessageDispatcher messageDispatcher;

    /**
     * Instance of ViewControllerGUI to control GUIApplication
     */
    private static ViewControllerGUI viewControllerGUI;

    /**
     * Main method used to launch JavaFX application.
     */
    public static void main() {
        launch();
    }

    /**
     * Setter for messageParser and messageDispatcher
     * @param newMessageParser MessageParser instance
     * @param newMessageDispatcher MessageDispatcher instance
     */
    public static void setParserAndDispatcher(MessageParser newMessageParser, MessageDispatcher newMessageDispatcher) {
        GUIApplication.messageParser = newMessageParser;
        GUIApplication.messageDispatcher = newMessageDispatcher;
    }

    /**
     * Setter for viewControllerGUI
     * @param viewControllerGUI ViewControllerGUI instance
     */
    public static void setViewControllerGUI(ViewControllerGUI viewControllerGUI){
        GUIApplication.viewControllerGUI = viewControllerGUI;
    }

    /**
     * Start method for JavaFX application.
     * @param primaryStage application's primaryStage
     * @throws Exception if application can't be started
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        viewControllerGUI.setStage(primaryStage);
        viewControllerGUI.setPopUpStage();
        viewControllerGUI.setChatStage();
    }
}