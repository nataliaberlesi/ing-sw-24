package it.polimi.ingsw.Server.Model.Cards;

public class OccupiedCoordinateException extends RuntimeException{
    public OccupiedCoordinateException(String message){
        super(message);
    }
}
