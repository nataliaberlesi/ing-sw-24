package it.polimi.ingsw.Server.Model.Cards;

public class DeckFactory {

    public static Deck createGoldDeck(){
        return new Deck(GoldCardFactory.makeEveryGoldCardID());
    }

    public static Deck createResourceDeck(){
        return new Deck(ResourceCardFactory.makeEveryResourceCardID());
    }
}
