package it.polimi.ingsw.Server.Controller.DTO;

import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.Symbol;

public record StartSecondRound(String currentPlayer,Card[] hand,  Objective firstPrivateObjective, Objective secondPrivateObjective, Objective firstPublicObjective, Objective secondPublicObjective) {
}
