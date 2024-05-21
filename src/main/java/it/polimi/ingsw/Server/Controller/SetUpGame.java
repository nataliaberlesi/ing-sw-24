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
        gameInstance.setDrawableArea(drawableArea);
        setupHands(gameInstance);
        return new OutParamsDTO(
                gameInstance.getPlayerTurnOrder().get(0),
                firstPlayerStartingCard,
                gameInstance.getPlayerTurnOrder(),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea(),
                gameInstance.getAvailableColors());
    }

    /**
     * Generates second round starting configuration
     * @param gameInstance
     * @return DTO representing the game instance starting configuration
     */
    public static OutParamsDTO getStartSecondRoundParams(GameInstance gameInstance) {
        Player currentPlayer=gameInstance.getPlayers().get(gameInstance.getTurn());
        Card[] hand= gameInstance.getHand(gameInstance.getTurn());
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
        for(String player: gameInstance.getPlayerTurnOrder()) {
            gameInstance.getPlayers().get(player).placeStartingCard(deck.next());
        }
        setupObjectives(gameInstance);
    }
    private static void setupObjectives(GameInstance gameInstance) {
        ArrayList<String> objectives=ObjectiveFactory.makeEveryObjectiveID();
        Collections.shuffle(objectives);
        Iterator<String> objectiveIterator= objectives.iterator();
        String firstPublicObjectiveID=objectiveIterator.next();
        String secondPublicObjectiveID=objectiveIterator.next();
        for(String player: gameInstance.getPlayerTurnOrder()) {
            String firstPrivateObjectiveID=objectiveIterator.next();
            String secondPrivateObjectiveID=objectiveIterator.next();
            gameInstance.getPlayers().get(player).getPlayerBoard().setStartingObjectivesID(firstPrivateObjectiveID,secondPrivateObjectiveID);
            gameInstance.setPublicObjectives(player,firstPublicObjectiveID,secondPublicObjectiveID);
        }
    }
    private static void setupHands(GameInstance gameInstance) {
        DrawableArea drawableArea=gameInstance.getDrawableArea();
        for(String player: gameInstance.getPlayerTurnOrder()) {
            Player currentPlayer=gameInstance.getPlayers().get(player);
            for(int i=0;i<2;i++) {
                currentPlayer
                        .getPlayerHand()
                        .placeCardInHand(drawableArea.getResourceDrawingSection().drawCard(0));
            }
            currentPlayer
                    .getPlayerHand()
                    .placeCardInHand(drawableArea.getGoldDrawingSection().drawCard(0));
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
