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
                if(actionsCLI.isFirstRoundActionsEnabled()){
                    dealWithFirstCardPlacement();
                    String chosenColor=dealWithPlayerColorChoice();
                    actionsCLI.enableShowOtherPlayerBoardAndBackOFHand();
                    System.out.println("Great, now at any time if you want to see another players board (and back of hand) " +
                            "all you need to do is type:\n SHOW 'username'");
                    messageDispatcher.firstRound(viewController.getMyPlayer().getUsername(),viewController.getMyPlayer().getPlayerBoard().getStartingCard().isFaceUp(), chosenColor);
                    actionsCLI.disableFirstRoundActions();
                }
                if(actionsCLI.isCreateEnabled()){
                    checkUsernameAndNumberOfPlayersAsksAgainIfNotOK();
                }
                if(actionsCLI.isJoinEnabled()){
                    checkUsernameAsksAgainIfNotOk();
                }
                if(actionsCLI.isShowOtherPlayerBoardAndBackOFHandEnabled()){
                    String[] input=getInput().split(" ");
                    if(input.length>1 && input[0].equals("SHOW")){
                        boolean playerNotFound=true;
                        String username=input[1];
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
                }
            }
        }
    }


    /**
     *
     * @return client chosen number of players
     */
    private Integer askNumberOfPlayers(){
        System.out.println("Choose number of players:");
        return Integer.valueOf(getInput());
    }

    private void checkUsernameAsksAgainIfNotOk(){
        String username=getInput();
        if(viewController.checkParamsAndSendJoin(username)){
            actionsCLI.disableJoin();
            viewController.createMyPlayer(username);
        }
        else {
            viewController.switchToJoin();
        }
    }


    private void checkUsernameAndNumberOfPlayersAsksAgainIfNotOK(){
        String username=getInput();
        Integer numberOfPlayers = askNumberOfPlayers();
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
    private void dealWithFirstCardPlacement(){
        String input= getInput();
        switch (input){
            case "F"->{
                flipStartingCard();
                viewController.showScene();
                dealWithFirstCardPlacement();
            }
            case "PLACE"->{
                System.out.println("Congrats! You have placed your first card");
            }
            default -> {
                viewController.showErrorAlert("Command Not Recognized", "Remember 'F' is to flip the card and 'place' is to place the card\n ");
                dealWithFirstCardPlacement();
            }
        }
    }


    /**
     * shows client available colors to choose from
     * @return chosen color (if it is one of the available colors)
     */
    private String dealWithPlayerColorChoice(){
        ArrayList<String> availableColors= messageParser.getAvailableColors();
        System.out.println("Now it's time to choose your color:\nSimply type one of these colors to choose one");
        for(String availableColor: availableColors){
            System.out.println(availableColor);
        }
        String chosenColor=scanner.nextLine();
        if(availableColors.contains(chosenColor)){
            System.out.println("\nGreat choice\n");
            return chosenColor;
        }
        System.out.println("I'm afraid that color is unavailable, let's try again");
        return dealWithPlayerColorChoice();
    }


    private String getInput(){
        return scanner.nextLine().toUpperCase().strip();
    }
}
