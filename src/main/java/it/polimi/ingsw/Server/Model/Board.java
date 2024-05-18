package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.Cards.*;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * The Board acts like a dynamic matrix of coordinates where it is legal to place cards (placeable coordinates).
 * These coordinates are stored in a hashmap, where each coordinate is a key to a list of symbols that occupy those coordinates
 * (So if a card is later placed there, those symbols will be covered).
 * The board also stores a dynamic list of coordinates where it will always be illegal to place a card(unplaceable coordinates).
 * Each time a card is placed, it's "visible" corners will create new placeable coordinates (if not already present) and
 * it's "hidden" corners will create new unplaceable coordinates.
 * There is also a symbol counter (in the form of a hashmap) that keeps track of how many visible symbols are present on the board.
 * Every time a card is placed, the symbol counter will be updated by taking away the symbols that have been covered and adding the
 * symbols of the card that is being placed; each "visible" corner creates a new placeable coordinate (if it is not already existing or an unplaceable)
 * and each "hidden" corner will creat a new unplaceable coordinate (if it is not already existing) or "transforms" a placeable coordinate into an unplaceable one.
 */
public class Board {


    /**
     * score of the player that controls this board
     */
    private int score=0;
    /**
     * points earned by completing objectives
     */
    private int objectivesScore=0;
    /**
     * list of cards that have been laced on board, where they were placed and in which orientation
     */
    private final ArrayList<PlacedCard> placedCards=new ArrayList<>();
    /**
     * list of all coordinates where cards can't be placed
     */
    private final ArrayList<Coordinates> unplaceableCoordinates = new ArrayList<>();

    /**
     * map of all coordinates where cards are allowed to be placed
     */
    private final HashMap<Coordinates, ArrayList<Symbol>> placeableCoordinates = new HashMap<>();

    /**
     * map containing the number of visible occurrences for each symbol
     * Useful for objectives and card objectives that assign points based on the number of symbols present in the Board
     */
    private final HashMap<Symbol, Integer> visibleSymbolCounter = new HashMap<>();
    /**
     * ID used to save the starting card assigned to the player during the first round
     * When the player chooses to flip it, it becomes CARD_ALREADY_BE_PLACED
     */
    private String startingCardID;
    /**
     * ID used to save the starting card assigned to the player during the second round
     * When the player chooses which one of the two, they become both OBJECTIVE_ALREADY_CHOSEN
     */
    private String[] startingObjectivesID;

    /**
     * array of objectives that will assign points at the end of the game
     * the objectives stay the same throughout the game
     * there are only ever 3 objectives in the array
     * (2 common objectives for all players and a personal one for each player).
     * Some objectives calculate points based on patterns on the board,
     * others based on symbols that are visible on the board at the end of the game.
     * Objectives are updated throughout the game on what cards are placed and where, only pattern objectives save this information
     * and only if it is useful to them.
     */
    private final Objective[] objectives=new Objective[3];



