package it.polimi.ingsw.Client.View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class GUIApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("initialScreen.fxml"));
        GUIMainController controller = new GUIMainController(primaryStage);
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