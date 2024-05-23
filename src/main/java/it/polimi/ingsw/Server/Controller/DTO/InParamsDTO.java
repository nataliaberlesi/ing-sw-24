package it.polimi.ingsw.Server.Controller.DTO;

import it.polimi.ingsw.Server.Model.Coordinates;

public record InParamsDTO(String username,
                          Integer numberOfPlayers,
                          Boolean isFacingUp,
                          String color,
                          Integer index,
                          Coordinates coordinates,
                          String drawableSection,
                          String cause,
                          Boolean persistence) {
}
