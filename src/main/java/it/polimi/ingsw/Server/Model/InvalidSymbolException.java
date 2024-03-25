package it.polimi.ingsw.Server.Model;

public class InvalidSymbolException extends RuntimeException{
    public InvalidSymbolException(String s){
        super(s);
    }
}
