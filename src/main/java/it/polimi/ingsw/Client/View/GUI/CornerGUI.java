package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CornerGUI extends Region {
    protected boolean isSelected = false;
    protected static final double cornerWidth = 19.5;
    protected static final double cornerHeight = 23.0;
    protected Coordinates cornerCoordinates;


    public CornerGUI(){
        setPrefSize(cornerWidth, cornerHeight);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }



    public boolean toggleSelection(CornerGUI corner, BoardGUI board) {
        for (CardGUI cardInBoard : board.getCardsOnBoard()){ //deactivates selection of corner
            for (CornerGUI cornerInCard : cardInBoard.getCorners()) {
                if (cornerInCard.isSelected && !cornerInCard.equals(corner)) {
                    cornerInCard.setBorder(null);
                    cornerInCard.isSelected = false;
                }
            }
        }

        if ((corner.getBorder() == null || corner.getBorder().getStrokes().isEmpty()) && !corner.isSelected) {
            corner.setBorder(new Border(new BorderStroke(
                    Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(3)
            )));
            corner.isSelected = true;
            StaticsForGUI.atLeastOneCornerSelected = true;
        } else {
            corner.setBorder(null);
            corner.isSelected = false;
            StaticsForGUI.atLeastOneCornerSelected = false;
        }
        return StaticsForGUI.atLeastOneCornerSelected;
    }


}
