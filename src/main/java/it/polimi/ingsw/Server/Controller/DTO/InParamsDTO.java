package it.polimi.ingsw.Server.Controller.DTO;

import it.polimi.ingsw.Server.Model.Coordinates;

public record InParamsDTO(String username,
                          Integer numberOfPlayers,
                          Boolean isFaceUp,
                          String color,
                          Integer index,
                          Coordinates coordinates,
                          String drawableSection,
                          String cause) {
}
