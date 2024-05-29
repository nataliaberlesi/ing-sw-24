package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Controller.DTO.OutParamsDTO;
import it.polimi.ingsw.Server.Controller.DTO.ParamsDTO;
import it.polimi.ingsw.Server.Model.Cards.*;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
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
     * @return DTO representing the gameInstance's first round starting configuration
     */
    public static ParamsDTO getStartFirstRoundParams(GameInstance gameInstance) {
        Collections.shuffle(gameInstance.getPlayerTurnOrder());
        setupPlayerBoards(gameInstance);
        String firstPlayerStartingCardID=gameInstance.getPlayers().get(gameInstance.getTurn()).getPlayerBoard().seeStartingCardID();
        Card firstPlayerStartingCard=StartingCardFactory.makeStartingCard(firstPlayerStartingCardID);
        DrawableArea drawableArea=getDrawableArea();
        gameInstance.setDrawableArea(drawableArea);
        setupHands(gameInstance);
        return new ParamsDTO(new OutParamsDTO(
                gameInstance.getPlayerTurnOrder().get(0),
                firstPlayerStartingCard,
                gameInstance.getPlayerTurnOrder(),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea(),
                gameInstance.getAvailableColors()));
    }

    /**
     * Generates second round starting configuration
     * @return DTO representing the game instance starting configuration
     */
    public static ParamsDTO getStartSecondRoundParams(GameInstance gameInstance) {
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
        return new ParamsDTO(new OutParamsDTO(currentPlayer.getUsername(),
                hand,
                privateObjectives,
                publicObjectives));
    }

    /**
     *Gives a starting card to each player and setups objectives
     */
    private static void setupPlayerBoards(GameInstance gameInstance) {
        Deck deck= DeckFactory.createShuffledStartingDeck();
        for(String player: gameInstance.getPlayerTurnOrder()) {
            gameInstance.getPlayers().get(player).placeStartingCard(deck.next());
        }
        setupObjectives(gameInstance);
    }

    /**
     * Gives 2 public objectives to each player, and two private objectives to choose from
     */
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

    /**
     *Draws 2 card from the resource deck and 1 card from the gold deck for each player
     */
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

    /**
     * Setups the drawableArea with one resource drawing section and one gold drawing section
     * @return the drawableArea
     */
    private static DrawableArea getDrawableArea() {
        Deck goldDeck=DeckFactory.createShuffledGoldDeck();
        Deck resourceDeck=DeckFactory.createShuffledResourceDeck();
        DrawableCards goldDrawableCards=new DrawableCards(goldDeck);
        DrawableCards resourceDrawableCards=new DrawableCards(resourceDeck);
        return new DrawableArea(resourceDrawableCards,goldDrawableCards);
    }
}
