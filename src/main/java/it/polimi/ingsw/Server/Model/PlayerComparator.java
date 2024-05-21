package it.polimi.ingsw.Server.Model;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        if(p1.getPlayerFinalScore()!=p2.getPlayerFinalScore()) {
            return Integer.compare(p1.getPlayerFinalScore(),p2.getPlayerFinalScore())*-1;
        }
        return Integer.compare(p1.getPlayerObjectivesScore(), p2.getPlayerObjectivesScore())*-1;
    }
}