    /**
     * a board when initiated will save the id of starting card that will be placed in coordinates 0,0 upon command
     * @param startingCardID id of starting card that will be placed
     * @throws IllegalArgumentException if id passed is not a starting card id
     */
    public Board(String startingCardID) throws IllegalArgumentException {
        this.startingObjectivesID=new String[2];
        if(!StartingCardFactory.isStartingCardID(startingCardID)){
            throw new IllegalArgumentException("Invalid card ID");
        }
        this.startingCardID =startingCardID;
    }
    /**
     * objectives are what give extra points at the end of the game, some objectives calculate points based on patterns on the board,
     * others based on symbols that are visible on the board at the end of the game.
     * Objectives are updated throughout the game on what cards are placed and where, only pattern objectives save this information
     * if it is useful to them.
     * @param objective that is being added to board, there can never be more than 3
     * @throws RuntimeException if more than 3 objectives are added to board
     */
    public void addObjective(String objective) throws RuntimeException{
        for(int i =0; i<3; i++){
            if(objectives[i]==null){
                objectives[i]=ObjectiveFactory.makeObjective(objective);
                return;
            }
        }
        throw new RuntimeException("more than three objectives have been assigned to player board");
    }
    public void choosePrivateObjective(int objectiveIndex) throws RuntimeException{
        String objectiveAlreadyBeenChosen="OBJECTIVE_ALREADY_BEEN_CHOSEN";
        if(objectiveIndex>1 || objectiveIndex<0) {
            throw new IndexOutOfBoundsException("There are only 2 starting objectives");
        }
        String objective=startingObjectivesID[objectiveIndex];
        if(Objects.equals(objective,objectiveAlreadyBeenChosen)) {
            throw new RuntimeException("OBJECTIVE_ALREADY_BEEN_CHOSEN");
        }
        addObjective(objective);
        this.setStartingObjectivesID(objectiveAlreadyBeenChosen, objectiveAlreadyBeenChosen);
    }
    /**
     * starting card will be placed in (0,0) in the orientation indicated.
     * Each corner of a startingCard creates new placeable or unplaceable coordinates for each corner, unplaceable if it is "hidden"
     * or placeable if it is "visible". If placed facing up it will also contain symbols in its center that are added to the
     * visibleSymbolCounter.
     * @param isFacingUp is the orientation that the starting card will have.
     * @throws RuntimeException if a starting card has already been placed or if other cards has been placed before
     */
    public void placeStartingCard(Boolean isFacingUp) throws RuntimeException {
        if(Objects.equals(startingCardID, "CARD_ALREADY_BEEN_PLACED")){
            throw new RuntimeException("The card has already been placed");
        }
        StartingCard startingCard =StartingCardFactory.makeStartingCard(this.startingCardID);
        Symbol[] corners=getVisibleCorners(startingCard, isFacingUp);
        // place starting card in coordinates (0,0) and creates new placeable and unplaceable coordinates
        placeCornerSymbolsSurroundingCoordinates(corners, new Coordinates());
        // add center symbols to visible counter if card is facing up
        if(isFacingUp){
            ArrayList<Symbol> centerSymbols= startingCard.getFrontCenterSymbols();
            for(Symbol centerSymbol: centerSymbols){
                updateVisibleCounter(centerSymbol);
            }
        }
        if(!placedCards.isEmpty()){
            throw new RuntimeException("Cards have been placed before starting card");
        }
        addPlacedCard(startingCard, new Coordinates(), isFacingUp);
        startingCardID="CARD_ALREADY_BEEN_PLACED";
    }

    /**
     *
     * @return points gained from objectives
     */
    public int getObjectivesScore() {
        return objectivesScore;
    }

    /**
     *
     * @param placedCard  card that was placed
     * @param coordinates where card was placed
     * @param isFacingUp orientation of card
     */
    private void addPlacedCard(Card placedCard, Coordinates coordinates, boolean isFacingUp) {
        placedCards.add(new PlacedCard(placedCard, coordinates, isFacingUp));
    }



