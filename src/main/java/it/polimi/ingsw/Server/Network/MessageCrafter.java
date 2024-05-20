package it.polimi.ingsw.Server.Network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import it.polimi.ingsw.Server.Controller.DTO.OutParamsDTO;
import it.polimi.ingsw.Server.Controller.GameController;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.ObjectiveFactory;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Cards.StartingCardFactory;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.PlacedCard;
import it.polimi.ingsw.Server.Model.Player;
import it.polimi.ingsw.Server.Model.Scoreboard;

import java.util.ArrayList;

public class MessageCrafter {
    private static final MessageParser messageParser=MessageParser.getINSTANCE();
    public static Message craftErrorMessage(MessageType messageType, String currentPlayer) {
        JsonObject params=new JsonObject();
        params.addProperty("currentPlayer",currentPlayer);
        return new Message(messageType,params);
    }
    public static Message craftConnectMessage(boolean masterStatus) {
        MessageType messageType=MessageType.CONNECT;
        JsonObject params=new JsonObject();
        params.addProperty("masterStatus",masterStatus);
        return new Message(messageType,params);
    }
    public static Message craftAbortMessage(String cause) {
        MessageType messageType=MessageType.ABORT;
        JsonObject params= new JsonObject();
        params.addProperty("cause",cause);
        return new Message(messageType,params);
    }
    public static Message craftCreateMessage(String username) {
        MessageType messageType=MessageType.CREATE;
        JsonObject params= new JsonObject();
        params.addProperty("currentPlayer",username);
        return new Message(messageType,params);
    }
    public static Message craftJoinMessage(String username,Boolean unavailableUsername) {
        MessageType messageType=MessageType.JOIN;
        OutParamsDTO outParamsDTO=
                new OutParamsDTO(
                        username,
                        unavailableUsername
                );
        return new Message(messageType,messageParser.toJsonObject(outParamsDTO));
    }

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
        return new Message(messageType,messageParser.toJsonObject(outParamsDTO));
    }
    public static Message craftSecondRoundMessage(String currentPlayer, String affectedPlayer, Card[] hand, Objective[] privateObjectives, Objective chosenPrivateObjective) {
            MessageType messageType=MessageType.SECONDROUND;
            OutParamsDTO outParamsDTO=new OutParamsDTO(
                    currentPlayer,
                    affectedPlayer,
                    hand,
                    privateObjectives,
                    chosenPrivateObjective
            );
            return new Message(messageType,messageParser.toJsonObject(outParamsDTO));
    }

    public static Message craftPlaceCardMessage(String currentPlayer, ArrayList<PlacedCard> placedCards, Integer score, Card[] hand) {
        MessageType messageType=MessageType.ACTION_PLACECARD;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                currentPlayer,
                currentPlayer,
                placedCards,
                score,
                hand
        );
        return new Message(messageType, messageParser.toJsonObject(outParamsDTO));
    }

    public static Message craftDrawCardMessage(String currentPlayer, String affectedPlayer, Card[] hand, Card[] resourceDrawableArea, Card[] goldDrawableArea) {
        MessageType messageType=MessageType.ACTION_DRAWCARD;
        OutParamsDTO outParamsDTO=new OutParamsDTO(
                currentPlayer,
                affectedPlayer,
                hand,
                resourceDrawableArea,
                goldDrawableArea
        );
        return new Message(messageType, messageParser.toJsonObject(outParamsDTO));
    }

    public static Message craftWinnersMessage(Scoreboard scoreBoard) {
        MessageType messageType=MessageType.WINNERS;
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
        return new Message(messageType,messageParser.toJsonObject(outParamsDTO));
    }
}
