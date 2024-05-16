package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.CornerCardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.PointsCardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.SymbolObjective;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * makes all gold cards or a specific gold card or just the complete list of gold IDs.
 */
public class GoldCardFactory {

    /**
     * these are the only objectives that gold cards may have
     */
    private final static CardObjective threePointsCardObjective=new PointsCardObjective(3);
    private final static CardObjective fivePointsCardObjective=new PointsCardObjective(5);
    private final static CardObjective cornersCardObjective=new CornerCardObjective();
    private final static CardObjective featherCardObjective=new SymbolObjective(Symbol.FEATHER);
    private final static CardObjective inkCardObjective=new SymbolObjective(Symbol.INK);
    private final static CardObjective scrollCardObjective=new SymbolObjective(Symbol.SCROLL);

    /**
     * these are prerequisites of the gold cards
     */
    private final static ArrayList<Symbol> twoMushrooms=new ArrayList<>(Arrays.asList(Symbol.MUSHROOM, Symbol.MUSHROOM));
    private final static ArrayList<Symbol> threeMushrooms=new ArrayList<>(Arrays.asList(Symbol.MUSHROOM, Symbol.MUSHROOM, Symbol.MUSHROOM));
    private final static ArrayList<Symbol> fiveMushrooms=new ArrayList<>(Arrays.asList(Symbol.MUSHROOM, Symbol.MUSHROOM,Symbol.MUSHROOM, Symbol.MUSHROOM, Symbol.MUSHROOM));
    private final static ArrayList<Symbol> twoWolves=new ArrayList<>(Arrays.asList(Symbol.WOLF, Symbol.WOLF));
    private final static ArrayList<Symbol> threeWolves=new ArrayList<>(Arrays.asList(Symbol.WOLF, Symbol.WOLF, Symbol.WOLF));
    private final static ArrayList<Symbol> fiveWolves=new ArrayList<>(Arrays.asList(Symbol.WOLF, Symbol.WOLF,Symbol.WOLF, Symbol.WOLF, Symbol.WOLF));
    private final static ArrayList<Symbol> twoLeaves=new ArrayList<>(Arrays.asList(Symbol.LEAF, Symbol.LEAF));
    private final static ArrayList<Symbol> threeLeaves=new ArrayList<>(Arrays.asList(Symbol.LEAF,Symbol.LEAF, Symbol.LEAF));
    private final static ArrayList<Symbol> fiveLeaves=new ArrayList<>(Arrays.asList(Symbol.LEAF, Symbol.LEAF,Symbol.LEAF,Symbol.LEAF, Symbol.LEAF));
    private final static ArrayList<Symbol> twoButterflies=new ArrayList<>(Arrays.asList(Symbol.BUTTERFLY, Symbol.BUTTERFLY));
    private final static ArrayList<Symbol> threeButterflies=new ArrayList<>(Arrays.asList(Symbol.BUTTERFLY, Symbol.BUTTERFLY, Symbol.BUTTERFLY));
    private final static ArrayList<Symbol> fiveButterflies=new ArrayList<>(Arrays.asList(Symbol.BUTTERFLY, Symbol.BUTTERFLY, Symbol.BUTTERFLY, Symbol.BUTTERFLY, Symbol.BUTTERFLY));

