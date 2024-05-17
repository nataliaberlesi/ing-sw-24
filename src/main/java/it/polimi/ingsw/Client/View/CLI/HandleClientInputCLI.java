package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;

import java.util.ArrayList;
import java.util.Scanner;

public class HandleClientInputCLI implements Runnable{

    ViewControllerCLI viewController;
    MessageDispatcher messageDispatcher;
    ClientActionsCLI actionsCLI;
    MessageParser messageParser;
    Scanner scanner = new Scanner(System.in);


    HandleClientInputCLI(ViewControllerCLI viewController, MessageParser messageParser, MessageDispatcher messageDispatcher, ClientActionsCLI actionsCLI) {
        this.viewController = viewController;
        this.messageParser = messageParser;
        this.messageDispatcher = messageDispatcher;
        this.actionsCLI = actionsCLI;
    }
    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while(true){
            if(scanner.hasNextLine()){
                String input = getInput();
                if(actionsCLI.isShowOtherPlayerBoardAndBackOFHandEnabled()){
                    String[] inputArray=input.split(" ");
                    if(inputArray.length>1 && inputArray[0].equals("SHOW")){
                        showPlayerBoardAndHand(inputArray[1]);
                    }
                }
                if(actionsCLI.isChooseColorEnabled()){
                    dealWithPlayerColorChoice(input);
                }
                if(actionsCLI.isPlaceStartingCardEnabled()){
                    dealWithFirstCardPlacement(input);
                }
                if(actionsCLI.isCreateEnabled()){
                    checkUsernameAndNumberOfPlayersAsksAgainIfNotOK(input);
                }
                if(actionsCLI.isJoinEnabled()){
                    checkUsernameAsksAgainIfNotOk(input);
                }
                if(actionsCLI.isisChoosePrivateObjectiveEnabledEnabled()){
                    int objectiveIndex=Integer.parseInt(getInput())-1;
                    if(objectiveIndex==0 || objectiveIndex==1){
                        messageDispatcher.secondRound(viewController.getMyPlayer().getUsername(),objectiveIndex);
                        System.out.println("Hmm that looks like a tough objective, but ok, I'll let the server know");
                        actionsCLI.disableChoosePrivateObjective();
                    }
                    else{
                        System.out.println("try again, accepted indexes are either 1 or 2");
                    }
                }

            }
        }
    }

    private void showPlayerBoardAndHand(String username){
        boolean playerNotFound=true;
        for(PlayerCLI player: viewController.getPlayersInGame().getPlayers()){
            if(player.getUsername().toUpperCase().equals(username)){
                playerNotFound=false;
                viewController.setCurrentPlayerInScene(player);
                viewController.showScene();
            }
        }
        if(playerNotFound){
            System.out.println("Sorry I'm afraid that player is not present");
        }
    }


    private void checkUsernameAsksAgainIfNotOk(String username){
        if(viewController.checkParamsAndSendJoin(username)){
            actionsCLI.disableJoin();
            viewController.createMyPlayer(username);
        }
        else {
            viewController.switchToJoin();
        }
    }


    private void checkUsernameAndNumberOfPlayersAsksAgainIfNotOK(String input){
        String[] inputArray=input.split(" ");
        String username=inputArray[0];
        int numberOfPlayers;
        if(inputArray.length>1){
            try {
                numberOfPlayers = Integer.parseInt(inputArray[1]);
            }
            catch (NumberFormatException e) {
                numberOfPlayers=0;
            }
        }
        else{
            numberOfPlayers=0;
        }
        if(viewController.checkParamsAndSendCreate(username, numberOfPlayers)){
            actionsCLI.disableCreate();
        }
        else {
            viewController.switchToCreate();
        }
    }

    /**
     * flips the starting card on the board (this can only happen before the starting card is placed)
     */
    private void flipStartingCard(){
        viewController.getMyPlayer().getPlayerBoard().getStartingCard().flip();
    }

    /**
     * reads user input and flips card or places it accordingly, if input is not understood the method calls itself
     */
    private void dealWithFirstCardPlacement(String input){
        switch (input){
            case "F"->{
                flipStartingCard();
                viewController.showScene();
            }
            case "PLACE"->{
                actionsCLI.disablePlaceStartingCard();
                System.out.println("Congrats! You have placed your first card");
                actionsCLI.enableChooseColor(messageParser.getAvailableColors());
            }
            default -> {
                viewController.showErrorAlert("Command Not Recognized", "Remember 'F' is to flip the card and 'place' is to place the card\n ");
            }
        }
    }

    private void dealWithPlayerColorChoice(String chosenColor){
        if(messageParser.getAvailableColors().contains(chosenColor)){
            actionsCLI.disableChooseColor();
            sendStartingCardOrientationAndColorChoice(chosenColor);
            if(chosenColor.equals("GREEN")){
                System.out.println("\nGREEN IS NOT A CREATIVE COLOR\n");
            }
            else{
                System.out.println("\nGREAT CHOICE!\n");
            }
            actionsCLI.enableShowOtherPlayerBoardAndBackOFHand();
        }
        else {
            System.out.println("I'm afraid that color is unavailable, let's try again");
        }
    }

    private void sendStartingCardOrientationAndColorChoice(String chosenColor){
        messageDispatcher.firstRound(viewController.getMyPlayer().getUsername(),viewController.getMyPlayer().getPlayerBoard().getStartingCard().isFaceUp(), chosenColor);
    }


    private String getInput(){
        return scanner.nextLine().toUpperCase().strip();
    }
}
