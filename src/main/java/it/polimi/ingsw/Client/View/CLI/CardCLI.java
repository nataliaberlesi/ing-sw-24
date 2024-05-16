package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Server.Model.Coordinates;

import java.util.ArrayList;
import java.util.Objects;

/**
 * card as shown in the CLI
 */
public class CardCLI implements Comparable<CardCLI>{


    /**
     * unique ID identifying specific card
     */
    private final String cardID;

    /**
     * color of card
     */
    private String cardColor;

    /**
     * ID for empty card
     */
    private final static String emptyCardID="EMPTY_CARD";
    /**
     * symbols on empty card
     */
    private final static char[] emptyCardCorners={'-','-','-','-'};
    /**
     * empty top/bottom card border
     */
    private static final String horizontalBorder="-------";
    /**
     * side border
     */
    private static final String verticalBorder="|";
    /**
     * bottom line is either empty or contains the specifics of the card objective
     * if card has an objective it shows like this: --*F---
     */
    private String bottomLine=horizontalBorder;
    /**
     * symbol on the back of the card
     */
    private String backSymbol="       ";
    /**
     * symbols on the front corners of the card
     */
    private char[] frontCorners;
    /**
     * symbols on the back corners of the card, in resource and gold cards are blank, starting cards have other symbols.
     */
    private char[] backCorners;
    /**
     * are only present in gold cards,
     * are shown like this: 3M,B  (three mushrooms and one butterfly)
     * gold cards show front center symbols instead of prerequisites
     */
    private String prerequisites="";

    /**
     * contains the points earned by card: --2pts-
     * if card doesn't earn points: -------
     */
    private String cardObjective= horizontalBorder;
    /**
     * coordinates of where the card is placed
     */
    private Coordinates coordinates;

    /**
     * indicates the orientation of the card
     */
    private boolean isFaceUp=true;

    /**
     * flips the card
     */
    public void flip(){
        isFaceUp=!isFaceUp;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }



    public String getCardID(){
        return cardID;
    }



    /**
     * the card ID indicates the color of card and ethe color determines which symbol is on the back of the card
     */
    private void setBackSymbolAndColor() {
        char backSymbol=cardID.charAt(1);
        switch (backSymbol) {
            case ('R'):
                backSymbol = 'M';
                cardColor=ColoredText.ANSI_RED;
                break;
            case ('B'):
                backSymbol='W';
                cardColor=ColoredText.ANSI_BLUE;
                break;
            case ('G'):
                backSymbol='L';
                cardColor=ColoredText.ANSI_GREEN;
                break;
            case ('P'):
                backSymbol='B';
                cardColor=ColoredText.ANSI_PURPLE;
                break;
            default:
                backSymbol=' ';
                break;
        }
        this.backSymbol = getProperSpacing(""+backSymbol);
    }

    public CardCLI(){
        this.cardID=emptyCardID;
        this.frontCorners=emptyCardCorners;
        this.backCorners=emptyCardCorners;
    }

    /**
     * constructor called for startingCards
     * @param cardID unique id of card
     * @param frontCorners symbols in the corners on the front of card
     * @param backCorners symbols in the corners on the back of card
     * @param frontCenterSymbols symbols in the center of the front of card
     */
    public CardCLI(String cardID, String[] frontCorners, String[] backCorners, ArrayList<String> frontCenterSymbols){
        this.cardID=cardID;
        setFrontCorners(frontCorners);
        setBackCorners(backCorners);
        setFrontCenterSymbols(frontCenterSymbols);
    }

    /**
     * constructor called for resource and gold cards
     * @param cardID unique id of card
     * @param frontCorners symbols in the corners on the front of card
     * @param backCorners symbols in the corners on the back of card
     * @param cardObjective objective of cards details how many points are earned when card is placed
     * @param prerequisites (null for resource cards) are the amount of visible symbols that need to be present on the board in order
     *                      to place card
     */
    public CardCLI(String cardID, String[] frontCorners, String[] backCorners, String cardObjective, String cardObjectiveSymbol, int cardObjectivePoints ,ArrayList<String> prerequisites){
        this.cardID=cardID;
        setFrontCorners(frontCorners);
        setBackCorners(backCorners);
        if(cardObjective!=null && cardObjectivePoints!=0) {
            setCardObjective(cardObjective, cardObjectiveSymbol, cardObjectivePoints);
        }
        if(!prerequisites.isEmpty()) {
            setPrerequisites(prerequisites);
        }
        setBackSymbolAndColor();
    }
    /**
     * saves the initials of the symbols on the front corners
     * @param frontCorners list of front corner symbols
     */
    public void setFrontCorners(String[] frontCorners) {
        this.frontCorners=getInitials(frontCorners);
    }

