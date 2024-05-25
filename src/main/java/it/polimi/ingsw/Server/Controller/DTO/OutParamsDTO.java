package it.polimi.ingsw.Server.Controller.DTO;

import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ScoreboardPositionDTO;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.PlacedCard;

import java.util.ArrayList;

public record OutParamsDTO(String currentPlayer,
                           Boolean unavailableUsername,
                           Card card,
                           String affectedPlayer,
                           ArrayList<PlacedCard> placedCards,
                           Integer score,
                           String color,
                           Card[] hand,
                           Objective[] privateObjectives,
                           Objective[] publicObjectives,
                           Objective chosenObjective,
                           ArrayList<String> players,
                           Card[] resourceDrawableArea,
                           Card[] goldDrawableArea,
                           ArrayList<Color> availableColors,
                           ArrayList<ScoreboardPositionDTO> scoreboard,
                           String cause,
                           Boolean masterStatus,
                           String chat
                           ) {
    /**
     * Constructor used for CONNECT params
     */
    public OutParamsDTO(Boolean masterStatus) {
        this(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,masterStatus,null);
    }
    /**
     * Constructor used for JOIN params
     * @param currentPlayer player who tried to join
     * @param unavailableUsername if the username he provided is unavailable
     */
    public OutParamsDTO(String currentPlayer,
                        Boolean unavailableUsername) {
        this(currentPlayer, unavailableUsername,null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }
    /**
     * Constructor used for START_FIRSTROUND params
     * @param currentPlayer the player who has his turn
     * @param card the currentPlayer starting card
     * @param players the player list
     * @param resourceDrawableArea
     * @param goldDrawableArea
     * @param availableColors the current available colors
     */
    public OutParamsDTO(String currentPlayer, Card card, ArrayList<String> players,Card[] resourceDrawableArea, Card[] goldDrawableArea, ArrayList<Color> availableColors){
        this(currentPlayer, null,
                card,null,null,null,null,null,null,null,null,
                players,
                resourceDrawableArea,
                goldDrawableArea,
                availableColors,null,null,null,null);
    }

    /**
     * Constructor used for FIRSTROUND params
     * @param currentPlayer
     * @param card
     * @param affectedPlayer
     * @param placedCards
     * @param color
     */
    public OutParamsDTO(String currentPlayer, Card card, String affectedPlayer, ArrayList<PlacedCard> placedCards, String color, ArrayList<Color> availableColors){
        this(currentPlayer, null,
                card,
                affectedPlayer,
                placedCards,null,
                color,null,null,null,null,null,null,null,
                availableColors,null,null,null,null);
    }

    /**
     * Constructor used for START_SECONDROUND params
     * @param currentPlayer
     * @param hand
     */
    public OutParamsDTO(String currentPlayer, Card[] hand,Objective[] privateObjectives, Objective[] publicObjectives){
        this(currentPlayer, null, null,null,null,null,null,
                hand,privateObjectives,
                publicObjectives,null,null,null,null,null,null,null,null,null);
    }
    /**
     * Constructor used for SECONDROUND params
     */
    public OutParamsDTO(String currentPlayer, String affectedPlayer, Card[] hand,Objective[] privateObjectives, Objective chosenPrivateObjective){
        this(currentPlayer, null, null,affectedPlayer,null,null,null,hand,privateObjectives,null,chosenPrivateObjective,null,null,null,null,null,null,null,null);
    }
    /**
     * Constructor used for ACTION_PLACECARD params
     */
    public OutParamsDTO(String currentPlayer, String affectedPlayer, ArrayList<PlacedCard> placedCards, Integer score, Card[] hand){
        this(currentPlayer, null, null,affectedPlayer,placedCards,score,null,hand,null,null,null,null,null,null,null,null,null,null,null);
    }

    /**
     * Constructor used for ACTION_DRAWCARD params
     * @param currentPlayer
     */
    public OutParamsDTO(String currentPlayer, String affectedPlayer, Card[] hand, Card[] resourceDrawableArea, Card[] goldDrawableArea){
        this(currentPlayer, null, null,affectedPlayer,null,null,null,hand,null,null,null,null,resourceDrawableArea,goldDrawableArea,null,null,null,null,null);
    }

    /**
     * Constructor used for WINNERS params
     * @param scoreboard
     */
    public OutParamsDTO(ArrayList<ScoreboardPositionDTO> scoreboard){
        this(null, null, null,null,null,null,null,null,null,null,null,null,null,null,null,scoreboard,null,null,null);
    }
    /**
     * Constructor used for ABORT message
     */
    public OutParamsDTO(String cause){
        this(null, null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,cause,null,null);
    }
    /**
     * Constructor used for CONTINUE message
     */
    public OutParamsDTO(String affectedPlayer, ArrayList<PlacedCard> placedCards, Integer score, String color, Card[] hand, Objective[] publicObjectives, Objective chosenPrivateObjective, ArrayList<String> players, Card[] resourceDrawableArea, Card[] goldDrawableArea) {
        this(null,null,null,affectedPlayer,placedCards,score,color,hand,null,publicObjectives,chosenPrivateObjective,players,resourceDrawableArea,goldDrawableArea, null, null, null, null,null);
    }
    /**
     * Constructor used for CHAT message
     */
    public OutParamsDTO(String currentPlayer, String affectedPlayer, String chat) {
        this(currentPlayer, null, null, affectedPlayer, null,null,null,null,null,null,null,null,null,null,null,null,null,null,chat);
    }

}

