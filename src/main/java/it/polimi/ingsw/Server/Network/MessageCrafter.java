package it.polimi.ingsw.Server.Network;


import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import it.polimi.ingsw.Server.Controller.DTO.OutParamsDTO;
import it.polimi.ingsw.Server.Controller.DTO.ParamsDTO;
import it.polimi.ingsw.Server.Controller.GameInstance;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.PlacedCard;
import it.polimi.ingsw.Server.Model.Player;
import it.polimi.ingsw.Server.Model.Scoreboard;

import java.util.ArrayList;

public class MessageCrafter {
    /**
     * @param masterStatus if the user is the first to connect
     * @return a CONNECT message
     */
    public static Message craftConnectMessage(boolean masterStatus) {
        MessageType messageType=MessageType.CONNECT;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                masterStatus
        );
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param cause the cause of abort
     * @return an ABORT message
     */
    public static Message craftAbortMessage(String cause) {
        MessageType messageType=MessageType.ABORT;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                cause
        );
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param username the nickname of the user who is creating the game
     * @return a CREATE message
     */
    public static Message craftCreateMessage(String username) {
        MessageType messageType=MessageType.CREATE;
        return new Message(messageType,new ParamsDTO(null,null));
    }

    /**
     * @param username the nickname of the user who is joining the game
     * @param unavailableUsername if the nickname is unavailable
     * @return a JOIN message
     */
    public static Message craftJoinMessage(String username,Boolean unavailableUsername) {
        MessageType messageType=MessageType.JOIN;
        OutParamsDTO outParamsDTO=
                new OutParamsDTO(
                        username,
                        unavailableUsername
                );
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param cardID the ID of the current player starting card
     * @param currentPlayer the current player
     * @param availableColors the current available colors
     * @param affectedPlayer the affected player
     * @param placedCards the affected player's placed cards
     * @param color the affected player's chosen color
     * @return a FIRSTROUND message
     */
    public static Message craftFirstRoundMessage(String cardID, String currentPlayer, ArrayList<Color> availableColors, String affectedPlayer, ArrayList<PlacedCard> placedCards, String color) {
        MessageType messageType=MessageType.FIRSTROUND;
        Card card=null;
        if(cardID!=null) {
            card=StartingCardFactory.makeStartingCard(cardID);
        }
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                currentPlayer,
                card,
                affectedPlayer,
                placedCards,
                color,
                availableColors);
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param currentPlayer the current player
     * @param affectedPlayer the affected player
     * @param hand the current player hand
     * @param privateObjectives the current player private objectives
     * @param chosenPrivateObjective the affected player chosen objective
     * @return a SECONDROUND message
     */
    public static Message craftSecondRoundMessage(String currentPlayer, String affectedPlayer, Card[] hand, Objective[] privateObjectives, Objective chosenPrivateObjective) {
            MessageType messageType=MessageType.SECONDROUND;
            OutParamsDTO outParamsDTO=new OutParamsDTO(
                    currentPlayer,
                    affectedPlayer,
                    hand,
                    privateObjectives,
                    chosenPrivateObjective
            );
            return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param currentPlayer the current and affected player
     * @param placedCards the current player placed cards
     * @param score the current player score
     * @param hand the current player hand
     * @return a ACTION_PLACECARD message
     */
    public static Message craftPlaceCardMessage(String currentPlayer, ArrayList<PlacedCard> placedCards, Integer score, Card[] hand) {
        MessageType messageType=MessageType.ACTION_PLACECARD;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                currentPlayer,
                currentPlayer,
                placedCards,
                score,
                hand
        );
        return new Message(messageType, new ParamsDTO(outParamsDTO));
    }

    /**
     * @param currentPlayer the current player
     * @param affectedPlayer the affected player
     * @param hand the affected player hand
     * @param resourceDrawableArea the board drawable area
     * @param goldDrawableArea the board drawable area
     * @return a ACTION_DRAWCARD message
     */
    public static Message craftDrawCardMessage(String currentPlayer, String affectedPlayer, Card[] hand, Card[] resourceDrawableArea, Card[] goldDrawableArea) {
        MessageType messageType=MessageType.ACTION_DRAWCARD;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                currentPlayer,
                affectedPlayer,
                hand,
                resourceDrawableArea,
                goldDrawableArea
        );
        return new Message(messageType, new ParamsDTO(outParamsDTO));
    }

    /**
     * @param scoreBoard the final scoreboard
     * @return a ENDGAME message
     */
    public static Message craftEndgameMessage(Scoreboard scoreBoard) {
        MessageType messageType=MessageType.ENDGAME;
        ArrayList<ScoreboardPositionDTO> scoreBoardDTO=new ArrayList<>();
        for(Player player:scoreBoard.getScoreboard()){
            scoreBoardDTO.add(new ScoreboardPositionDTO(
                    player.getUsername(),
                    player.getPlayerPosition(),
                    player.getPlayerFinalScore()
            ));
        }
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                scoreBoardDTO
        );
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @return a PERSISTENCE message
     */
    public static Message craftPersistencyNotification() {
        return new Message(MessageType.PERSISTENCE,new ParamsDTO(null,null));
    }

    /**
     * @param gameInstance the gameInstance to continue
     * @return a CONTINUE message
     */
    public static Message craftContinueGameMessage(GameInstance gameInstance) {
        MessageType messageType=MessageType.CONTINUE;
        Objective[] objectives=gameInstance.getObjectives(gameInstance.getTurn());
        Objective[] publicObjectives= new Objective[2];
        publicObjectives[0]=objectives[0];
        publicObjectives[1]=objectives[1];
        OutParamsDTO outParamsDTO= new OutParamsDTO(
                gameInstance.getTurn(),
                gameInstance.getPlacedCards(gameInstance.getTurn()),
                gameInstance.getScore(gameInstance.getTurn()),
                gameInstance.getColor(gameInstance.getTurn()).toString(),
                gameInstance.getHand(gameInstance.getTurn()),
                publicObjectives,
                objectives[2],
                gameInstance.getPlayerTurnOrder(),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea()
        );
        gameInstance.nextTurn();
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }

    /**
     * @param currentPlayer the sender
     * @param affectedPlayer the receiver
     * @param chat the message
     * @return a CHAT message
     */
    public static Message craftChatMessage(String currentPlayer, String affectedPlayer, String chat) {
        MessageType messageType=MessageType.CHAT;
        OutParamsDTO outParamsDTO =new OutParamsDTO(
                currentPlayer,
                affectedPlayer,
                chat
        );
        return new Message(messageType,new ParamsDTO(outParamsDTO));
    }
}
