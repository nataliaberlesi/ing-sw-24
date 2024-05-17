package it.polimi.ingsw.Client.View.CLI;

import java.util.ArrayList;

public class ClientActionsCLI {


    private boolean placeStartingCard =false;
    private boolean chooseColor=false;
    private boolean placeCard=false;
    private boolean flipHand=false;
    private boolean showOtherPlayerBoardAndBackOFHand=false;
    private boolean drawCard=false;
    private boolean choosePrivateObjective =false;
    private boolean join=false;
    private boolean exit=false;
    private boolean create=false;
    private boolean chooseNumberOfPlayers=false;


    public void enableChooseNumberOfPlayers(){
        chooseNumberOfPlayers=true;
    }

    public void disableChooseNumberOfPlayers(){
        chooseNumberOfPlayers=false;
    }

    public boolean isChooseNumberOfPlayersEnabled() {
        return chooseNumberOfPlayers;
    }

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

    public void enableExit() {
        exit = true;
    }

    public void disableExit() {
        exit = false;
    }

    public boolean isExitEnabled() {
        return exit;
    }

    public void enableCreate() {
        System.out.println("Choose your username and number of players:");
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
        placeCard = true;
    }

    public void enableFlipHand() {
        flipHand = true;
    }
    public void enableShowOtherPlayerBoardAndBackOFHand() {
        System.out.println("Now at any time if you want to see another players board (and back of hand) " +
                "all you need to do is type:\nSHOW 'username'");
        showOtherPlayerBoardAndBackOFHand = true;
    }
    public void enableDrawCard() {
        drawCard = true;
    }
    public void enableChoosePrivateObjective(ObjectiveCLI[] privateObjectives) {
        System.out.println("""
                If you look up you will see the objectives for this game.
                Those objectives are valid for all players,
                but each of you can choose a private objective.
                You can choose from these two:""");
        for (int i = 0; i < privateObjectives.length; i++) {
            System.out.println(i+1+")");
            privateObjectives[i].printObjective();
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

    public boolean isFlipHandEnabled() {
        return flipHand;
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

    public void disableFlipHand() {
        flipHand = false;
    }
    public void disableShowOtherPlayerBoardAndBackOFHand() {
        showOtherPlayerBoardAndBackOFHand = false;
    }
    public void disableDrawCard() {
        drawCard = false;
    }
    public void disableChoosePrivateObjective() {
        choosePrivateObjective = false;
    }

}
