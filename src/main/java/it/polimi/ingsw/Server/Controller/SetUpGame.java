package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Controller.DTO.StartFirstRound;
import it.polimi.ingsw.Server.Controller.DTO.StartSecondRound;
import it.polimi.ingsw.Server.Model.Cards.*;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.DrawableArea;
import it.polimi.ingsw.Server.Model.DrawableCards;
import it.polimi.ingsw.Server.Model.Player;

import java.util.*;

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
        Card[] resourceDrawableArea= new Card[3];
        Card[] goldDrawableArea=new Card[3];
        for(int i=0;i<3;i++) {
            resourceDrawableArea[i]= ResourceCardFactory.makeResourceCard(drawableArea.getResourceDrawingSection().seeCard(i));
            goldDrawableArea[i]=GoldCardFactory.makeGoldCard(drawableArea.getGoldDrawingSection().seeCard(i));
        }
        gameInstance.setDrawableArea(drawableArea);
        ArrayList<Color> colors=new ArrayList<>();
        for(Color color: Color.values()) {
            colors.add(color);
        }
        return new StartFirstRound(gameInstance.getPlayerTurnOrder(),firstPlayerStartingCard,resourceDrawableArea,goldDrawableArea,colors);
    }
    public static StartSecondRound getStartSecondRoundParams(GameInstance gameInstance) {
        Card[] hand=new Card[3];
        String currentPlayer=gameInstance.getTurn();
        for(int i=0;i<2;i++) {
            gameInstance
                    .getPlayers()
                    .get(currentPlayer)
                    .getPlayerHand()
                    .placeCardInHand(gameInstance.getDrawableArea().getResourceDrawingSection().drawCard(0));
        }
        gameInstance
                .getPlayers()
                .get(currentPlayer)
                .getPlayerHand()
                .placeCardInHand(gameInstance.getDrawableArea().getGoldDrawingSection().drawCard(0));
        for(int i=0;i<3;i++) {
            hand[i]=ResourceCardFactory.makeResourceCard(gameInstance
                    .getPlayers()
                    .get(currentPlayer)
                    .getPlayerHand()
                    .showCardInHand(i));
        }
        ArrayList<String> objectives=ObjectiveFactory.makeEveryObjectiveID();
        Collections.shuffle(objectives);
        Iterator<String> objectiveIterator= objectives.iterator();
        Objective firstPrivateObjective=ObjectiveFactory.makeObjective(objectiveIterator.next());
        Objective secondPrivateObjective=ObjectiveFactory.makeObjective(objectiveIterator.next());
        Objective firstPublicObjective=ObjectiveFactory.makeObjective(objectiveIterator.next());
        Objective secondPublicObjective=ObjectiveFactory.makeObjective(objectiveIterator.next());
        gameInstance.setPublicObjectives(firstPublicObjective.getID(),secondPublicObjective.getID());
        return new StartSecondRound(currentPlayer,hand,firstPrivateObjective,secondPrivateObjective,firstPublicObjective,secondPublicObjective);
    }
    public static DrawableArea getDrawableArea() {
        Deck goldDeck=DeckFactory.createShuffledGoldDeck();
        Deck resourceDeck=DeckFactory.createShuffledResourceDeck();
        DrawableCards goldDrawableCards=new DrawableCards(goldDeck);
        DrawableCards resourceDrawableCards=new DrawableCards(resourceDeck);
        return new DrawableArea(resourceDrawableCards,goldDrawableCards);
    }
}
