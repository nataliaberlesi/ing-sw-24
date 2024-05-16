package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class representing the JavaFX application.
 */
public class GUIApplication extends Application {

    private static MessageParser messageParser;
    private static MessageDispatcher messageDispatcher;

    private static ViewControllerGUI viewControllerGUI;

    /**
     * Main method for JavaFX application.
     *

     */
    public static void main() {
        launch();
    }

    public static void setParserAndDispatcher(MessageParser newMessageParser, MessageDispatcher newMessageDispatcher) {
        GUIApplication.messageParser = newMessageParser;
        GUIApplication.messageDispatcher = newMessageDispatcher;
    }
    public static void setViewControllerGUI(ViewControllerGUI viewControllerGUI){
        GUIApplication.viewControllerGUI = viewControllerGUI;
    }

    /**
     * Start method for JavaFX application.
     *
     * @param primaryStage application primaryStage
     * @throws IOException if FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        viewControllerGUI.setMainStage(primaryStage);
        viewControllerGUI.setComponents();
        primaryStage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Codex Naturalis");
    }
}