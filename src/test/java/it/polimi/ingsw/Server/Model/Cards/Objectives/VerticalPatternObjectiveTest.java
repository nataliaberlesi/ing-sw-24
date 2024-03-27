package it.polimi.ingsw.Server.Model.Cards.Objectives;

import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerticalPatternObjectiveTest {

    private VerticalPatternObjective createColumnWith(Symbol verticalSymbol){
        Coordinates bottomOfColumn=new Coordinates();
        Coordinates topOfColumn=new Coordinates(0,2);
        VerticalPatternObjective vpo=new VerticalPatternObjective(verticalSymbol);
        vpo.updateObjective(verticalSymbol, bottomOfColumn);
        vpo.updateObjective(verticalSymbol, topOfColumn);
        return vpo;
    }

    private VerticalPatternObjective createLongOverlappingPattern(Symbol verticalSymbol,Symbol oddSymbol){
        VerticalPatternObjective vpo=new VerticalPatternObjective(verticalSymbol);
        fillVerticalCoordinates(vpo,verticalSymbol);
        fillOddCoordinates(vpo,oddSymbol);
        return vpo;
    }

    private void fillVerticalCoordinates(VerticalPatternObjective vpo, Symbol verticalSymbol){
        for(int i=0; i<7; i++){
            Coordinates verticalCoordinates=new Coordinates(0, i*2);
            vpo.updateObjective(verticalSymbol, verticalCoordinates);
        }
    }

    private void fillOddCoordinates(VerticalPatternObjective vpo, Symbol oddSymbol){
        for(int i=0; i<6; i++){
            Coordinates leftOddCoordinates=new Coordinates(-1, i*2+1);
            Coordinates rightOddCoordinates=new Coordinates(1, i*2+1);
            vpo.updateObjective(oddSymbol, leftOddCoordinates);
            vpo.updateObjective(oddSymbol, rightOddCoordinates);
        }
    }

    /**
     *      W
     *        B
     *        B
     *    3 points
     */
    @Test
    void oneOccurrenceVerticalPatternPurpleReturnsThree(){
            Symbol verticalSymbol=Symbol.BUTTERFLY;
            Objective vpo=createColumnWith(verticalSymbol);
            Coordinates topLeft=new Coordinates(-1,3);
            vpo.updateObjective(Symbol.WOLF, topLeft );
            assertEquals(3,vpo.calculatePoints(null));
    }

    /**
     *        M
     *        M
     *          L
     *    3 points
     */
    @Test
    void oneOccurrenceVerticalPatternRedReturnsThree(){
        Symbol verticalSymbol=Symbol.MUSHROOM;
        Objective vpo=createColumnWith(verticalSymbol);
        Coordinates bottomRight=new Coordinates(1,-1);
        vpo.updateObjective(Symbol.LEAF, bottomRight );
        assertEquals(3,vpo.calculatePoints(null));

    }
    /**
     *          M
     *        W
     *        W
     *    3 points
     */
    @Test
    void oneOccurrenceVerticalPatternBlueReturnsThree(){
        Symbol verticalSymbol=Symbol.WOLF;
        Objective vpo=createColumnWith(verticalSymbol);
        Coordinates topRight=new Coordinates(1,3);
        vpo.updateObjective(Symbol.MUSHROOM, topRight );
        assertEquals(3,vpo.calculatePoints(null));

    }
    /**
     *        L
     *        L
     *      B
     *    3 points
     */
    @Test
    void oneOccurrenceVerticalPatternGreenReturnsThree(){
        Symbol verticalSymbol=Symbol.LEAF;
        Objective vpo=createColumnWith(verticalSymbol);
        Coordinates bottomLeft=new Coordinates(-1,-1);
        vpo.updateObjective(Symbol.BUTTERFLY, bottomLeft );
        assertEquals(3,vpo.calculatePoints(null));
    }

    @Test
    void threeOverlappingPatternsReturnNineRed(){
        Symbol verticalSymbol=Symbol.MUSHROOM;
        Symbol oddSymbol=Symbol.LEAF;
        VerticalPatternObjective vpo=createLongOverlappingPattern(verticalSymbol, oddSymbol);
        assertEquals(9,vpo.calculatePoints(null));
    }
    @Test
    void threeOverlappingPatternsReturnNineBlue(){
        Symbol verticalSymbol=Symbol.WOLF;
        Symbol oddSymbol=Symbol.MUSHROOM;
        VerticalPatternObjective vpo=createLongOverlappingPattern(verticalSymbol, oddSymbol);
        assertEquals(9,vpo.calculatePoints(null));
    }
    @Test
    void threeOverlappingPatternsReturnNineGreen(){
        Symbol verticalSymbol=Symbol.LEAF;
        Symbol oddSymbol=Symbol.BUTTERFLY;
        VerticalPatternObjective vpo=createLongOverlappingPattern(verticalSymbol, oddSymbol);
        assertEquals(9,vpo.calculatePoints(null));
    }
    @Test
    void threeOverlappingPatternsReturnNinePurple(){
        Symbol verticalSymbol=Symbol.BUTTERFLY;
        Symbol oddSymbol=Symbol.WOLF;
        VerticalPatternObjective vpo=createLongOverlappingPattern(verticalSymbol, oddSymbol);
        assertEquals(9,vpo.calculatePoints(null));
    }
}