package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

/**
 * Table where boards are set as well as drawing sections.
 */
public class Table {

    private final DrawingSection goldDrawingSection;

    private final DrawingSection resourceDrawingSection;

    private final ArrayList<Player> players=new ArrayList<>();


    public Table(DrawingSection resourceDrawingSection, DrawingSection goldDrawingSection){
        this.resourceDrawingSection=resourceDrawingSection;
        this.goldDrawingSection=goldDrawingSection;
    }

    public boolean addPlayer(Player player){
        if(players.size()>3||players.contains(player)){
            return false;
        }
        players.add(player);
        return true;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public DrawingSection getGoldDrawingSection() {
        return goldDrawingSection;
    }

    public DrawingSection getResourceDrawingSection() {
        return resourceDrawingSection;
    }
}
