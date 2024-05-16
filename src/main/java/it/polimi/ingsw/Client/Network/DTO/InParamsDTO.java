package it.polimi.ingsw.Client.Network.DTO;

import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CardDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ObjectiveDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.PlacedCardDTO;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.PlacedCard;

import java.util.ArrayList;

public record InParamsDTO(String currentPlayer,
                           Boolean unavailableUsername,
                           CardDTO card,
                           String affectedPlayer,
                           ArrayList<PlacedCardDTO> placedCards,
                           Integer score,
                           String color,
                           CardDTO[] hand,
                           ObjectiveDTO firstPrivateObjective,
                           ObjectiveDTO secondPrivateObjective,
                           ObjectiveDTO firstPublicObjective,
                           ObjectiveDTO secondPublicObjective,
                           ObjectiveDTO chosenPrivateObjective,
                           ArrayList<String> players,
                           CardDTO[] resourceDrawableArea,
                           CardDTO[] goldDrawableArea,
                           ArrayList<String> availableColors,
                           ArrayList<String> winners,
                          Boolean masterStatus
) {
}
