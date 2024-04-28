package it.polimi.ingsw.Server.Model;

/**
 * area that contains all the cards that can be drawn by a player
 */
public class DrawableArea {

    /**
     * contains the gold cards that are visible to all layers and can be drawn,
     * the first card is always face down and the other two are face up
     */
    private final DrawableCards goldDrawableCards;
    /**
     * contains the resource cards that are visible to all layers and can be drawn,
     * the first card is always face down and the other two are face up
     */
    private final DrawableCards resourceDrawableCards;

    /**
     *
     * @param resourceDrawableCards are the resource cards that can be drawn
     * @param goldDrawableCards are the gold cards that can be drawn
     */
    public DrawableArea(DrawableCards resourceDrawableCards, DrawableCards goldDrawableCards){
        this.resourceDrawableCards = resourceDrawableCards;
        this.goldDrawableCards = goldDrawableCards;
    }


    /**
     *
     * @return gold cards that can be drawn
     */
    public DrawableCards getGoldDrawingSection() {
        return goldDrawableCards;
    }

    /**
     *
     * @return resource cards that can be drawn
     */
    public DrawableCards getResourceDrawingSection() {
        return resourceDrawableCards;
    }
}
