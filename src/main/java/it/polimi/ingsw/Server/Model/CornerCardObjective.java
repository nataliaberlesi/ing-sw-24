package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class CornerCardObjective implements CardObjective{

    private final static int points=2;

    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }
}
