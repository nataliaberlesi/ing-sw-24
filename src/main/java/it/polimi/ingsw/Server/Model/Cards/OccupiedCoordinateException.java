package it.polimi.ingsw.Server.Model.Cards;

/**
 * thrown when two cards are placed in same coordinates
 */
public class OccupiedCoordinateException extends RuntimeException{
    public OccupiedCoordinateException(String message){
        super(message);
    }
}
