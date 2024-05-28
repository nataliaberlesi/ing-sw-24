package it.polimi.ingsw.Client.View.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Loading scene used to indicate to player that game will start soon
 */
public class LoadingScene extends Scene {

    /**
     * Constructor
     */
    public LoadingScene() {
        super(new AnchorPane(), 1060, 595);
        AnchorPane root = (AnchorPane) this.getRoot();
        configureBackground(root);
    }

    /**
     * Configures loading gif as background
     * @param root root
     */
    private void configureBackground(AnchorPane root) {
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/loadingScreen.gif")));
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        root.setBackground(new Background(bgImage));
    }
}

