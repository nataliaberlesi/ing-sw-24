package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.PointsCardObjective;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * makes all resource cards or a specific resource card or just the complete list of resource IDs.
 */
public class ResourceCardFactory {

    /**
     * these are the only objectives resource cards have
     */
    private final static CardObjective giveOnePointWhenPlaced=new PointsCardObjective(1),
                         giveZeroPointsWhenPlaced=new PointsCardObjective(0);


    /**
     * map that returns a resource card corresponding to a given ID
     */
    private static final HashMap<String, Supplier<ResourceCard>> resourceCardMaker = new HashMap<>(){{
        //red resource cards
        put("RR0",()->new ResourceCard("RR0",Symbol.MUSHROOM, new Symbol[]{Symbol.BLANK, Symbol.MUSHROOM, Symbol.MUSHROOM, Symbol.FULL}, giveZeroPointsWhenPlaced));
        put("RR1",()->new ResourceCard("RR1",Symbol.MUSHROOM, new Symbol[]{Symbol.MUSHROOM, Symbol.MUSHROOM, Symbol.FULL, Symbol.BLANK}, giveZeroPointsWhenPlaced));
        put("RR2",()->new ResourceCard("RR2",Symbol.MUSHROOM, new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.MUSHROOM, Symbol.MUSHROOM}, giveZeroPointsWhenPlaced));
        put("RR3",()->new ResourceCard("RR3",Symbol.MUSHROOM, new Symbol[]{Symbol.MUSHROOM, Symbol.FULL, Symbol.BLANK, Symbol.MUSHROOM}, giveZeroPointsWhenPlaced));
        put("RR4",()->new ResourceCard("RR4",Symbol.MUSHROOM, new Symbol[]{Symbol.FEATHER, Symbol.FULL, Symbol.LEAF, Symbol.MUSHROOM}, giveZeroPointsWhenPlaced));
        put("RR5",()->new ResourceCard("RR5",Symbol.MUSHROOM, new Symbol[]{Symbol.MUSHROOM, Symbol.INK, Symbol.FULL, Symbol.WOLF}, giveZeroPointsWhenPlaced));
        put("RR6",()->new ResourceCard("RR6",Symbol.MUSHROOM, new Symbol[]{Symbol.BUTTERFLY, Symbol.MUSHROOM, Symbol.SCROLL, Symbol.BLANK}, giveZeroPointsWhenPlaced));
        put("RR7",()->new ResourceCard("RR7",Symbol.MUSHROOM, new Symbol[]{Symbol.MUSHROOM, Symbol.BLANK, Symbol.BLANK, Symbol.FULL}, giveOnePointWhenPlaced));
        put("RR8",()->new ResourceCard("RR8",Symbol.MUSHROOM, new Symbol[]{Symbol.FULL, Symbol.MUSHROOM, Symbol.BLANK, Symbol.BLANK}, giveOnePointWhenPlaced));
        put("RR9",()->new ResourceCard("RR9",Symbol.MUSHROOM, new Symbol[]{Symbol.BLANK, Symbol.FULL, Symbol.MUSHROOM, Symbol.BLANK}, giveOnePointWhenPlaced));
        //green resource cards
        put("RG0",()->new ResourceCard("RG0",Symbol.LEAF, new Symbol[]{Symbol.BLANK,Symbol.LEAF,Symbol.LEAF,Symbol.FULL},giveZeroPointsWhenPlaced));
        put("RG1",()->new ResourceCard("RG1",Symbol.LEAF, new Symbol[]{Symbol.LEAF,Symbol.LEAF,Symbol.FULL,Symbol.BLANK},giveZeroPointsWhenPlaced));
        put("RG2",()->new ResourceCard("RG2",Symbol.LEAF, new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.LEAF,Symbol.LEAF},giveZeroPointsWhenPlaced));
        put("RG3",()->new ResourceCard("RG3",Symbol.LEAF, new Symbol[]{Symbol.LEAF,Symbol.FULL,Symbol.BLANK,Symbol.LEAF},giveZeroPointsWhenPlaced));
        put("RG4",()->new ResourceCard("RG4",Symbol.LEAF, new Symbol[]{Symbol.BUTTERFLY,Symbol.FULL,Symbol.FEATHER,Symbol.LEAF},giveZeroPointsWhenPlaced));
        put("RG5",()->new ResourceCard("RG5",Symbol.LEAF, new Symbol[]{Symbol.LEAF,Symbol.MUSHROOM,Symbol.FULL,Symbol.INK},giveZeroPointsWhenPlaced));
        put("RG6",()->new ResourceCard("RG6",Symbol.LEAF, new Symbol[]{Symbol.FULL,Symbol.SCROLL,Symbol.LEAF,Symbol.WOLF},giveZeroPointsWhenPlaced));
        put("RG7",()->new ResourceCard("RG7",Symbol.LEAF, new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.LEAF,Symbol.FULL},giveOnePointWhenPlaced));
        put("RG8",()->new ResourceCard("RG8",Symbol.LEAF, new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.LEAF},giveOnePointWhenPlaced));
        put("RG9",()->new ResourceCard("RG9",Symbol.LEAF, new Symbol[]{Symbol.LEAF,Symbol.FULL,Symbol.BLANK,Symbol.BLANK},giveOnePointWhenPlaced));
        //blue resource cards
        put("RB0",()->new ResourceCard("RB0",Symbol.WOLF,new Symbol[]{Symbol.WOLF,Symbol.WOLF,Symbol.BLANK,Symbol.FULL},giveZeroPointsWhenPlaced));
        put("RB1",()->new ResourceCard("RB1",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.WOLF,Symbol.WOLF},giveZeroPointsWhenPlaced));
        put("RB2",()->new ResourceCard("RB2",Symbol.WOLF,new Symbol[]{Symbol.FULL,Symbol.WOLF,Symbol.WOLF,Symbol.BLANK},giveZeroPointsWhenPlaced));
        put("RB3",()->new ResourceCard("RB3",Symbol.WOLF,new Symbol[]{Symbol.WOLF,Symbol.BLANK,Symbol.FULL,Symbol.WOLF},giveZeroPointsWhenPlaced));
        put("RB4",()->new ResourceCard("RB4",Symbol.WOLF,new Symbol[]{Symbol.BUTTERFLY,Symbol.FULL,Symbol.INK,Symbol.WOLF},giveZeroPointsWhenPlaced));
        put("RB5",()->new ResourceCard("RB5",Symbol.WOLF,new Symbol[]{Symbol.WOLF,Symbol.LEAF,Symbol.FULL,Symbol.SCROLL},giveZeroPointsWhenPlaced));
        put("RB6",()->new ResourceCard("RB6",Symbol.WOLF,new Symbol[]{Symbol.FULL,Symbol.FEATHER,Symbol.WOLF,Symbol.MUSHROOM},giveZeroPointsWhenPlaced));
        put("RB7",()->new ResourceCard("RB7",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.WOLF,Symbol.BLANK},giveOnePointWhenPlaced));
        put("RB8",()->new ResourceCard("RB8",Symbol.WOLF,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.BLANK,Symbol.WOLF},giveOnePointWhenPlaced));
        put("RB9",()->new ResourceCard("RB9",Symbol.WOLF,new Symbol[]{Symbol.WOLF,Symbol.BLANK,Symbol.BLANK,Symbol.FULL},giveOnePointWhenPlaced));
        //purple resource cards
        put("RP0",()->new ResourceCard("RP1",Symbol.BUTTERFLY,new Symbol[]{Symbol.BUTTERFLY,Symbol.BUTTERFLY,Symbol.BLANK,Symbol.FULL},giveZeroPointsWhenPlaced));
        put("RP1",()->new ResourceCard("RP2",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.BUTTERFLY,Symbol.BUTTERFLY},giveZeroPointsWhenPlaced));
        put("RP2",()->new ResourceCard("RP3",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL,Symbol.BUTTERFLY,Symbol.BUTTERFLY,Symbol.BLANK},giveZeroPointsWhenPlaced));
        put("RP3",()->new ResourceCard("RP4",Symbol.BUTTERFLY,new Symbol[]{Symbol.BUTTERFLY,Symbol.BLANK,Symbol.FULL,Symbol.BUTTERFLY},giveZeroPointsWhenPlaced));
        put("RP4",()->new ResourceCard("RP5",Symbol.BUTTERFLY,new Symbol[]{Symbol.FEATHER,Symbol.FULL,Symbol.WOLF,Symbol.BUTTERFLY},giveZeroPointsWhenPlaced));
        put("RP5",()->new ResourceCard("RP6",Symbol.BUTTERFLY,new Symbol[]{Symbol.BUTTERFLY,Symbol.SCROLL,Symbol.FULL,Symbol.MUSHROOM},giveZeroPointsWhenPlaced));
        put("RP6",()->new ResourceCard("RP7",Symbol.BUTTERFLY,new Symbol[]{Symbol.LEAF,Symbol.BUTTERFLY,Symbol.SCROLL,Symbol.FULL},giveZeroPointsWhenPlaced));
        put("RP7",()->new ResourceCard("RP8",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL,Symbol.BUTTERFLY,Symbol.BLANK,Symbol.BLANK},giveOnePointWhenPlaced));
        put("RP8",()->new ResourceCard("RP9",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.BUTTERFLY},giveOnePointWhenPlaced));
        put("RP9",()->new ResourceCard("RP10",Symbol.BUTTERFLY,new Symbol[]{Symbol.BUTTERFLY,Symbol.FULL,Symbol.BLANK,Symbol.BLANK},giveOnePointWhenPlaced));
    }};


    /**
     *
     * @return every cardID that can be assigned to a resourceCard
     */
    public static ArrayList<String> makeEveryResourceCardID(){
        ArrayList<String> everyResourceCardID = new ArrayList<>();
        ArrayList<String> resourceCardsPrefix=new ArrayList<>();
        resourceCardsPrefix.add("RR");
        resourceCardsPrefix.add("RG");
        resourceCardsPrefix.add("RB");
        resourceCardsPrefix.add("RP");
        for(String prefix:resourceCardsPrefix){
            for(int i=0; i<10; i++){
                everyResourceCardID.add(prefix+i);
            }
        }
        return everyResourceCardID;
    }

    /**
     *
     * @param cardID is a unique code that identifies a specific card
     * @return the card specified by the ID, if it starts wi
     */
    public static ResourceCard makeResourceCard(String cardID) throws IllegalArgumentException{
        if(cardID.startsWith("G")){
            return GoldCardFactory.makeGoldCard(cardID);
        }
        Supplier<ResourceCard> cardSupplier= resourceCardMaker.get(cardID);
        if(cardSupplier!=null){
            return cardSupplier.get();
        }
        else{
            throw new IllegalArgumentException(cardID+" Card not found");
        }
    }

    /**
     *
     * @return every resource card
     */
    private static ArrayList<ResourceCard> makeEveryResourceCard(){
        ArrayList<ResourceCard> everyResourceCard=new ArrayList<>();
        ArrayList<String> everyResourceCardID= makeEveryResourceCardID();
        for(String cardID:everyResourceCardID){
            everyResourceCard.add(makeResourceCard(cardID));
        }
        return everyResourceCard;
    }


}
