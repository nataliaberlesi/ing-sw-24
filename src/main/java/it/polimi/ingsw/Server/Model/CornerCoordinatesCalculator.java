package it.polimi.ingsw.Server.Model;

import java.util.HashMap;

public class CornerCoordinatesCalculator {
    /**
     * map that assigns to each angle of a card the increments necessary to find global coordinates
     */
    private static final HashMap<Integer, Coordinates> relativeCoordinates;

    static {
        relativeCoordinates=new HashMap<>();
        relativeCoordinates.put(0, new Coordinates(1,1));
        relativeCoordinates.put(1, new Coordinates(-1,1));
        relativeCoordinates.put(2, new Coordinates(-1,-1));
        relativeCoordinates.put(3, new Coordinates(1,-1));
    }

    /**
     * this method calculates the coordinates of a given corner of a card being placed in given coordinates
     * @param centerCoordinates coordinates of the card
     * @param cornerNumber number of the corner of the card
     * @return the coordinates of the corner
     */
    public static Coordinates cornerCoordinates(Coordinates centerCoordinates, int cornerNumber){
        int x=centerCoordinates.getX()+relativeCoordinates.get(cornerNumber).getX();
        int y=centerCoordinates.getY()+relativeCoordinates.get(cornerNumber).getY();
        return new Coordinates(x,y);
    }
}