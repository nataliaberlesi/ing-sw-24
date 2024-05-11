package it.polimi.ingsw.Server.Model.Cards;

public class DeckFactory {

    public static Deck createShuffledGoldDeck(){
        Deck deck=new Deck(GoldCardFactory.makeEveryGoldCardID());
        deck.shuffle();
        return deck;
    }

    public static Deck createShuffledResourceDeck(){
        Deck deck=new Deck(ResourceCardFactory.makeEveryResourceCardID());
        deck.shuffle();
        return deck;
    }
    public static Deck createShuffledStartingDeck(){
        Deck deck=new Deck(StartingCardFactory.makeEveryStartingCardID());
        deck.shuffle();
        return deck;
    }
}