    /**
     * places card in given coordinates if coordinates are contained in placeableCoordinates,
     * placing a card will create new placeable and unplaceable coordinates for each corner (placeable if corner is "visible"),
     * the symbols that the card covers by being placed will be removed from the visible symbol counter and the symbols visible
     * on the card will be added. Points earner by placing the card will be added to the score.
     * The objectives of the board will be updated on the card being placed.
     *
     * @param cardID is the ID of the card that is being placed
     * @param coordinates are the coordinates where the card will be placed
     * @return true if receives as an input valid coordinates and helper functions don't raise exceptions,
     * false if invalid coordinates are passed
     * @throws RuntimeException if a set of coordinates is occupied by more than 4 corners/symbols
     */
    public boolean placeCard(String cardID, Coordinates coordinates, boolean isFacingUp) throws RuntimeException{
        // checks weather the coordinates are placeable and if prerequisites are met (always true for resource cards or back facing cards)
        if(isPlaceable(coordinates)){
            ResourceCard card= ResourceCardFactory.makeResourceCard(cardID);
            //if card is facing down, it is unnecessary to check prerequisites
            if(!isFacingUp||card.checkPrerequisites(visibleSymbolCounter)){
                //gets the symbols on corners that will be covered
                ArrayList<Symbol> coveredSymbols=placeableCoordinates.get(coordinates);
                //removes covered symbols from the visibleSymbolCounter
                removeCoveredSymbolsFromSymbolCounter(coveredSymbols);
                //removes the coordinates where the card is being placed from the hashmap of placeableCoordinates.
                placeableCoordinates.remove(coordinates);
                //adds coordinates to unplaceableCoordinates
                unplaceableCoordinates.add(coordinates);
                Symbol[] cardVisibleCorners=getVisibleCorners(card, isFacingUp);
                //then for each corner it creates new placeable or unplaceable corners, or it adds symbols to existing ones
                placeCornerSymbolsSurroundingCoordinates(cardVisibleCorners, coordinates);
                //if the card is face down then the symbol in the center of the card is added to the visible symbol counter
                if(!isFacingUp){
                    updateVisibleCounter(card.getBackSymbol());
                }
                else{
                    //calculate points gained by placing card and adds it to the score
                    score+=card.getCardObjective().calculatePoints(visibleSymbolCounter,coveredSymbols.size());
                }
                //adds card and coordinates to occupiedCoordinates
                updateBoardObjectives(card.getBackSymbol(), coordinates);
                //adds card to placedCards
                addPlacedCard(card, coordinates, isFacingUp);
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @param coordinates where user wants to place card
     * @return true if it is possible to place a card in those coordinates
     */
    public boolean isPlaceable(Coordinates coordinates){
        return placeableCoordinates.containsKey(coordinates);
    }



    /**
     * updates the visibleSymbolCounter adding the new symbols that are now visible
     * @param newVisibleSymbol is a new symbol that is now being placed and is visible
     */
    private void updateVisibleCounter(Symbol newVisibleSymbol){
        int numberOfVisibleSymbols=0;
        if(visibleSymbolCounter.get(newVisibleSymbol)!=null){
            numberOfVisibleSymbols=visibleSymbolCounter.get(newVisibleSymbol);
        }
        visibleSymbolCounter.put(newVisibleSymbol, ++numberOfVisibleSymbols);

    }

    /**
     * when a card is placed it will inevitably cover at least one corner of a card that is already placed,
     * the symbols that are present on the corners that are being covered will be removed from the symbol counter by this method.
     * @param coveredSymbols list of symbols that are being covered
     * @throws RuntimeException more symbols are removed than present on the board
     */
    private void removeCoveredSymbolsFromSymbolCounter(ArrayList<Symbol> coveredSymbols) throws RuntimeException{
        for(Symbol symbolBeingCovered: coveredSymbols){
            int numberOfVisibleOccurrencesOfCoveredSymbol=visibleSymbolCounter.get(symbolBeingCovered)-1;
            if(numberOfVisibleOccurrencesOfCoveredSymbol<0){
                throw new RuntimeException("Impossible amount of visible symbols have been removed from board");
            }
            visibleSymbolCounter.put(symbolBeingCovered, numberOfVisibleOccurrencesOfCoveredSymbol);
        }
    }

    /**
     * adds currentCoordinates to unplaceableCoordinates and removes it from placeableCoordinates if present
     * @param unplaceable are the coordinates that are unplaceable that will be added to the list of unplaceableCoordinates
     */
    private void addUnplaceableCoordinates(Coordinates unplaceable){
        placeableCoordinates.remove(unplaceable);
        unplaceableCoordinates.add(unplaceable);
    }

    /**
     *
     * @param placeable are placeable coordinates that will be added to the list of placeableCoordinates if not already present
     * @param cornerSymbol is the symbol that occupies the placeable coordinate and will be added to the list of symbols that occupy the placeable coordinates
     * @throws RuntimeException if a set of coordinates is occupied by more than 4 corners/symbols
     */
    private void addSymbolsToPlaceableCoordinates(Coordinates placeable, Symbol cornerSymbol) throws RuntimeException{

        //will contain symbols that are already present in placeable coordinates
        ArrayList<Symbol> coordinatesSymbols = new ArrayList<>();

        //checking of symbols are already present in placeable coordinates
        if (placeableCoordinates.get(placeable) != null) {
            //gets symbols that already occupy the placeable coordinates
            coordinatesSymbols = placeableCoordinates.get(placeable);
            //the number of symbols already present in placeable coordinates is equal to the number of corners, that can never exceed 4
            // (including the on the method is about to add)
            if(coordinatesSymbols.size()>3){
                throw new RuntimeException("there are more than 4 corners occupying these coordinates: "+placeable);
            }
        }

        // adding currentSymbol symbol to the list of symbols occupying placeable coordinates, if no symbols where already present this operation is equivalent
        // to creating a new placeable coordinate and adding the cornerSymbol to it.
        coordinatesSymbols.add(cornerSymbol);
        placeableCoordinates.put(placeable , coordinatesSymbols);
    }

    /**
     * associates each corner (symbol) of the card that is being placed in the Board
     * to the adequate coordinates it points to
     * if a card's corned is FULL ("hidden"), it adds the corresponding pointed coordinates to the unplaceableCoordinates list
     * if a card's corner is "visible", it adds its symbol and pointed coordinates to the placeableCoordinates hashmap
     * @param corners are the visible corners of card being placed
     * @param coordinates location where the card is being placed
     * @throws RuntimeException if a set of coordinates is occupied by more than 4 corners/symbols
     */
    private void placeCornerSymbolsSurroundingCoordinates(Symbol[] corners, Coordinates coordinates) throws RuntimeException{

        // will store the corner that is being considered
        Symbol currentCorner;

        // will store the coordinates that the current corner occupies (not the coordinates of the card to which the corner belongs to,
        // but the coordinates where, if a card is later placed, will cover the current corner)
        Coordinates currentCornerCoordinates;

        for (int i = 0; i < 4; i++) {

            // getting the coordinates that the current corner occupies (not the coordinates of the card to which the corner belongs to,
            // but the coordinates where, if a card is later placed, will cover the current corner)
            currentCornerCoordinates = CornerCoordinatesCalculator.cornerCoordinates(coordinates, i);

            // getting the corner that is being considered
            currentCorner = corners[i];

            // checking if currentCoordinates is already in unplaceableCoordinates,
            // if so it is only necessary to update visibleSymbolCounter, so the body of if statement will be skipped
            if (!unplaceableCoordinates.contains(currentCornerCoordinates)) {

                //checking if the currentCorner is FULL, meaning the current corner coordinates must be added to unplacerable coordinates
                if (currentCorner.equals(Symbol.FULL)) {
                    //adds currentCoordinates to unplaceableCoordinates and removes it from placeableCoordinates
                    addUnplaceableCoordinates(currentCornerCoordinates);
                }

                // if the corner is not FULL then the coordinates of the corner are placeable
                else {
                    // adds currentCorner symbol to currentCornerCoordinates in placeableCoordinates
                    addSymbolsToPlaceableCoordinates(currentCornerCoordinates, currentCorner);
                }
            }

            updateVisibleCounter(currentCorner);
        }

    }


    /**
     * notifies board objective of what card is being placed (useful for objectives that calculate points based on patterns)
     * @param backSymbolOfCardBeingPlaced is the symbol in the center of the back of the card that is being placed
     * @param coordinatesOfWhereCardIsBeingPlaced are the coordinates of the card being placed
     */
    private void updateBoardObjectives(Symbol backSymbolOfCardBeingPlaced, Coordinates coordinatesOfWhereCardIsBeingPlaced){
        for(Objective objective:objectives){
            objective.updateObjective(backSymbolOfCardBeingPlaced,coordinatesOfWhereCardIsBeingPlaced);
        }
    }

    /**
     * calculates points gained from objectives and updates objectiveScore
     */
    private void calculateAndUpdateObjectiveScore(){
        for(Objective objective:objectives){
            objectivesScore+=objective.calculatePoints(visibleSymbolCounter);
        }
    }

    /**
     *
     * @return the current score plus the points gained from the objectives
     */
    public int getFinalScore(){
        calculateAndUpdateObjectiveScore();
        return score+objectivesScore;
    }

    /**
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param card that is being placed
     * @param isFacingUp indicates the orientation of card
     * @return the corners on the visible side of the card
     */
    private Symbol[] getVisibleCorners(Card card, boolean isFacingUp){
        if(isFacingUp){
            return card.getFrontCorners();
        }
           return  card.getBackCorners();
    }

    /**
     *
     * @return list of cards that have been placed on board
     */
    public ArrayList<PlacedCard> getPlacedCards() {
        return placedCards;
    }
    public void setStartingObjectivesID(String firstPrivateObjectiveID, String secondPrivateObjectiveID) {
        this.startingObjectivesID[0]=firstPrivateObjectiveID;
        this.startingObjectivesID[1]=secondPrivateObjectiveID;
    }

    public String seeStartingCardID() {
        return this.startingCardID;
    }

    public String seeFirstPrivateObjectiveID() {
        return startingObjectivesID[0];
    }
    public String seeSecondPrivateObjectiveID() {
        return startingObjectivesID[1];
    }
    public Objective seeFirstPublicObjective() {
        return objectives[0];
    }
    public Objective seeSecondPublicObjective() {
        return objectives[1];
    }

    public String seeStartingPrivateObjective(Integer index) {
        if(index<=startingObjectivesID.length && index>=0) {
            return startingObjectivesID[index];
        }
        throw new IndexOutOfBoundsException("Not so many starting private objectives");
    }

    public Objective seeObjective(int index) {
        if(index<=objectives.length && index>=0) {
            return objectives[index];
        }
        throw new IndexOutOfBoundsException("Not so many objectives");
    }
}
