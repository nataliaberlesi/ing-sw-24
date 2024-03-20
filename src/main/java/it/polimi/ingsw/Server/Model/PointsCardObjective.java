package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class PointsCardObjective implements CardObjective{

    private final int points;

    public PointsCardObjective(int points) {
        this.points = points;
    }


    @Override
    public int calculatePoints(HashMap<Symbol, Integer> symbolCounter, int coveredCorners) {
        return 0;
    }
}
