package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public class CornerCardObjective implements CardObjective{

    private final static int points=2;
    @Override
    public int calculatePoints(ArrayList<Symbol> symbolCounter, int coveredCorners) {
        return 0;
    }
}
