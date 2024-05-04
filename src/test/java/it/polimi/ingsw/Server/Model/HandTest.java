package it.polimi.ingsw.Server.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void puttingCardInPosition0AndGettingCardFromPosition0ReturnsSameCard() {
        Hand hand=new Hand();
        hand.placeCardInHand("RR0");

        assertEquals(hand.showCardInHand(0), hand.getCardFromHand(0));
    }

    @Test
    void puttingCardInPosition2AndGettingCardFromPosition2ReturnsSameCard() {
        Hand hand=new Hand();
        hand.placeCardInHand("RR0");
        hand.placeCardInHand("RR1");
        hand.placeCardInHand("RR2");

        assertEquals(hand.showCardInHand(2), hand.getCardFromHand(2));
    }

    @Test
    void puttingCardInPosition1AndGettingCardFromPosition1ReturnsSameCard() {
        Hand hand=new Hand();
        hand.placeCardInHand("RR0");
        hand.placeCardInHand("RR1");
        hand.placeCardInHand("RR2");
        hand.getCardFromHand(1);
        hand.placeCardInHand("RG9");

        assertEquals("RG9", hand.getCardFromHand(1));
    }

    @Test
    void PuttingCardInFullHandThrowsException(){
        Hand hand=new Hand();
        hand.placeCardInHand("RR0");
        hand.placeCardInHand("RR1");
        hand.placeCardInHand("RR2");
        assertThrows(RuntimeException.class, ()->hand.placeCardInHand("RR3"));
    }

}