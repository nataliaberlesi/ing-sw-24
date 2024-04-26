package it.polimi.ingsw.Server.Model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    private static final boolean facingDown=false;
    private static final boolean facingUp=true;
    private Board board1, board2;
    @BeforeEach
    void setUp(){
        board2 =new Board("S5", facingUp);
        board1 =new Board("S4",facingUp);
        // "O04"=new VerticalPatternObjective(Symbol.MUSHROOM)); "O08"=new SymbolObjective(Symbol.MUSHROOM)); "O13"=
        String[] objectives2=new String[]{"O04", "O08", "O13"};
        // "011"=new SymbolObjective(Symbol.BUTTERFLY)  "O02"=new DiagonalPatternObjective(Symbol.WOLF)  "O07"=new VerticalPatternObjective(Symbol.BUTTERFLY)
        String[] objectives1=new String[]{"O11", "O02", "O07"};
        for(String objective:objectives1){
            board1.addObjective(objective);
        }
        for(String objective:objectives2){
            board2.addObjective(objective);
        }
        board1.placeCard("RP3", new Coordinates(1,1), facingDown);
        board1.placeCard("RR1", new Coordinates(0,2), facingDown);
        board1.placeCard("GP9", new Coordinates(1,3), facingDown);
        board1.placeCard("RB6", new Coordinates(0,4), facingDown);
    }

    @Test
    void placingAllFaceDownCardsReturnsZeroPoints(){
        assertEquals(0, board1.getScore());
    }

    @Test
    void oneVerticalPatternAndOneThreeOfAKindReturnsFivePoints(){
        assertEquals(5, board1.getFinalScore());
    }

    @Test
    void placingCardInLegalPositionReturnsTrue(){
        assertTrue(board1.placeCard("RG3",new Coordinates(2,2),facingUp));
    }

    @Test
    void placingEightDiagonalPurpleCardsAndHavingTenVisibleButterfliesReturnsAndOneVerticalPatternReturnsNine(){
        board1.placeCard("RB0",new Coordinates(2,2),facingDown);
        board1.placeCard("RB1",new Coordinates(3,3),facingDown);
        board1.placeCard("RB2",new Coordinates(4,4),facingDown);
        board1.placeCard("GB3",new Coordinates(5,5),facingDown);
        board1.placeCard("GB6",new Coordinates(6,6),facingDown);
        board1.placeCard("GB0",new Coordinates(7,7),facingDown);
        board1.placeCard("RB9",new Coordinates(8,8),facingDown);
        board1.placeCard("RB9",new Coordinates(9,9),facingDown);
        assertEquals(9, board1.getFinalScore());
    }

    //returns false
    private boolean placeFirstCard(){
        return board2.placeCard("GG9",new Coordinates(-1,1), facingUp);
    }

    //returns true and adds 1 point
    private boolean placeSecondCard(){
        return board2.placeCard("RP8",new Coordinates(1,1), facingUp);
    }

    //returns true
    private boolean placeThirdCard(){
        return board2.placeCard("RP1",new Coordinates(2,0), facingUp);
    }

    // returns false
    private boolean placeFourthCard(){
        return board2.placeCard("RG4",new Coordinates(1,1), facingDown);
    }

    // returns true
    private boolean placeFifthCard(){
        return board2.placeCard("GR9",new Coordinates(3,1), facingDown);
    }

    //returns true
    private boolean placeSixthCard(){
        return board2.placeCard("GG0",new Coordinates(4,2), facingDown);
    }

    //returns false
    private boolean placeSeventhCard(){
        return board2.placeCard("GP4",new Coordinates(5,1), facingUp);
    }

    @Test
    void playFirstRound(){
        assertFalse(placeFirstCard());
    }

    @Test
    void playSecondRound(){
        placeFirstCard();
        assertTrue(this::placeSecondCard);
    }
    @Test
    void playThirdRound(){
        placeFirstCard();
        placeSecondCard();
        assertTrue(placeThirdCard());
    }
    @Test
    void playFourthRound(){
        placeFirstCard();
        placeSecondCard();
        placeThirdCard();
        assertFalse(placeFourthCard());
    }
    @Test
    void playFifthRound(){
        placeFirstCard();
        placeSecondCard();
        placeThirdCard();
        placeFourthCard();
        assertTrue(placeFifthCard());
    }
    @Test
    void playSixthRound(){
        placeFirstCard();
        placeSecondCard();
        placeThirdCard();
        placeFourthCard();
        placeFifthCard();
        assertTrue(placeSixthCard());
    }
    @Test
    void playSeventhRound(){
        placeFirstCard();
        placeSecondCard();
        placeThirdCard();
        placeFourthCard();
        placeFifthCard();
        placeSixthCard();
        assertFalse(placeSeventhCard());
    }

    @Test
    void endOfSeventhsRoundScoreEqualsOnePoint(){
        placeFirstCard();
        placeSecondCard();
        placeThirdCard();
        placeFourthCard();
        placeFifthCard();
        placeSixthCard();
        placeSeventhCard();
        assertEquals(1,board2.getScore());
    }





}