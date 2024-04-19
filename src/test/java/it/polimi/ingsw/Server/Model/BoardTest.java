package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Objectives.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    private static final boolean facingDown=false;
    private static final boolean facingUp=true;
    private Board board;
    @BeforeEach
    void setUp(){
        board=new Board("S4",facingUp);
        Objective[] objectives=new Objective[]{new SymbolObjective(Symbol.BUTTERFLY), new DiagonalPatternObjective(Symbol.WOLF), new VerticalPatternObjective(Symbol.BUTTERFLY)};
        for(Objective objective:objectives){
            board.addObjective(objective);
        }
        board.placeCard("RP3", new Coordinates(1,1), facingDown);
        board.placeCard("RR1", new Coordinates(0,2), facingDown);
        board.placeCard("GP9", new Coordinates(1,3), facingDown);
        board.placeCard("RB6", new Coordinates(0,4), facingDown);
    }

    @Test
    void placingAllFaceDownCardsReturnsZeroPoints(){
        assertEquals(0,board.getScore());
    }

    @Test
    void oneVerticalPatternAndOneThreOfAKindReturnsFivePoints(){
        assertEquals(5,board.getFinalScore());
    }

    @Test
    void placingCardInLegalPositionReturnsTrue(){
        assertTrue(board.placeCard("RG3",new Coordinates(2,2),facingUp));
    }

    @Test
    void placingEightDiagonalPurpleCardsAndHavingTenVisibleButterfliesReturnsAndOneVerticalPatternReturnsNine(){
        board.placeCard("RB0",new Coordinates(2,2),facingDown);
        board.placeCard("RB1",new Coordinates(3,3),facingDown);
        board.placeCard("RB2",new Coordinates(4,4),facingDown);
        board.placeCard("GB3",new Coordinates(5,5),facingDown);
        board.placeCard("GB6",new Coordinates(6,6),facingDown);
        board.placeCard("GB0",new Coordinates(7,7),facingDown);
        board.placeCard("RB9",new Coordinates(8,8),facingDown);
        board.placeCard("RB9",new Coordinates(9,9),facingDown);
        assertEquals(9,board.getFinalScore());
    }



}