package it.polimi.ingsw.Client.View.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class StaticsForGUI {
    protected static boolean enableActions = false;
    protected static boolean atLeastOneCornerSelected = false;
    protected static void setLabelCharacteristics(Label label, String fontStyle, int fontDimension, double layoutX, double layoutY){
        label.setFont(new Font(fontStyle, fontDimension));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
    }
    protected static void setButtonCharacteristics(Button button, String fontStyle, int fontDimension, double layoutX, double layoutY, boolean setDisable){
        button.setFont(new Font(fontStyle, fontDimension));
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setMnemonicParsing(false);
        button.setDisable(setDisable);
    }

}
