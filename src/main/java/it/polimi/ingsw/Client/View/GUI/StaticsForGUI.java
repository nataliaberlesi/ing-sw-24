package it.polimi.ingsw.Client.View.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class StaticsForGUI {
    protected static void setLabelCharacteristics(Label label, String fontStyle, int fontDimention, double layoutX, double layoutY){
        label.setFont(new Font(fontStyle, fontDimention));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
    }

}
