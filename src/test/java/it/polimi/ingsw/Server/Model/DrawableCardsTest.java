package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.Deck;
import it.polimi.ingsw.Server.Model.Cards.GoldCardFactory;
import it.polimi.ingsw.Server.Model.Cards.ResourceCardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DrawableCardsTest {
    DrawableCards goldDrawableCards;
    DrawableCards resourceDrawableCards;

    @BeforeEach
    void setUp() {
        Deck resourceDeck=new Deck(ResourceCardFactory.makeEveryResourceCardID());
        Deck goldDeck=new Deck(GoldCardFactory.makeEveryGoldCardID());
        goldDrawableCards=new DrawableCards(goldDeck);
        resourceDrawableCards=new DrawableCards(resourceDeck);
    }

    @Test
    void firstResourceCardInResourceDrawableCardsIsRR0(){
        assertEquals("RR0", resourceDrawableCards.drawCard(0));
    }

    @Test
    void firstGoldCardInGoldDrawableCardsIsRR0(){
        assertEquals("GR0", goldDrawableCards.drawCard(0));
    }

    @Test
    void goldDrawableCardsHaveAllOfTheCards(){
        ArrayList<String> allGoldCards=GoldCardFactory.makeEveryGoldCardID();
        assertTrue(checkEveryCard(allGoldCards,goldDrawableCards));
    }

    @Test
    void resourceDrawableCardsHaveAllOfTheCards(){
        ArrayList<String> allResourceCards=ResourceCardFactory.makeEveryResourceCardID();
        assertTrue(checkEveryCard(allResourceCards,resourceDrawableCards));
    }

    private boolean checkEveryCard(ArrayList<String> cards, DrawableCards drawableCards){
        while(true){
            String cardBeingDrawn=drawableCards.drawCard(0);
            if(cardBeingDrawn==null){
                cardBeingDrawn=drawableCards.drawCard(1);
                if(cardBeingDrawn==null){
                    cardBeingDrawn=drawableCards.drawCard(2);
                    if(cardBeingDrawn==null){
                        return cards.isEmpty();
                    }
                }
            }
            cards.remove(cardBeingDrawn);
        }
    }

}