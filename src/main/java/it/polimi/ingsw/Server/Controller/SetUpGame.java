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
     * @return DTO representing the gameInstance's first round starting configuration
     */
    public static StartFirstRound getStartFirstRoundParams(GameInstance gameInstance) {
        Collections.shuffle(gameInstance.getPlayerTurnOrder());
        setupPlayerBoards(gameInstance);
        String firstPlayerStartingCardID=gameInstance.getPlayers().get(gameInstance.getTurn()).getPlayerBoard().seeStartingCardID();
        Card firstPlayerStartingCard=StartingCardFactory.makeStartingCard(firstPlayerStartingCardID);
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

    /**
     * Generates second round starting configuration
     * @param gameInstance
     * @return DTO representing the game instance starting configuration
     */
    public static StartSecondRound getStartSecondRoundParams(GameInstance gameInstance) {
        Player currentPlayer=gameInstance.getPlayers().get(gameInstance.getTurn());
        Card[] hand=setupHand(currentPlayer,gameInstance.getDrawableArea());
        Objective firstPrivateObjective=ObjectiveFactory.makeObjective(currentPlayer.getPlayerBoard().seeFirstPrivateObjectiveID());
        Objective secondPrivateObjective=ObjectiveFactory.makeObjective(currentPlayer.getPlayerBoard().seeSecondPrivateObjectiveID());
        Objective firstPublicObjective=currentPlayer.getPlayerBoard().seeFirstPublicObjective();
        Objective secondPublicObjective=currentPlayer.getPlayerBoard().seeSecondPublicObjective();
        return new StartSecondRound(currentPlayer.getUsername(),hand,firstPrivateObjective,secondPrivateObjective,firstPublicObjective,secondPublicObjective);
    }


    private static void setupPlayerBoards(GameInstance gameInstance) {
        Deck deck= DeckFactory.createShuffledStartingDeck();
        ArrayList<String> objectives=ObjectiveFactory.makeEveryObjectiveID();
        Collections.shuffle(objectives);
        Iterator<String> objectiveIterator= objectives.iterator();
        String firstPublicObjectiveID=objectiveIterator.next();
        String secondPublicObjectiveID=objectiveIterator.next();
        String firstPrivateObjectiveID=objectiveIterator.next();
        String secondPrivateObjectiveID=objectiveIterator.next();
        for(String player: gameInstance.getPlayerTurnOrder()) {
            gameInstance.getPlayers().get(player).startPlayerBoard(deck.next(),firstPublicObjectiveID,secondPublicObjectiveID,firstPrivateObjectiveID, secondPrivateObjectiveID);
        }
    }
    private static DrawableArea getDrawableArea() {
        Deck goldDeck=DeckFactory.createShuffledGoldDeck();
        Deck resourceDeck=DeckFactory.createShuffledResourceDeck();
        DrawableCards goldDrawableCards=new DrawableCards(goldDeck);
        DrawableCards resourceDrawableCards=new DrawableCards(resourceDeck);
        return new DrawableArea(resourceDrawableCards,goldDrawableCards);
    }
    private static Card[] setupHand(Player currentPlayer, DrawableArea drawableArea) {
        Card[] hand=new Card[3];
        for(int i=0;i<2;i++) {
            currentPlayer
                    .getPlayerHand()
                    .placeCardInHand(drawableArea.getResourceDrawingSection().drawCard(0));
        }
        currentPlayer
                .getPlayerHand()
                .placeCardInHand(drawableArea.getGoldDrawingSection().drawCard(0));
        for(int i=0;i<3;i++) {
            hand[i]=ResourceCardFactory.makeResourceCard(currentPlayer
                    .getPlayerHand()
                    .showCardInHand(i));
        }
        return hand;
    }
}
