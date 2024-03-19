package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public class PointsCardObjective implements CardObjective{

    private final int points;

    public PointsCardObjective(int points) {
        this.points = points;
    }

    @Override
    public int calculatePoints(ArrayList<Symbol> symbolCounter, int coveredCorners) {
        return 0;
    }
}
