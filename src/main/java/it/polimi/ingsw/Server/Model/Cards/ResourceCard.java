package it.polimi.ingsw.Server.Model.Cards;

import it.polimi.ingsw.Server.Model.Cards.Objectives.CardObjective;
import it.polimi.ingsw.Server.Model.Cards.Objectives.SymbolController;
import it.polimi.ingsw.Server.Model.InvalidSymbolException;
import it.polimi.ingsw.Server.Model.Symbol;

import java.util.ArrayList;

/**
 * Resource is a Card that can be placed during the game after the first round
 * and can always be placed in placeable coordinates
 */
public class ResourceCard extends Card {

    /**
     * objective is the objective that will be activated once the card is placed giving the player that places it points
     * according to the cardObjective
     */
    private final CardObjective cardObjective;


    private static final Symbol[] backCorners={Symbol.BLANK,Symbol.BLANK,Symbol.BLANK,Symbol.BLANK};

    /**
     * @param cardId       unique identifier used to associate card to graphic resource
     * @param backSymbol   is a Symbol that is on the back of the card
     * @param frontCorners must be an array of 4 Symbols indicating the symbols on each front corner
     * @param cardObjective is the objective that will be activated once the card is placed
     */
    public ResourceCard(String cardId, Symbol backSymbol, Symbol[] frontCorners, CardObjective cardObjective) throws RuntimeException{
        super(cardId, backSymbol, frontCorners, backCorners);
        if(SymbolController.isNotBackSymbol(backSymbol)){
            throw new InvalidSymbolException(backSymbol+" can't be on the center back of a card");
        }
        this.cardObjective = cardObjective;
    }

    public CardObjective getCardObjective() {
        return cardObjective;
    }

    public ArrayList<Symbol> getPrerequisites(){
        return null;
    }
}
