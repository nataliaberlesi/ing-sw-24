package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageDispatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class representing the JavaFX application.
 */
public class GUIApplication extends Application {

    private static MessageParser messageParser;
    private static MessageDispatcher messageDispatcher;

    /**
     * Main method for JavaFX application.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }

    public static void setParserAndDispatcher(MessageParser newMessageParser, MessageDispatcher newMessageDispatcher) {
        GUIApplication.messageParser = newMessageParser;
        GUIApplication.messageDispatcher = newMessageDispatcher;
    }

    /**
     * Start method for JavaFX application.
     *
     * @param primaryStage application primaryStage
     * @throws IOException if FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("initialScreen.fxml"));
        GUIMainController controller = new GUIMainController(messageParser, messageDispatcher, primaryStage);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(String.valueOf(GUIApplication.class.getResource("Images/cranioLogo.png"))));
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Codex Naturalis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}