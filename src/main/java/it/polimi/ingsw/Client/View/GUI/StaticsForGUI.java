package it.polimi.ingsw.Client.View.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

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
    /**
     * Corner fixed width
     */
    protected static final double cornerWidth = 19.5;
    /**
     * Corner fixed height
     */
    protected static final double cornerHeight = 23.0;

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
