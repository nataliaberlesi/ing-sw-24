package it.polimi.ingsw.Client.Network.DTO;

import it.polimi.ingsw.Client.Network.DTO.ModelDTO.*;

import java.util.ArrayList;

public record InParamsDTO(String currentPlayer,
                          Boolean unavailableUsername, //
                          CardDTO card, //
                          String affectedPlayer, //
                          ArrayList<PlacedCardDTO> placedCards, //
                          Integer score, //
                          String color, //
                          CardDTO[] hand, //
                          ObjectiveDTO[] privateObjectives,
                          ObjectiveDTO[] publicObjectives,
                          ObjectiveDTO chosenObjective,
                          ArrayList<String> players, //
                          CardDTO[] resourceDrawableArea, //?
                          CardDTO[] goldDrawableArea, //?
                          ArrayList<String> availableColors, //
                          ArrayList<String> winners, //
                          String cause,
                          Boolean masterStatus, //
                          ArrayList<ScoreboardPositionDTO> scoreboard
) {
}