    /**
     * map that returns a resource card corresponding to a given ID
     */
    private static final HashMap<String, Supplier<ResourceCard>> goldCardMaker= new HashMap<>(){{
        //red gold cards
        put("GR0",()->new GoldCard("GR0",Symbol.MUSHROOM,new Symbol[]{Symbol.BLANK, Symbol.FULL, Symbol.BLANK, Symbol.FEATHER},featherCardObjective, add(twoMushrooms, Symbol.WOLF)));
        put("GR1",()->new GoldCard("GR1",Symbol.MUSHROOM,new Symbol[]{Symbol.INK, Symbol.BLANK, Symbol.FULL, Symbol.BLANK},inkCardObjective,add(twoMushrooms, Symbol.LEAF)));
        put("GR2",()->new GoldCard("GR2",Symbol.MUSHROOM,new Symbol[]{Symbol.BLANK, Symbol.SCROLL, Symbol.BLANK, Symbol.FULL},scrollCardObjective,add(twoMushrooms, Symbol.BUTTERFLY)));
        put("GR3",()->new GoldCard("GR3",Symbol.MUSHROOM,new Symbol[]{Symbol.BLANK, Symbol.BLANK, Symbol.FULL, Symbol.BLANK},cornersCardObjective,add(threeMushrooms, Symbol.WOLF)));
        put("GR4",()->new GoldCard("GR4",Symbol.MUSHROOM,new Symbol[]{Symbol.BLANK, Symbol.BLANK, Symbol.BLANK, Symbol.FULL},cornersCardObjective,add(threeMushrooms, Symbol.LEAF)));
        put("GR5",()->new GoldCard("GR5",Symbol.MUSHROOM,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.BLANK, Symbol.BLANK},cornersCardObjective,add(threeMushrooms, Symbol.BUTTERFLY)));
        put("GR6",()->new GoldCard("GR6",Symbol.MUSHROOM,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.INK, Symbol.FULL},threePointsCardObjective,threeMushrooms));
        put("GR7",()->new GoldCard("GR7",Symbol.MUSHROOM,new Symbol[]{Symbol.BLANK, Symbol.FEATHER, Symbol.FULL, Symbol.FULL},threePointsCardObjective,threeMushrooms));
        put("GR8",()->new GoldCard("GR8",Symbol.MUSHROOM,new Symbol[]{Symbol.SCROLL, Symbol.FULL, Symbol.FULL, Symbol.BLANK},threePointsCardObjective,threeMushrooms));
        put("GR9",()->new GoldCard("GR9",Symbol.MUSHROOM,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.BLANK, Symbol.FULL},fivePointsCardObjective,fiveMushrooms));
        //green gold cards
        put("GG0",()->new GoldCard("GG0",Symbol.LEAF,new Symbol[]{Symbol.BLANK,Symbol.FEATHER,Symbol.BLANK,Symbol.FULL},featherCardObjective,add(twoLeaves,Symbol.BUTTERFLY)));
        put("GG1",()->new GoldCard("GG1",Symbol.LEAF,new Symbol[]{Symbol.SCROLL,Symbol.BLANK,Symbol.FULL,Symbol.BLANK},scrollCardObjective,add(twoLeaves,Symbol.MUSHROOM)));
        put("GG2",()->new GoldCard("GG2",Symbol.LEAF,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.INK,Symbol.BLANK},inkCardObjective,add(twoLeaves,Symbol.WOLF)));
        put("GG3",()->new GoldCard("GG3",Symbol.LEAF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.BLANK,Symbol.BLANK},cornersCardObjective,add(threeLeaves,Symbol.BUTTERFLY)));
        put("GG4",()->new GoldCard("GG4",Symbol.LEAF,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.BLANK,Symbol.FULL},cornersCardObjective,add(threeLeaves,Symbol.WOLF)));
        put("GG5",()->new GoldCard("GG5",Symbol.LEAF,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.BLANK, Symbol.BLANK},cornersCardObjective,add(threeLeaves,Symbol.MUSHROOM)));
        put("GG6",()->new GoldCard("GG6",Symbol.LEAF,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.FEATHER,Symbol.FULL},threePointsCardObjective,threeLeaves));
        put("GG7",()->new GoldCard("GG7",Symbol.LEAF,new Symbol[]{Symbol.BLANK,Symbol.SCROLL,Symbol.FULL,Symbol.FULL},threePointsCardObjective,threeLeaves));
        put("GG8",()->new GoldCard("GG8",Symbol.LEAF,new Symbol[]{Symbol.INK,Symbol.FULL,Symbol.FULL,Symbol.BLANK},threePointsCardObjective,threeLeaves));
        put("GG9",()->new GoldCard("GG9",Symbol.LEAF,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.FULL},fivePointsCardObjective,fiveLeaves));
        //blue gold cards
        put("GB0",()->new GoldCard("GB0",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.INK,Symbol.BLANK,Symbol.FULL},inkCardObjective,add(twoWolves,Symbol.BUTTERFLY)));
        put("GB1",()->new GoldCard("GB1",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.BLANK,Symbol.SCROLL},scrollCardObjective,add(twoWolves,Symbol.LEAF)));
        put("GB2",()->new GoldCard("GB2",Symbol.WOLF,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.FEATHER,Symbol.BLANK},featherCardObjective,add(twoWolves,Symbol.MUSHROOM)));
        put("GB3",()->new GoldCard("GB3",Symbol.WOLF,new Symbol[]{Symbol.BLANK, Symbol.BLANK, Symbol.FULL, Symbol.BLANK},cornersCardObjective,add(threeWolves,Symbol.BUTTERFLY)));
        put("GB4",()->new GoldCard("GB4",Symbol.WOLF,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.BLANK, Symbol.BLANK},cornersCardObjective,add(threeWolves,Symbol.MUSHROOM)));
        put("GB5",()->new GoldCard("GB5",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.BLANK,Symbol.BLANK},cornersCardObjective,add(threeWolves,Symbol.LEAF)));
        put("GB6",()->new GoldCard("GB6",Symbol.WOLF,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.SCROLL,Symbol.FULL},threePointsCardObjective,threeWolves));
        put("GB7",()->new GoldCard("GB7",Symbol.WOLF,new Symbol[]{Symbol.INK,Symbol.BLANK,Symbol.FULL,Symbol.FULL},threePointsCardObjective,threeWolves));
        put("GB8",()->new GoldCard("GB8",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.FULL,Symbol.FEATHER},threePointsCardObjective,threeWolves));
        put("GB9",()->new GoldCard("GB9",Symbol.WOLF,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.FULL,Symbol.BLANK},fivePointsCardObjective,fiveWolves));
        //purple gold cards
        put("GP0",()->new GoldCard("GP0",Symbol.BUTTERFLY,new Symbol[]{Symbol.FEATHER,Symbol.BLANK,Symbol.FULL,Symbol.BLANK},featherCardObjective,add(twoButterflies,Symbol.LEAF)));
        put("GP1",()->new GoldCard("GP1",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL,Symbol.BLANK,Symbol.SCROLL,Symbol.BLANK},scrollCardObjective,add(twoButterflies,Symbol.WOLF)));
        put("GP2",()->new GoldCard("GP2",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.FULL,Symbol.BLANK,Symbol.INK},inkCardObjective,add(twoButterflies,Symbol.MUSHROOM)));
        put("GP3",()->new GoldCard("GP3",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.BLANK},cornersCardObjective,add(threeButterflies,Symbol.WOLF)));
        put("GP4",()->new GoldCard("GP4",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.BLANK,Symbol.FULL},cornersCardObjective,add(threeButterflies,Symbol.LEAF)));
        put("GP5",()->new GoldCard("GP5",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL, Symbol.BLANK, Symbol.BLANK, Symbol.BLANK},cornersCardObjective,add(threeButterflies,Symbol.MUSHROOM)));
        put("GP6",()->new GoldCard("GP6",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL,Symbol.INK,Symbol.BLANK,Symbol.FULL},threePointsCardObjective,threeButterflies));
        put("GP7",()->new GoldCard("GP7",Symbol.BUTTERFLY,new Symbol[]{Symbol.SCROLL,Symbol.BLANK,Symbol.FULL,Symbol.FULL},threePointsCardObjective,threeButterflies));
        put("GP8",()->new GoldCard("GP8",Symbol.BUTTERFLY,new Symbol[]{Symbol.FULL,Symbol.FULL,Symbol.FEATHER,Symbol.BLANK},threePointsCardObjective,threeButterflies));
        put("GP9",()->new GoldCard("GP9",Symbol.BUTTERFLY,new Symbol[]{Symbol.BLANK,Symbol.BLANK,Symbol.FULL,Symbol.FULL},fivePointsCardObjective,fiveButterflies));
    }};


    /**
     *
     * @param cardID is a unique code that identifies a specific card
     * @return the card specified by the ID
     */
    public static ResourceCard makeGoldCard(String cardID) throws IllegalArgumentException{
        Supplier<ResourceCard> cardSupplier= goldCardMaker.get(cardID);
        if(cardSupplier!=null){
            return cardSupplier.get();
        }
        else{
            throw new IllegalArgumentException(cardID+" Card not found");
        }
    }

    /**
     *
     * @return every cardID that can be assigned to a resourceCard
     */
    public static ArrayList<String> makeEveryGoldCardID(){
        ArrayList<String> everyGoldCardID = new ArrayList<>();
        ArrayList<String> resourceCardsPrefix=new ArrayList<>();
        resourceCardsPrefix.add("GR");
        resourceCardsPrefix.add("GG");
        resourceCardsPrefix.add("GB");
        resourceCardsPrefix.add("GP");
        for(String prefix:resourceCardsPrefix){
            for(int i=0; i<10; i++){
                everyGoldCardID.add(prefix+i);
            }
        }
        return everyGoldCardID;
    }

    /**
     *
     * @return every resource card
     */
    public static ArrayList<ResourceCard> makeEveryGoldCard(){
        ArrayList<ResourceCard> everyResourceCard=new ArrayList<>();
        ArrayList<String> everyGoldCardID= makeEveryGoldCardID();
        for(String cardID:everyGoldCardID){
            everyResourceCard.add(makeGoldCard(cardID));
        }
        return everyResourceCard;
    }

    /**
     *
     * @param incompletePrerequisites prerequisites that are missing a symbol
     * @param symbol that is missing from prerequisites
     * @return list containing incomplete prerequisites plus the given symbol
     */
    static ArrayList<Symbol> add(ArrayList<Symbol> incompletePrerequisites, Symbol symbol){
        ArrayList<Symbol> clone = (ArrayList<Symbol>) incompletePrerequisites.clone();
        clone.add(symbol);
        return clone;
    }

}
