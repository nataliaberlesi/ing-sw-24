package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

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

    private boolean continueGame=false;

    private ObjectiveCLI[] privateObjectiveChoices =new ObjectiveCLI[2];

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

    public void disableChooseColor(){
        chooseColor=false;
    }

    public void enableContinueGame(){
        continueGame=true;
    }

    public void disableContinueGame(){
        continueGame=false;
    }

    public boolean isContinueGameEnabled(){
        return continueGame;
    }
    public boolean isChooseColorEnabled(){
        return chooseColor;
    }

    public void enableJoin() {
        System.out.println(ClientOutputs.chooseUsername);
        join = true;
    }

    public void disableJoin() {
        join = false;
    }

    public boolean isJoinEnabled() {
        return join;
    }

    public boolean isHelp() {
        return help;
    }

    public void enableCreate() {
        System.out.println(ClientOutputs.chooseUsernameAndNumberOfPlayers);
        create = true;
    }

    public void disableCreate() {
        create = false;
    }

    public boolean isCreateEnabled() {
        return create;
    }
    public void enablePlaceStartingCard() {
        System.out.println(ClientOutputs.enablePlaceStartingCard);
        placeStartingCard = true;
    }
    public void enablePlaceCard() {
        System.out.println(ClientOutputs.enablePlaceCard);
        placeCard = true;
    }
    public void enableShowOtherPlayerBoardAndBackOFHand() {
        showOtherPlayerBoardAndBackOFHand = true;
    }
    public void enableDrawCard() {
        System.out.println(ClientOutputs.enableDrawCard);
        drawCard = true;
    }
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

    public void disablePlaceStartingCard() {
        placeStartingCard = false;
    }
    public void disablePlaceCard() {
        placeCard = false;
    }

    public void disableDrawCard() {
        drawCard = false;
    }
    public void disableChoosePrivateObjective() {
        choosePrivateObjective = false;
    }
    public void setAvailableColors(ArrayList<String> availableColors) {
        this.availableColors = availableColors;
    }

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
