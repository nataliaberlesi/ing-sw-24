package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Server.Model.Coordinates;

import java.util.Arrays;
import java.util.Scanner;

/**
 * while this class runs it is always listening to player inputs. If the input corresponds to an action the client can
 * take, then the action is carried out, if the input doesn't correspond to any action, or the parameters for that action
 * are incorrect the input is ignored.
 */
public class HandleClientInputCLI implements Runnable{

    /**
     * controls the scene of client
     */
    ViewControllerCLI viewController;
    /**
     * sends messages to server
     */
    MessageDispatcher messageDispatcher;
    /**
     * determine the actions that the user can carrie out
     */
    ClientActionsCLI actionsCLI;
    /**
     * hold the last message sent by server
     */
    MessageParser messageParser;
    /**
     * used to read user inputs
     */
    Scanner scanner = new Scanner(System.in);


    /**
     *
     * @param viewController controls client scene
     * @param messageParser used to read message from server
     * @param messageDispatcher used to send messages to server
     * @param actionsCLI determines which actions a player can take
     */
    HandleClientInputCLI(ViewControllerCLI viewController, MessageParser messageParser, MessageDispatcher messageDispatcher, ClientActionsCLI actionsCLI) {
        this.viewController = viewController;
        this.messageParser = messageParser;
        this.messageDispatcher = messageDispatcher;
        this.actionsCLI = actionsCLI;
    }

    /**
     * Listens to client and acts out his actions if they are enabled
     */
    @Override
    public void run() {
        while(true){
            if(scanner.hasNextLine()){
                String input = scanner.nextLine().toUpperCase().strip();
                if(actionsCLI.isPlaceCardEnabled()){
                    String[] inputArray = input.split(" ");
                    if(inputArray[0].equals("PLACE")){
                        if(inputArray.length > 3) {
                            if(!placeCard(Arrays.copyOfRange(inputArray,1, 4))){
                                System.out.println("remember to follow the instructions to place card.\n" +
                                        "If you need help, type help");
                            }
                        }
                    }
                }
                if(actionsCLI.isDrawCardEnabled()){
                    String[] inputArray = input.split(" ");
                    if(inputArray[0].equals("DRAW")) {
                        if (inputArray.length > 2) {
                            if(!drawCard(Arrays.copyOfRange(inputArray,1, 3))){
                                System.out.println("remeber to follow the instructions to draw card.\n" +
                                        "If you need help, type help");
                            }
                        }
                    }
                }
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
                if(actionsCLI.isisChoosePrivateObjectiveEnabled()){
                    int objectiveIndex=getIndex(input);
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

    /**
     *
     * @param inputArray players input where each word is an element in the array
     * @return true if all params are correct
     */
    private boolean placeCard(String[] inputArray){
        boolean success=false;
        int index = getIndex(inputArray[0]);
        if (index <= 2 && index >= 0) {
            Coordinates coordinates = getCoordinates(inputArray[1]);
            if (coordinates != null) {
                boolean isFaceUp;
                switch (inputArray[2]) {
                    case "UP" -> {
                        actionsCLI.disablePlaceCard();
                        success=true;
                        isFaceUp = true;
                        messageDispatcher.placeCard(viewController.getMyPlayer().getUsername(), isFaceUp, index, coordinates);
                    }
                    case "DOWN" -> {
                        success=true;
                        actionsCLI.disablePlaceCard();
                        isFaceUp = false;
                        messageDispatcher.placeCard(viewController.getMyPlayer().getUsername(), isFaceUp, index, coordinates);
                    }
                }
            }
        }
        return success;
    }

    /**
     *
     * @param inputArray players input where each word is an element in the array
     * @return true if params to draw card are correct
     */
    private boolean drawCard(String[] inputArray){
        boolean success=false;
        int index = getIndex(inputArray[0]);
        if (index >= 0 && index <= 2) {
            switch (inputArray[0]) {
                case "GOLD" -> {
                    success=true;
                    messageDispatcher.drawCard(viewController.getMyPlayer().getUsername(), index, "goldDrawableArea");
                }
                case "RESOURCE" -> {
                    success=true;
                    messageDispatcher.drawCard(viewController.getMyPlayer().getUsername(), index, "resourceDrawableArea");
                }
            }
        }
        return success;
    }


    /**
     *
     * @param coordinatesString coordinates written like this   (0,0)
     * @return string translated to actual coordinates
     */
    private Coordinates getCoordinates(String coordinatesString){
        coordinatesString=coordinatesString.replace("()","");
        String[] coordinatesArray=coordinatesString.split(",");
        if(coordinatesArray.length==2){
            try {
                return new Coordinates(Integer.parseInt(coordinatesArray[0]), Integer.parseInt(coordinatesArray[1]));
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     *
     * @param indexString index given by user, user will give an index in the form of a string that is +1 of the actual index in an array
     * @return index is what the user indicated transformed into a corresponding array index (-1 if user didn't write a number9
     */
    private int getIndex(String indexString){
        int index;
        try{
            index = Integer.parseInt(indexString)-1;
        }
        catch(NumberFormatException e){
            index = -1;
        }
        return index;
    }

    /**
     *
     * @param username of player that my client wants to see
     */
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

    /**
     *
     * @param username is username chosen by client
     */
    private void checkUsernameAsksAgainIfNotOk(String username){
        if(viewController.checkParamsAndSendJoin(username)){
            actionsCLI.disableJoin();
            viewController.createMyPlayer(username);
        }
        else {
            viewController.switchToJoin();
        }
    }

    /**
     *
     * @param input is composed of username chosen by client and number of players that will be in game
     */
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

    /**
     *
     * @param chosenColor is color chosen by client, if color is in the list of available colors then the choice is sent to server
     */
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

    /**
     *
     * @param chosenColor is color chosen by player and sent to server
     */
    private void sendStartingCardOrientationAndColorChoice(String chosenColor){
        messageDispatcher.firstRound(viewController.getMyPlayer().getUsername(),viewController.getMyPlayer().getPlayerBoard().getStartingCard().isFaceUp(), chosenColor);
    }

}
