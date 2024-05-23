package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CornerGUI extends Region {
    protected boolean isSelected = false;
    protected static final double cornerWidth = 19.5;
    protected static final double cornerHeight = 23.0;
    protected Coordinates cornerCoordinates;
    protected static boolean atLeastOneCornerSelected;

    public CornerGUI(){
        setPrefSize(cornerWidth, cornerHeight);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    public boolean toggleSelection(CornerGUI corner, BoardGUI board, MainScene mainScene) {
        atLeastOneCornerSelected = false;
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
                    Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5)
            )));
            corner.isSelected = true;
            atLeastOneCornerSelected = true;
        } else {
            corner.setBorder(null);
            corner.isSelected = false;
        }
        return atLeastOneCornerSelected;
    }

}
