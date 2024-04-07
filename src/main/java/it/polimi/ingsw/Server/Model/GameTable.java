package it.polimi.ingsw.Server.Model;

import java.util.ArrayList;

/**
 * GameTable where boards are set as well as drawing sections.
 */
public class GameTable {

    private final DrawingSection goldDrawingSection;

    private final DrawingSection resourceDrawingSection;

    //maybe unnecessary
    private ArrayList<Board> boards;

    public GameTable(DrawingSection resourceDrawingSection, DrawingSection goldDrawingSection){
        this.resourceDrawingSection=resourceDrawingSection;
        this.goldDrawingSection=goldDrawingSection;
    }

    public void addBoard(Board board) throws RuntimeException{
        if(boards.size()>=4){
            throw new RuntimeException("too many boards are being added to a table");
        }
        boards.add(board);
    }

    public DrawingSection getGoldDrawingSection() {
        return goldDrawingSection;
    }

    public DrawingSection getResourceDrawingSection() {
        return resourceDrawingSection;
    }
}
