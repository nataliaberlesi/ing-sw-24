package it.polimi.ingsw.Client.View.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for grouping static constants and methods that will be used in different classes in GUI
 */
public class StaticsForGUI {
    /**
     * Indicates if player is able to perform actions in their game, used mainly to enable confirm button clicks in MainScene class
     */
    protected static boolean enableActions = false;
    /**
     * Indicates if at least one corner is selected in a board card
     */
    protected static boolean atLeastOneCornerSelected = false;

    public static Map<String, Double> dimensions = new HashMap<>();

    static {
        dimensions.put("cardWidth", 94.875);
        dimensions.put("cardHeight", 63.25);
        dimensions.put("cornerWidth", 22.425);
        dimensions.put("cornerHeight", 26.45);
        dimensions.put("boardWidth", dimensions.get("cardWidth") + 40* (dimensions.get("cardWidth") - dimensions.get("cornerWidth")));
        dimensions.put("boardHeight", dimensions.get("cardHeight") + 40 * (dimensions.get("cardHeight") - dimensions.get("cornerHeight")));
        dimensions.put("initialCardLayoutX", (dimensions.get("boardWidth") - dimensions.get("cardWidth")) / 2);
        dimensions.put("initialCardLayoutY", (dimensions.get("boardHeight") - dimensions.get("cardHeight")) / 2);
        dimensions.put("xOffsetForPlayerColorToken", 18.4);
        dimensions.put("yOffsetForPlayerColorToken", 19.55);
        dimensions.put("xOffsetForFirstPlayerToken", 52.9);
        dimensions.put("yOffsetForFirstPlayerToken", dimensions.get("yOffsetForPlayerColorToken"));
        dimensions.put("tokenDimension", 23.0);
    }

    /**
     * Method used to scale the dimensions of the cards for testing
     */
    public static void scaleAllDimensions(double scaleFactor) {
        dimensions.replaceAll((key, value) -> value * scaleFactor);
    }


    /**
     * Sets the characteristics of labels in scenes
     * @param label label
     * @param fontStyle font style wanted for label
     * @param fontDimension font dimension
     * @param layoutX layout X position of label in scene
     * @param layoutY layout Y position of label in scene
     */
    protected static void setLabelCharacteristics(Label label, String fontStyle, int fontDimension, double layoutX, double layoutY){
        label.setFont(new Font(fontStyle, fontDimension));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
    }

    /**
     * Sets the characteristics of buttons in scenes
     * @param button button
     * @param fontStyle font style wanted for button
     * @param fontDimension font dimension
     * @param layoutX layout X position of button in scene
     * @param layoutY layout Y position of button in scene
     * @param setDisable sets button as disabled or enabled
     */
    protected static void setButtonCharacteristics(Button button, String fontStyle, int fontDimension, double layoutX, double layoutY, boolean setDisable){
        button.setFont(new Font(fontStyle, fontDimension));
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setMnemonicParsing(false);
        button.setDisable(setDisable);
    }
}
