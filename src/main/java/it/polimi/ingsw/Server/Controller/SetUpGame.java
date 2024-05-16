package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Controller.DTO.OutParamsDTO;
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
    public static OutParamsDTO getStartFirstRoundParams(GameInstance gameInstance) {
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
        return new OutParamsDTO(
                gameInstance.getPlayerTurnOrder().get(0),
                firstPlayerStartingCard,
                gameInstance.getPlayerTurnOrder(),
                resourceDrawableArea,
                goldDrawableArea,
                gameInstance.getAvailableColors());
    }

    /**
     * Generates second round starting configuration
     * @param gameInstance
     * @return DTO representing the game instance starting configuration
     */
    public static OutParamsDTO getStartSecondRoundParams(GameInstance gameInstance) {
        Player currentPlayer=gameInstance.getPlayers().get(gameInstance.getTurn());
        Card[] hand=setupHand(currentPlayer,gameInstance.getDrawableArea());
        Objective firstPrivateObjective=ObjectiveFactory.makeObjective(currentPlayer.getPlayerBoard().seeFirstPrivateObjectiveID());
        Objective secondPrivateObjective=ObjectiveFactory.makeObjective(currentPlayer.getPlayerBoard().seeSecondPrivateObjectiveID());
        Objective[] privateObjectives=new Objective[2];
        privateObjectives[0]=firstPrivateObjective;
        privateObjectives[1]=secondPrivateObjective;
        Objective firstPublicObjective=currentPlayer.getPlayerBoard().seeFirstPublicObjective();
        Objective secondPublicObjective=currentPlayer.getPlayerBoard().seeSecondPublicObjective();
        Objective[] publicObjectives=new Objective[2];
        publicObjectives[0]=firstPublicObjective;
        publicObjectives[1]=secondPublicObjective;
        return new OutParamsDTO(currentPlayer.getUsername(),
                hand,
                privateObjectives,
                publicObjectives);
    }


    private static void setupPlayerBoards(GameInstance gameInstance) {
        Deck deck= DeckFactory.createShuffledStartingDeck();
        gameInstance.setStartingDeck(deck);
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
