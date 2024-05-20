package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

public class Scoreboard {
    private ArrayList<Player> scoreboard=new ArrayList<Player>();
    public void addPlayer(Player player) {
        this.scoreboard.add(player);
    }
    public void sortScoreboard() {
        this.scoreboard.sort(new PlayerComparator());
    }
    public void orderPositions() {
        for (int i = 0; i < this.scoreboard.size(); i++) {
            if (i > 0 && this.scoreboard.get(i).getPlayerFinalScore() == this.scoreboard.get(i - 1).getPlayerFinalScore() &&
                    this.scoreboard.get(i).getPlayerObjectivesScore() == this.scoreboard.get(i - 1).getPlayerObjectivesScore()) {
                this.scoreboard.get(i).setPlayerPosition(this.scoreboard.get(i - 1).getPlayerPosition());
            } else if(i==0){
                this.scoreboard.get(i).setPlayerPosition(1);
            }else {
                this.scoreboard.get(i).setPlayerPosition(this.scoreboard.get(i - 1).getPlayerPosition()+1);
            }
        }
    }

    public ArrayList<Player> getScoreboard() {
        return scoreboard;
    }
}
