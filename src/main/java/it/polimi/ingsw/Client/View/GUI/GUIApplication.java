package it.polimi.ingsw.Client.View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/it/polimi/ingsw/Client.View.GUI/initialScreen.fxml")));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/it/polimi/ingsw/Client.View.GUI/Images/cranioLogo.png"))));
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Codex Naturalis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}