package it.polimi.ingsw.Server.Model;

public abstract class Card {

    private final Symbol backSymbol;

    private boolean isFacingeUp = true;

    private final Symbol[] frontCorners=new Symbol[4];

    private final Symbol[] backCorners;

    public void flipCard(){
        isFacingeUp=!isFacingeUp;
    }

    public Symbol[] getVisibleCorners(){
        if(isFacingeUp){
            return frontCorners;
        }
        return backCorners;
    }

    public Symbol[] getBackCorners() {
        return backCorners;
    }

    public Symbol[] getFrontCorners() {
        return frontCorners;
    }

    public Symbol getBackSymbol() {
        return backSymbol;
    }

    public boolean isFacingeUp() {
        return isFacingeUp;
    }

}
