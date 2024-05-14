package it.polimi.ingsw.Server.Controller.DTO;

import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Color;
import it.polimi.ingsw.Server.Model.DrawableArea;

import java.util.ArrayList;
import java.util.HashMap;

public record StartFirstRound(ArrayList<String> players, Card firstPlayerStartingCard, Card[] resourceDrawableArea,Card[] goldDrawableArea, ArrayList<Color> colors) {

}
