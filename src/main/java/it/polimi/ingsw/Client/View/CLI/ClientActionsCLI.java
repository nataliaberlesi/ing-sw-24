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

    private ObjectiveCLI[] privateObjectiveChoices =new ObjectiveCLI[2];

    private ArrayList<String> availableColors;

    /**
     *
     * @param availableColors colors that player can choose from
     */
    public void enableChooseColor(ArrayList<String> availableColors){
        System.out.println("Now it's time to choose your color:\nSimply type one of these colors to choose one");
        for(String availableColor: availableColors){
            System.out.println(availableColor);
        }
        chooseColor=true;
    }
    public void disableChooseColor(){
        chooseColor=false;
    }

    public boolean isChooseColorEnabled(){
        return chooseColor;
    }

    public void enableJoin() {
        System.out.println("Choose your username");
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
        System.out.println("Choose your username and number of players (min 2, max 4):");
        create = true;
    }

    public void disableCreate() {
        create = false;
    }

    public boolean isCreateEnabled() {
        return create;
    }
    public void enablePlaceStartingCard() {
        System.out.println("""
                You have been assigned a starting card!
                You can flip the card by typing 'F'
                When you are happy with the cards orientation type 'Place'
                once placed you can't change card orientation""");
        placeStartingCard = true;
    }
    public void enablePlaceCard() {
        System.out.println("""
                Place a card
                type "place", then the index of card in your hand you want to place,
                then the coordinates of where you want to place them in the form (0,0)
                and then UP or DOWN depending if you want the card face up or not""");
        placeCard = true;
    }
    public void enableShowOtherPlayerBoardAndBackOFHand() {
        System.out.println("Now at any time if you want to see another players board (and back of hand) " +
                "all you need to do is type:\nSHOW 'username'");
        showOtherPlayerBoardAndBackOFHand = true;
    }
    public void enableDrawCard() {
        System.out.println("""
                Draw a card
                type "draw", then gold/resource,
                and then the index of the card you want to draw""");
        drawCard = true;
    }
    public void enableChoosePrivateObjective() {
        System.out.println("""
                If you look up you will see the objectives for this game.
                Those objectives are valid for all players,
                but each of you can choose a private objective.
                You can choose from these two:""");
        for (int i = 0; i < privateObjectiveChoices.length; i++) {
            System.out.println(i+1+")");
            privateObjectiveChoices[i].printObjective();
        }
        System.out.println("Type 1 to choose the first one, type 2 to choose the second one");
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
