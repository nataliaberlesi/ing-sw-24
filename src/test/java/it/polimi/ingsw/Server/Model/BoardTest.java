package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.GoldCard;
import it.polimi.ingsw.Server.Model.Cards.Objectives.*;
import it.polimi.ingsw.Server.Model.Cards.ResourceCard;
import it.polimi.ingsw.Server.Model.Cards.StartingCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    private static final Objective[] patternObjectives={new VerticalPatternObjective(Symbol.MUSHROOM),new VerticalPatternObjective(Symbol.MUSHROOM),new VerticalPatternObjective(Symbol.MUSHROOM)};
    private static final ArrayList<Symbol> frontCenterSymbols=new ArrayList<>(Arrays.asList(Symbol.MUSHROOM,Symbol.WOLF));
    private static final Symbol[] startingCardBackCorners={Symbol.MUSHROOM,Symbol.WOLF,Symbol.LEAF,Symbol.BUTTERFLY};
    private static final CardObjective onePoint= new PointsCardObjective(1);
    private static final CardObjective featherPoints= new SymbolObjective(Symbol.FEATHER);
    private static final Symbol[] frontCorners={Symbol.WOLF, Symbol.FULL, Symbol.MUSHROOM, Symbol.BLANK};
    //private static final ResourceCard rc=new ResourceCard("1",Symbol.WOLF,frontCorners,zeroPoints);
    //private static final StartingCard sc=new StartingCard("0",frontCorners,startingCardBackCorners,frontCenterSymbols);
    //private static final ResourceCard gc=new GoldCard("2",Symbol.WOLF,frontCorners,zeroPoints,frontCenterSymbols);


    @Test
    void placingStartingCardAndCalculatingPointsReturnsZero(){
        Board board=makeBoard();
        assertEquals(0, board.getScore());
    }

    @Test
    void placingResourceCardinLegalPositionReturnsTrue(){
        Board board=makeBoard();
        assertTrue(board.placeCard(makeResourceCard(),new Coordinates(1,-1)));
    }
    @Test
    void placingResourceCardAndCalculatingPointsReturnsOne(){
        Board board=makeBoard();
        ResourceCard rc=makeResourceCard();
        rc.flipCard();
        board.placeCard(rc,new Coordinates(1,-1));
        assertEquals(1, board.getScore());
    }

    @Test
    void placingResourceCardAndThenGoldCardReturnsTrue(){
        Board board=makeBoard();
        ResourceCard rc=makeResourceCard();
        ResourceCard gc=makeGoldCard();
        gc.flipCard();
        rc.flipCard();
        board.placeCard(rc,new Coordinates(1,-1));
        assertTrue(board.placeCard(gc,new Coordinates(2,0)));
    }
    @Test
    void placingResourceCardAndThenGoldCardAndCalculatePointsReturnsOne(){
        Board board=makeBoard();
        ResourceCard rc=makeResourceCard();
        ResourceCard gc=makeGoldCard();
        gc.flipCard();
        rc.flipCard();
        board.placeCard(rc,new Coordinates(1,-1));
        board.placeCard(gc,new Coordinates(2,0));
        assertEquals(1, board.getScore());
    }

    @Test
    void placingCardInIllegalPositionReturnsFalse(){
        Board board=makeBoard();
        assertFalse(board.placeCard(makeResourceCard(),new Coordinates(-1,1)));
    }

    @Test
    void insufficientVisibleSymbolsWhenPlacingGoldCardReturnsFalse(){
        Board board=makeBoard();
        ArrayList<Symbol> butterflyPrerequisites=new ArrayList<>(List.of(Symbol.BUTTERFLY));
        ResourceCard gdb=new GoldCard("3", Symbol.WOLF, frontCorners, onePoint, butterflyPrerequisites);
        gdb.flipCard();
        assertFalse(board.placeCard(gdb, new Coordinates(1,-1)));
    }

    private Board makeBoard(){
        StartingCard sc=new StartingCard("0",frontCorners,startingCardBackCorners,frontCenterSymbols);
        sc.flipCard();
        Board board= new Board(sc);
        for(Objective o: patternObjectives){
            board.addObjective(o);
        }
        return board;
    }


    private ResourceCard makeResourceCard(){
        return new ResourceCard("1",Symbol.WOLF,frontCorners,onePoint);
    }

    private ResourceCard makeGoldCard(){
        return new GoldCard("2",Symbol.WOLF,frontCorners,featherPoints,frontCenterSymbols);
    }
}