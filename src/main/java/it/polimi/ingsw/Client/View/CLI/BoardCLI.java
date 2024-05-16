package it.polimi.ingsw.Client.View.CLI;


import it.polimi.ingsw.Server.Model.Coordinates;
import it.polimi.ingsw.Server.Model.CornerCoordinatesCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * board as shown in the CLI
 */
public class BoardCLI {

    /**
     * Y coordinate of the highest cards
     */
    private int maxY=0;
    /**
     * Y coordinate of the lowest cards
     */
    private int minY=0;

    /**
     * X coordinate of the left most cards
     */
    private int minX=0;

    /**
     * first card that is placed on the board
     */
    private CardCLI startingCard;


    /**
     *  map that contains the list of cards the Y axis corresponding to the key
     */
    private final HashMap<Integer, ArrayList<CardCLI>> board=new HashMap<>();

    /**
     * prints current board formation
     */
    public void printBoard(){
        System.out.println("BOARD:");
        StringBuilder spacing= new StringBuilder();
        for(int i=maxY;i>=minY;i--){
            ArrayList<CardCLI> cardsInCurrentLine = board.get(i);

            for(int k=0; k<4; k++) {
                int cursor=minX-1;
                for (CardCLI card : cardsInCurrentLine) {
                    int cardX = card.getX();
                    if (cardX != cursor) {
                        spacing.append(CardIndexCLI.cardLength.repeat(Math.max(0, cardX - (cursor + 1))));
                    }
                    cursor = cardX;
                    System.out.print(spacing+card.getLine(k));
                }
                System.out.println();
                spacing.setLength(0);
            }
        }
    }

    /**
     * adds card to the board
     * @param card that is being placed on the board
     */
    public void placeCard(CardCLI card, Coordinates coordinates){
        card.setCoordinates(coordinates);
        int cardY = card.getY();
        int cardX = card.getX();
        if(cardY<minY){
            minY=cardY;
        }
        if(cardY>maxY){
            maxY=cardY;
        }
        if(cardX<minX){
            minX=cardX;
        }
        ArrayList<CardCLI> cards = board.get(cardY);
        if(cards==null){
            cards=new ArrayList<>();
        }
        cards.add(card);
        Collections.sort(cards);
        board.put(cardY, cards);
        coverCorners(card.getCoordinates());
    }

    /**
     *
     * @param startingCard first card to place
     */
    public void placeStartingCard(CardCLI startingCard){
        this.startingCard=startingCard;
        placeCard(startingCard, new Coordinates());
    }


    public CardCLI getStartingCard() {
        return startingCard;
    }

    /**
     *
     * @param coveredCornerCoordinates are coordinates of card that might be covered
     * @return null if no card is found in that position, otherwise it returns the covered card
     */
    private CardCLI getCoveredCard(Coordinates coveredCornerCoordinates){
        int cornerY=coveredCornerCoordinates.getY();
        if(board.get(cornerY)!=null) {
            ArrayList<CardCLI> coveredCardLine= board.get(cornerY);
            for (CardCLI card : coveredCardLine) {
                if (card.getCoordinates().equals(coveredCornerCoordinates)) {
                    return card;
                }
            }
        }
        return null;
    }


    private int getCoveredCardCorner(int cornerOfCardThatIsCovering) throws IllegalArgumentException{
        if(cornerOfCardThatIsCovering==0||cornerOfCardThatIsCovering==1){
            return cornerOfCardThatIsCovering+2;
        }
        if(cornerOfCardThatIsCovering==2||cornerOfCardThatIsCovering==3){
            return cornerOfCardThatIsCovering-2;
        }
        throw new IllegalArgumentException(cornerOfCardThatIsCovering+" is not a valid corner number");
    }
    /**
     * replaces the covered symbols with '-'
     * @param coordinates of card that is placed and is covering the corners of surrounding cards
     */
    public void coverCorners(Coordinates coordinates){
        int coveredCardCorner;
        for(int i=0; i<4;i++){
            Coordinates coveredCornerCoordinates= CornerCoordinatesCalculator.cornerCoordinates(coordinates, i);
            CardCLI coveredCard=getCoveredCard(coveredCornerCoordinates);
            if(coveredCard!=null){
                coveredCardCorner=getCoveredCardCorner(i);
                char[] visibleCorners=coveredCard.getVisibleCorners();
                visibleCorners[coveredCardCorner]='-';
            }
        }
    }




}
