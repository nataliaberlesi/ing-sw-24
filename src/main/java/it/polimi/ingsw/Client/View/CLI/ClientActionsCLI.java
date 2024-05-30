package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

/**
 * contains every action a player can take, actions can be enabled or disabled in different moments
 */
public class ClientActionsCLI {

    /**
     * true when client can flip and place starting card
     */
    private boolean placeStartingCard =false;
    /**
     * true when client can choose a color
     */
    private boolean chooseColor=false;
    /**
     * choose when client can place a card
     */
    private boolean placeCard=false;
    /**
     * true when client can see other players boards and back of hands
     */
    private boolean showOtherPlayerBoardAndBackOFHand=false;
    /**
     * true when client can draw card from drawingArea
     */
    private boolean drawCard=false;
    /**
     * true when client can choose private objective
     */
    private boolean choosePrivateObjective =false;
    /**
     * true if player can choose username to join game
     */
    private boolean join=false;
    /**
     * true if player is master and can create a game by selecting a username and number of players
     */
    private boolean create=false;
    /**
     * true if player can ask for help to see which commands he can use, false if game is over
     */
    private boolean help=false;
    /**
     * true when there is a game saved that the client can choose to continue or start a new game
     */
    private boolean continueGame=false;

    private boolean resizeBoard=false;
    /**
     * are the objectives that a player can choose from for his/her private objective
     */
    private ObjectiveCLI[] privateObjectiveChoices =new ObjectiveCLI[2];
    /**
     * are the current available colors that hte client can choose from
     */
    private ArrayList<String> availableColors;


    /**
     *
     * @param availableColors colors that player can choose from
     */
    public void enableChooseColor(ArrayList<String> availableColors){
        System.out.println(ClientOutputs.enableColorChoice);
        for(String availableColor: availableColors){
            System.out.println(availableColor);
        }
        chooseColor=true;
    }

    /**
     * disabled once player has chosen a color
     */
    public void disableChooseColor(){
        chooseColor=false;
    }

    /**
     * enabled when a game is saved a client can choose whether to continue it or start a new game
     */
    public void enableContinueGame(){
        continueGame=true;
    }

    /**
     *disabled once player has chosen whether to continue a saved game
     */
    public void disableContinueGame(){
        continueGame=false;
    }

    public boolean isResizeBoardEnabled(){
        return resizeBoard;
    }

    public void enableResizeBoard(){
        resizeBoard=true;
    }

    public boolean isContinueGameEnabled(){
        return continueGame;
    }
    public boolean isChooseColorEnabled(){
        return chooseColor;
    }

    /**
     * enabled when player can join a game
     */
    public void enableJoin() {
        System.out.println(ClientOutputs.chooseUsername);
        join = true;
    }

    /**
     * disabled once player has joined
     */
    public void disableJoin() {
        join = false;
    }

    public boolean isJoinEnabled() {
        return join;
    }

    public boolean isHelp() {
        return help;
    }

    /**
     * enabled if client is the first person to connect to server and decides to create a new game
     */
    public void enableCreate() {
        System.out.println(ClientOutputs.chooseUsernameAndNumberOfPlayers);
        create = true;
    }

    /**
     * disabled once client created a game
     */
    public void disableCreate() {
        create = false;
    }

    public boolean isCreateEnabled() {
        return create;
    }

    /**
     * enabled when it's players turn and has been assigned a starting card
     */
    public void enablePlaceStartingCard() {
        System.out.println(ClientOutputs.enablePlaceStartingCard);
        placeStartingCard = true;
    }

    /**
     * enabled when it's clients turn and must place a card p their board
     */
    public void enablePlaceCard() {
        System.out.println(ClientOutputs.enablePlaceCard);
        placeCard = true;
    }

    /**
     * enabled after client has placed starting card, it allows the client to see other players boards and back of hand
     */
    public void enableShowOtherPlayerBoardAndBackOFHand() {
        showOtherPlayerBoardAndBackOFHand = true;
    }

    /**
     * enabled when it's clients turn and they must draw a card
     */
    public void enableDrawCard() {
        System.out.println(ClientOutputs.enableDrawCard);
        drawCard = true;
    }

    /**
     * is enabled when client must choose between to objectives
     */
    public void enableChoosePrivateObjective() {
        System.out.println(ClientOutputs.enableChoosePrivateObjective);
        for (int i = 0; i < privateObjectiveChoices.length; i++) {
            System.out.println(i+1+")");
            privateObjectiveChoices[i].printObjective();
        }
        System.out.println(ClientOutputs.choosePrivateObjectiveInstructions);
        choosePrivateObjective = true;
    }

    public boolean isPlaceStartingCardEnabled() {
        return placeStartingCard;
    }
    public boolean isPlaceCardEnabled() {
        return placeCard;
    }
    public boolean isShowOtherPlayerBoardAndBackOFHandEnabled() {
        return showOtherPlayerBoardAndBackOFHand;
    }
    public boolean isDrawCardEnabled() {
        return drawCard;
    }
    public boolean isisChoosePrivateObjectiveEnabled() {
        return choosePrivateObjective;
    }

    /**
     * disabled once client has placed the starting card
     */
    public void disablePlaceStartingCard() {
        placeStartingCard = false;
    }

    /**
     * disabled once client has placed a card
     */
    public void disablePlaceCard() {
        placeCard = false;
    }

    /**
     * disabled once client has drawn a card
     */
    public void disableDrawCard() {
        drawCard = false;
    }

    /**
     * disabled once client has chosen an objective to keep
     */
    public void disableChoosePrivateObjective() {
        choosePrivateObjective = false;
    }

    /**
     *  updates the colors available for client
     * @param availableColors are the colors available for the client to choose from
     */
    public void setAvailableColors(ArrayList<String> availableColors) {
        this.availableColors = availableColors;
    }

    /**
     *
     * @param privateObjectiveChoices are the objectives the client can choose from
     */
    public void setPrivateObjectiveChoices(ObjectiveCLI[] privateObjectiveChoices) {
        this.privateObjectiveChoices = privateObjectiveChoices;
    }


    public ObjectiveCLI[] getPrivateObjectiveChoices() {
        return privateObjectiveChoices;
    }

    public ArrayList<String> getAvailableColors() {
        return availableColors;
    }

    public void enableHelp(){
        help = true;
    }


}
