package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public interface CardObjective {
    public int calculatePoints(ArrayList<Symbol> symbolCounter, int coveredCorners );
}
