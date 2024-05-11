package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Controller.DTO.StartFirstRound;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Deck;
import it.polimi.ingsw.Server.Model.Cards.DeckFactory;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.DrawableArea;
import it.polimi.ingsw.Server.Model.DrawableCards;
import it.polimi.ingsw.Server.Model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Cards, drawing section and board creation based on how many players connect
 * */
public class SetUpGame {
    /**
     * Generates first round starting configuration
     * @param gameInstance
     * @return DTO rapresenting the gameInstance's first round starting configuration
     */
    public static StartFirstRound getStartFirstRoundParams(GameInstance gameInstance) {
        Deck deck= DeckFactory.createShuffledStartingDeck();
        gameInstance.setStartingDeck(deck);
        Card firstPlayerStartingCard=StartingCardFactory.makeStartingCard(deck.next());
        Collections.shuffle(gameInstance.getPlayerTurnOrder());
        DrawableArea drawableArea=getDrawableArea();
        ArrayList<Color> colors=new ArrayList<>();
        for(Color color: Color.values()) {
            colors.add(color);
        }
        return new StartFirstRound(gameInstance.getPlayerTurnOrder(),firstPlayerStartingCard,drawableArea,colors);
    }
    public static DrawableArea getDrawableArea() {
        Deck goldDeck=DeckFactory.createShuffledGoldDeck();
        Deck resourceDeck=DeckFactory.createShuffledResourceDeck();
        DrawableCards goldDrawableCards=new DrawableCards(goldDeck);
        DrawableCards resourceDrawableCards=new DrawableCards(resourceDeck);
        return new DrawableArea(resourceDrawableCards,goldDrawableCards);
    }
}
