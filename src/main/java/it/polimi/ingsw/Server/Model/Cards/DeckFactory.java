package it.polimi.ingsw.Server.Model.Cards;

public class DeckFactory {

    public static Deck createGoldDeck(){
        return new Deck(GoldCardFactory.makeEveryGoldCard());
    }

    public static Deck createResourceDeck(){
        return new Deck(ResourceCardFactory.makeEveryResourceCard());
    }
}