    /**
     * saves the initials of the symbols on the back corners
     * @param backCorners list of back corner symbols
     */
    public void setBackCorners(String[] backCorners) {
        this.backCorners=getInitials(backCorners);
    }

    /**
     * saves the number of symbols necessary to place card by their initials: 3B,L (three butterflies, one leaf)
     * @param prerequisites list of symbols that are necessary to be on the board in order to place card
     */
    public void setPrerequisites(ArrayList<String> prerequisites) {
        char firstPrerequisite=prerequisites.getFirst().charAt(0);
        char secondPrerequisite='0';
        int firstPrerequisiteCounter=0;
        for(String prerequisite:prerequisites){
            char prerequisiteChar=prerequisite.charAt(0);
            if(prerequisiteChar==firstPrerequisite){
                firstPrerequisiteCounter++;
            }
            else {
                secondPrerequisite=prerequisiteChar;
            }
        }
        this.prerequisites=this.prerequisites + firstPrerequisiteCounter + firstPrerequisite;
        if(secondPrerequisite!='0'){
            this.prerequisites=this.prerequisites+","+secondPrerequisite;
        }
    }



    /**
     *
     * @param symbols list of symbols fully spelled
     * @return initials of the symbols in the same order
     */
    public char[] getInitials(String[] symbols){
        char[] initials=new char[symbols.length];
        for(int i=0; i< symbols.length; i++){
            if(Objects.equals(symbols[i], "FULL")){
                initials[i]='-';
            }
            else if(Objects.equals(symbols[i], "BLANK")){
                initials[i]='O';
            }
            else {
                initials[i] = symbols[i].charAt(0);
            }
        }
        return initials;
    }

    /**
     *
     * @return corners that are visible according to orientation
     */
    public char[] getVisibleCorners(){
        if(isFaceUp){
            return frontCorners;
        }
        return backCorners;
    }

