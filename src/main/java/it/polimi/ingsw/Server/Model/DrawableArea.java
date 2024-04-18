package it.polimi.ingsw.Server.Model;

/**
 * area that contains all the cards that can be drawn by a player
 */
public class DrawableArea {

    private final DrawableCards goldDrawableCards;

    private final DrawableCards resourceDrawableCards;


    public DrawableArea(DrawableCards resourceDrawableCards, DrawableCards goldDrawableCards){
        this.resourceDrawableCards = resourceDrawableCards;
        this.goldDrawableCards = goldDrawableCards;
    }


    public DrawableCards getGoldDrawingSection() {
        return goldDrawableCards;
    }

    public DrawableCards getResourceDrawingSection() {
        return resourceDrawableCards;
    }
}
