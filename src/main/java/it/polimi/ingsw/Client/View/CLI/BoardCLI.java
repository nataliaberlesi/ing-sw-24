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

    private int currentView=0;


    /**
     *  map that contains the list of cards the Y axis corresponding to the key
     */
    private HashMap<Integer, ArrayList<CardCLI>> board=new HashMap<>();


    /**
     * when board is created, an empty card is placed in coordinates (0,0), this card will later be replaced by startingCard
     */
    public BoardCLI() {
        CardCLI emptyCard=new CardCLI();
        emptyCard.setCoordinates(new Coordinates());
        placeCard(emptyCard);
    }

    /**
     * prints the cards placed on board, or at least the ones that fit inside the max width.
     * The view of the board can be shifted to the left or to the right
     * @param widths max characters that can be printed in one line
     */
    public void printBoard(int widths){
        System.out.println("BOARD:\n\n");
        StringBuilder spacing= new StringBuilder();
        StringBuilder currentLine= new StringBuilder();
        for(int i=maxY;i>=minY;i--){
            ArrayList<CardCLI> cardsInCurrentLine = board.get(i);
            for(int k=0; k<4; k++) {
                currentLine.setLength(0);
                int cursor=minX-1;
                for (CardCLI card : cardsInCurrentLine) {
                    cursor=buildLine(card, cursor, spacing, currentLine, k);
                }
                currentLine = new StringBuilder(getSubstring(currentLine.toString(), currentView * widths /2, currentView * widths /2 + widths));
                if(!currentLine.toString().isBlank()) {
                    System.out.println(currentLine);
                    System.out.print(ColoredText.ANSI_RESET);
                }
            }
        }
        System.out.println("\n\n");
    }

    /**
     * takes the line of the card and adds it to the line of the board that is being printed
     * @param card cardin line being printed
     * @param cursor is used to indicate where to start building line
     * @param spacing is the spacing between cards
     * @param currentLine is the line being built
     * @param cardLine the number of the line of the card that is on the line being built
     */
    private int buildLine(CardCLI card, int cursor, StringBuilder spacing, StringBuilder currentLine, int cardLine){
        int cardX = card.getX();
        if (cardX != cursor) {
            spacing.append(CardIndexCLI.cardLength.repeat(Math.max(0, cardX - (cursor + 1))));
        }
        currentLine.append(spacing).append(card.getLine(cardLine));
        spacing.setLength(0);
        return  cardX;
    }

    /**
     *
     * @param string complete string
     * @param start starting index
     * @param end index
     * @return if possible, substring of complete string (from start to end), else an empty string
     */
    private String getSubstring(String string, int start, int end) {
        if (string.length() < end) {
            end = string.length();
        }
        if (start > string.length()) {
            return "";
        }
        return string.substring(start, end);
    }

    /**
     * moves the center view of the board ether to the right or to the left
     * @param direction ether r (right) or l (left)
     */
    public void moveView(String direction){
        if(direction.equalsIgnoreCase("r")){
            currentView++;
        }
        else if(direction.equalsIgnoreCase("l")){
            if(currentView>0){
                currentView--;
            }
        }
    }

    /**
     * updates board minX, minY and maxY, adds card to board and covers the corners of surrounding cards
     * @param card being placed
     */
    private void placeCard(CardCLI card){
        int cardY = card.getY();
        int cardX = card.getX();
        updateMinY(cardY);
        updateMaxY(cardY);
        updateMinX(cardX);
        addCardToBoardMap(card, cardY);
        coverCorners(card.getCoordinates());
    }

    /**
     * adds card to board map
     * @param card being placed
     * @param cardY y coordinate of card being placed
     */
    private void addCardToBoardMap(CardCLI card, int cardY){
        ArrayList<CardCLI> cards = board.get(cardY);
        if(cards==null){
            cards=new ArrayList<>();
        }
        cards.add(card);
        Collections.sort(cards);
        board.put(cardY, cards);
    }

    /**
     * updates y coordinate of the card that is farthest down on the board
     * @param newY y coordinate of card being placed
     */
    private void updateMinY(int newY){
        if(newY<minY){
            minY=newY;
        }
    }

    /**
     * updates y coordinate of the card that is farthest up on the board
     * @param newY y coordinate of card being placed
     */
    private void updateMaxY(int newY){
        if(newY>maxY){
            maxY=newY;
        }
    }

    /**
     * updates x coordinate farthest to the lest on the board
     * @param newX x coordinate of card being placed
     */
    private void updateMinX(int newX){
        if(newX<minX){
            minX=newX;
        }
    }


    /**
     *
     * @param startingCard first "real" card being placed, takes the spot of the empty card in (0,0)
     */
    public void placeStartingCard(CardCLI startingCard){
        this.startingCard=startingCard;
        startingCard.setCoordinates(new Coordinates());
        ArrayList<CardCLI> cards = new ArrayList<>();
        cards.add(startingCard);
        board.put(startingCard.getY(), cards);
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


    /**
     *
     * @param cornerOfCardThatIsCovering int corresponding to corner of the card that has been placed and is covering another corner
     * @return the int corresponding to the corner being covered
     * @throws IllegalArgumentException int corresponding to corner doesn't correspond to any existing corner (as corners are numbered from 0 to 3)
     */
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

    /**
     * each time a player successfully places a card the server sends an updated board containing all the cards that have been placed up to that point
     * @param cards all cards that have been placed on this board
     */
    public void updateBoard(ArrayList<CardCLI> cards){
        clearBoard();
        for(CardCLI card:cards){
            placeCard(card);
        }
    }

    /**
     * empties board
     */
    private void clearBoard(){
        board=new HashMap<>();
    }
}