    /**
     *
     * @param array of characters indicating different symbols
     * @return a string containing each character from array seperated by a comma
     */
    public static String arrayToStringWithComma(char[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * saves the initials of the symbols on the front center of starting card
     * @param frontCenterSymbols are present only on gold cards
     */
    public void setFrontCenterSymbols(ArrayList<String> frontCenterSymbols) {
        String [] symbols=frontCenterSymbols.toArray(new String[0]);
        char [] initials =getInitials(symbols);
        prerequisites= arrayToStringWithComma(initials);
    }

    /**
     * saves cardObjective as it will be shown on the card
     * @param cardObjective of card
     * @throws RuntimeException if cardObjective doesn't exist
     */
    public void setCardObjective(String cardObjective, String cardObjectiveSymbol, int cardObjectivePoints) throws RuntimeException{
        makeCardObjectiveTopLine(cardObjectivePoints);
        switch (cardObjective){
            case("PointsCardObjective")-> {
            }
            case("SymbolObjective")-> {
                makeCardObjectiveBottomLine(cardObjectiveSymbol.toUpperCase().charAt(0));
            }
            case("CornerCardObjective")-> {
                makeCardObjectiveBottomLine('C');
            }
            default-> {
                throw new RuntimeException(cardObjective + " objective doesn't exist");
            }

        }

    }

    private void makeCardObjectiveBottomLine(char cardObjectiveSymbolInitial){
        StringBuilder objectiveTopLine=new StringBuilder();
        objectiveTopLine.append("--*");
        objectiveTopLine.append(cardObjectiveSymbolInitial);
        objectiveTopLine.append("---");
        this.bottomLine=objectiveTopLine.toString();
    }

    private void makeCardObjectiveTopLine(int cardObjectivePoints){
        StringBuilder objectiveTopLine=new StringBuilder();
        objectiveTopLine.append("--");
        objectiveTopLine.append(cardObjectivePoints);
        objectiveTopLine.append("pts");
        objectiveTopLine.append("-");
        this.cardObjective=objectiveTopLine.toString();
    }

    /**
     *
     * @param coordinates of where the card is being placed
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates=coordinates;
    }


    public int getX() {
        return coordinates.getX();
    }

    public int getY() {
        return coordinates.getY();
    }

    private String getCardObjective(){
        if(isFaceUp){
            return cardObjective;
        }
        return horizontalBorder;
    }

    /**
     *
     * @return top border of card containing top symbols on the edges and the points earned by card: M--2pts-B
     */
    public String getTopLine(){
        StringBuilder topLine=new StringBuilder();
        char[] visibleCorners=getVisibleCorners();
        topLine.append(visibleCorners[1]);
        topLine.append(getCardObjective());
        topLine.append(visibleCorners[0]);
        return topLine.toString();
    }

    /**
     *
     * @return coordinates that are spaced so that they are centered inside the card: "  0,0  "
     */
    private String getProperlySpacedCoordinates(){
        if(coordinates==null){
            return "       ";
        }
        String Xcoordinate= String.valueOf(coordinates.getX());
        String Ycoordinate= String.valueOf(coordinates.getY());
        return getProperSpacing(Xcoordinate+","+Ycoordinate);
    }


    /**
     *
     * @param string that needs to be centered
     * @return properly spaced and centered string, such that it can fit inside the card
     * @throws IllegalArgumentException if a string can't fit inside a card
     */
    private String getProperSpacing(String string) throws IllegalArgumentException{
        if (string.length() >= 7) {
            throw new IllegalArgumentException(string+" is too long");
        }
        int totalSpaces = 7 - string.length();
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;
        return String.valueOf(' ').repeat(leftSpaces) +
                string +
                String.valueOf(' ').repeat(Math.max(0, rightSpaces));
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    /**
     *
     * @return line containing coordinates (second line of card): |  0,0  |
     */
    private String getCoordinatesLine(){
        return verticalBorder+getProperlySpacedCoordinates()+verticalBorder;
    }

    /**
     *
     * @return third line containing prerequisites (if it's a gold card) | 2B,W  |, or front center symbols (if it's a starting card) | B,W,L |, empty if it's a resource card |       |
     * if the card is facing down a resource or gold card will show their back center symbol, a starting card will be empty
     */
    private String getPrerequisitesLine(){
        StringBuilder prerequisitesLine=new StringBuilder();
        prerequisitesLine.append('|');
        if(isFaceUp){
            prerequisitesLine.append(getProperSpacing(prerequisites));
            prerequisitesLine.append('|');
            return prerequisitesLine.toString();
        }
        prerequisitesLine.append(backSymbol);
        prerequisitesLine.append('|');
        return prerequisitesLine.toString();
    }

    /**
     *
     * @return visible bottom
     */
    private String getBottom(){
        if(isFaceUp){
            return bottomLine;
        }
        return horizontalBorder;
    }

    /**
     *
     * @return last line of the card that contains the bottom corner symbols, if the card has an objective the last line also contains the conditions
     * for getting the points on the card: W--*2C--U
     */
    private String getBottomLine(){
        StringBuilder bottomLine=new StringBuilder();
        char[] visibleCorners=getVisibleCorners();
        bottomLine.append(visibleCorners[2]);
        bottomLine.append(getBottom());
        bottomLine.append(visibleCorners[3]);
        return bottomLine.toString();
    }

    private String getColoredText(StringBuilder text){
        if(cardColor==null){
            return text.toString();
        }
        return cardColor+text+ColoredText.ANSI_RESET;
    }

    /**
     *
     * @return current line corresponding to the currentLine
     */
    public String getLine(int lineNumber){
        StringBuilder line=new StringBuilder();
        switch (lineNumber) {
            case 0 :
                line.append(getTopLine());
                break;
            case 1 :
                line.append(getCoordinatesLine());
                break;
            case 2 :
                line.append(getPrerequisitesLine());
                break;
            case 3 :
                line.append(getBottomLine());
                break;
            default :
                throw new RuntimeException("ERROR IN VIEW, IMPOSSIBLE LINE WAS QUERIED");
        }
        return getColoredText(line);

    }

    /**
     * card becomes face up regardless of previous orientation
     */
    public void makeFaceUp(){
        isFaceUp=true;
    }

    /**
     * card becomes face down regardless of previous orientation
     */
    public void makeFaceDown(){
        isFaceUp=false;
    }


    /**
     * used to compare cards in order to have them in ascending order
     * @param other the object to be compared.
     * @return a negative integer if the first card has a smaller x coordinate than the second card x coordinate
     * 0 if the first card's x coordinate is equal to the second card's x coordinate
     * a positive integer if the first card has a bigger x coordinate than the second card x coordinate
     *
     */
    @Override
    public int compareTo(CardCLI other) {
        return Integer.compare(this.coordinates.getX(), other.getX());
    }

}
