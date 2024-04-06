package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StartingCardTest {

    private static final Symbol[] frontCorners={Symbol.FULL, Symbol.BUTTERFLY,Symbol.INK,Symbol.MUSHROOM};
    private static final Symbol[] backCorners={Symbol.LEAF, Symbol.BUTTERFLY,Symbol.WOLF,Symbol.MUSHROOM};
    private static final ArrayList<Symbol> frontCenterSymbols= new ArrayList<>(Arrays.asList(Symbol.MUSHROOM,Symbol.BUTTERFLY,Symbol.WOLF));

    @Test
    void normalParametersDontThrowException(){
        assertDoesNotThrow(()->new StartingCard("0",frontCorners,backCorners,frontCenterSymbols));
    }

    @Test
    void emptyFrontCenterSymbolsThrowsExceptionInConstructor(){
        assertThrows(IllegalArgumentException.class,()->new StartingCard("0", frontCorners, backCorners,new ArrayList<>()));
    }

    @Test
    void fourFrontCenterSymbolsThrowsExceptionInConstructor(){
        assertThrows(IllegalArgumentException.class,()->new StartingCard("0", frontCorners, backCorners,new ArrayList<>(4)));
    }


    @Test
    void wrongFrontCenterSymbolsThrowsExceptionInConstructor(){
        ArrayList<Symbol> wrongFrontCenterSymbols=new ArrayList<>(frontCenterSymbols);
        wrongFrontCenterSymbols.set(0,Symbol.INK);
        assertThrows(InvalidSymbolException.class,()->new StartingCard("0", frontCorners, backCorners,wrongFrontCenterSymbols));
    }

    @Test
    void goldenSymbolInBackCornersThrowsExceptionInConstructor(){
        Symbol[] wrongBackCornersSymbols=backCorners.clone();
        wrongBackCornersSymbols[0]=Symbol.FEATHER;
        assertThrows(InvalidSymbolException.class,()->new StartingCard("0", frontCorners, wrongBackCornersSymbols,frontCenterSymbols));
    }

    @Test
    void repeatingFrontCenterSymbolsThrowsExceptionInConstructor(){
        ArrayList<Symbol> repeatingFrontCenterSymbols=new ArrayList<>(frontCenterSymbols);
        repeatingFrontCenterSymbols.set(0,Symbol.WOLF);
        assertThrows(IllegalArgumentException.class,()->new StartingCard("0", frontCorners, backCorners,repeatingFrontCenterSymbols));
    }

    @Test
    void repeatingSymbolInBackCornersThrowsExceptionInConstructor(){
        Symbol[] repeatingBackCornersSymbols=backCorners.clone();
        repeatingBackCornersSymbols[0]=Symbol.MUSHROOM;
        assertThrows(IllegalArgumentException.class,()->new StartingCard("0", frontCorners, repeatingBackCornersSymbols,frontCenterSymbols));
    }


}