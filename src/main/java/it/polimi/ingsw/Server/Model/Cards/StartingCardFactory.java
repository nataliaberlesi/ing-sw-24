package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * makes all starting cards or a specific starting card or just the complete list of starting card IDs.
 */
public class StartingCardFactory {

    /**
     * map that returns a starting card corresponding to a given ID
     */
    private static final HashMap<String, Supplier<StartingCard>> startingCardMaker= new HashMap<>(){{

        put("S0",()->new StartingCard("S0",new Symbol[]{Symbol.LEAF,Symbol.BLANK,Symbol.BUTTERFLY,Symbol.BLANK},
                new Symbol[]{Symbol.LEAF,Symbol.MUSHROOM,Symbol.BUTTERFLY,Symbol.WOLF},new ArrayList<>(List.of(Symbol.BUTTERFLY))));

        put("S1",()->new StartingCard("S1",new Symbol[]{Symbol.BLANK,Symbol.WOLF,Symbol.BLANK,Symbol.MUSHROOM},
                new Symbol[]{Symbol.WOLF,Symbol.LEAF,Symbol.MUSHROOM,Symbol.BUTTERFLY},new ArrayList<>(List.of(Symbol.MUSHROOM))));

        put("S2",()->new StartingCard("S2",new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.BLANK,Symbol.BLANK},
                new Symbol[]{Symbol.WOLF,Symbol.BUTTERFLY,Symbol.MUSHROOM,Symbol.LEAF},new ArrayList<>(List.of(Symbol.MUSHROOM,Symbol.LEAF))));

        put("S3",()->new StartingCard("S3",new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.BLANK,Symbol.BLANK},
                new Symbol[]{Symbol.BUTTERFLY,Symbol.LEAF,Symbol.WOLF,Symbol.MUSHROOM},new ArrayList<>(List.of(Symbol.BUTTERFLY,Symbol.WOLF))));

        put("S4",()->new StartingCard("S4",new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.FULL},
                new Symbol[]{Symbol.MUSHROOM,Symbol.BUTTERFLY,Symbol.LEAF,Symbol.WOLF},new ArrayList<>(List.of(Symbol.BUTTERFLY,Symbol.WOLF,Symbol.LEAF))));

        put("S5",()->new StartingCard("S5",new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.FULL},
                new Symbol[]{Symbol.WOLF,Symbol.MUSHROOM,Symbol.LEAF,Symbol.BUTTERFLY},new ArrayList<>(List.of(Symbol.LEAF,Symbol.WOLF,Symbol.MUSHROOM))));

    }};

    /**
     *
     * @param cardID of starting card
     * @return starting card
     * @throws IllegalArgumentException if there is no card corresponding to the ID
     */
    public static StartingCard makeStartingCard(String cardID) throws IllegalArgumentException {
        Supplier<StartingCard> cardSupplier= startingCardMaker.get(cardID);
        if(cardSupplier!=null){
            return cardSupplier.get();
        }
        else{
            throw new IllegalArgumentException(cardID+" Card not found");
        }
    }

    /**
     *
     * @return list of every starting card ID
     */
    public static ArrayList<String> makeEveryStartingCardID(){
        String startingCardPrefix="G";
        ArrayList<String> everyStartingCardID = new ArrayList<>();
        for(int i=0; i<6; i++){
            everyStartingCardID.add(startingCardPrefix+i);
        }
        return everyStartingCardID;
    }
}
