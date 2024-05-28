package it.polimi.ingsw.Client.View.GUI;

import it.polimi.ingsw.Server.Model.Coordinates;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Region that represents corners in cards
 */
public class CornerGUI extends Region {
    /**
     * Corner's coordinates in model
     */
    protected Coordinates cornerCoordinates;
    /**
     * Indicates if a corner is selected or not
     */
    protected boolean isSelected = false;

    /**
     * Constructor for corners
     */
    public CornerGUI(){
        setPrefSize(StaticsForGUI.cornerWidth, StaticsForGUI.cornerHeight);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Toggle selection for board card's corners
     * @param corner selected corner
     * @param board board
     */

    protected void toggleSelection(CornerGUI corner, BoardGUI board) {
        for (CardGUI cardInBoard : board.getCardsOnBoard()){ //if there is already another corner selected in board cards, unselect it
            for (CornerGUI cornerInCard : cardInBoard.getCorners()) {
                if (cornerInCard.isSelected && !cornerInCard.equals(corner)) {
                    cornerInCard.setBorder(null);
                    cornerInCard.isSelected = false;
                }
            }
        }
        addBorderAndSelectCorner(corner);
    }

    /**
     * Adds (or removes) red border to/from selected corner and marks it as selected/unselected.
     * Also keeps track if at least one corner is selected for board cards
     * @param corner selected corner
     */
    private void addBorderAndSelectCorner(CornerGUI corner){
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
    }

}
