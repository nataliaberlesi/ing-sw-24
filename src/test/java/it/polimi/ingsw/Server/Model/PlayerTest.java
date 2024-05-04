package it.polimi.ingsw.Server.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void setPlayerColor(){
        String color="RED";
        Player player=new Player("player1");
        player.setPlayerColor(Color.valueOf(color));
        assertEquals(Color.RED,player.getPlayerColor());
    }

}