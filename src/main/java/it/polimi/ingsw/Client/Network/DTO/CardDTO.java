package it.polimi.ingsw.Client.Network.DTO;

import java.util.ArrayList;

public record CardDTO(String cardID,
                      String backSymbol,
                      String[] frontCorners,
                      String[] backCorners,
                      ArrayList<String> frontCenterSymbols,
                      ObjectiveDTO cardObjective,
                      ArrayList<String> prerequisites) {
}
