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
     * empty String of the length of a card
     */
    private final static String empty="         ";

    /**
     *  map that contains the list of cards the Y axis corresponding to the key
     */
    private HashMap<Integer, ArrayList<CardCLI>> board;

    /**
     * prints current board formation
     */
    public void printBoard(){
        for(int i=maxY;i>=minY;i--){
            ArrayList<CardCLI> cardsInCurrentLine = board.get(i);
            int cursor=minX-1;
            for(CardCLI card: cardsInCurrentLine){
                int cardX=card.getX();
                if(cardX!=cursor){
                    for(int j=cursor+1;j>0;j--){
                        System.out.print(empty);
                    }
                }
                cursor=cardX;
                System.out.print(card.getLine());
            }
            System.out.print("\n");
        }
    }

    /**
     * adds card to the board
     * @param card that is being placed on the board
     */
    public void addCard(CardCLI card){
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
        cards.add(card);
        Collections.sort(cards);
        board.put(cardY, cards);
        coverCorners(card.getCoordinates());
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

    /**
     * replaces the covered symbols with '-'
     * @param coordinates of card that is placed and is covering the corners of surrounding cards
     */
    public void coverCorners(Coordinates coordinates){
        int coveredCardCorner=0;
        for(int i=0; i<4;i++){
            Coordinates coveredCornerCoordinates= CornerCoordinatesCalculator.cornerCoordinates(coordinates, i);
            CardCLI coveredCard=getCoveredCard(coveredCornerCoordinates);
            if(coveredCard!=null){
                coveredCardCorner=Math.abs(i-2);
                char[] visibleCorners=coveredCard.getVisibleCorners();
                visibleCorners[coveredCardCorner]='-';
            }
        }
    }



}
