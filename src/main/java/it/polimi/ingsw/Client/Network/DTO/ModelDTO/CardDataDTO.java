package it.polimi.ingsw.Client.Network.DTO.ModelDTO;

import java.util.ArrayList;

public record CardDataDTO(String cardID,
                          String backSymbol,
                          String[] frontCorners,
                          String[] backCorners,
                          ArrayList<String> frontCenterSymbols,
                          ObjectiveDTO cardObjective,
                          ArrayList<String> prerequisites) {
}
