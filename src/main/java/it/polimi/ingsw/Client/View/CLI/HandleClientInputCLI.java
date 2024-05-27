package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
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
     * volatile flag that thread checks regularly to determine if it should terminate
     */
    private boolean running=true;
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
     * used to read user inputs
     */
    Scanner scanner = new Scanner(System.in);


    /**
     *
     * @param viewController controls client scene
     * @param messageDispatcher used to send messages to server
     * @param actionsCLI determines which actions a player can take
     */
    HandleClientInputCLI(ViewControllerCLI viewController, MessageDispatcher messageDispatcher, ClientActionsCLI actionsCLI) {
        this.viewController = viewController;
        this.messageDispatcher = messageDispatcher;
        this.actionsCLI = actionsCLI;
    }

    /**
     * Listens to client and acts out his actions if they are enabled
     */
    @Override
    public void run() {
        while(running){
            if(scanner.hasNextLine()){
                String input = scanner.nextLine().strip();
                if(input.equalsIgnoreCase("EXIT")){
                   viewController.exit();
                }
                if(actionsCLI.isPlaceCardEnabled()){
                    dealWithCardPlacement(input);
                }
                if(actionsCLI.isDrawCardEnabled()){
                    dealWithDrawingCard(input);
                }
                if(actionsCLI.isShowOtherPlayerBoardAndBackOFHandEnabled()){
                    dealWithShowingOtherPlayerBoard(input);
                }
                if(actionsCLI.isContinueGameEnabled()){
                    dealWithContinueGame(input);
                }
                if(actionsCLI.isChooseColorEnabled()){
                    dealWithPlayerColorChoice(input.toUpperCase());
                }
                if(actionsCLI.isPlaceStartingCardEnabled()){
                    dealWithFirstCardPlacement(input.toUpperCase());
                }
                if(actionsCLI.isCreateEnabled()){
                    checkUsernameAndNumberOfPlayersAsksAgainIfNotOK(input);
                }
                if(actionsCLI.isJoinEnabled()){
                    checkUsernameAsksAgainIfNotOk(input);
                }
                if(actionsCLI.isisChoosePrivateObjectiveEnabled()){
                    dealWithPrivateObjectiveChoice(getIndex(input));
                }
                if(input.equalsIgnoreCase("HELP")){
                    helpPlayer();
                }
                if(!input.isEmpty() && (input.charAt(0)=='T' || input.charAt(0)=='t')){
                    sendMessageToChat(input);
                }
                if(input.equalsIgnoreCase("chat")){
                    viewController.printCompleteChat();
                }
            }
        }
        System.out.println(ClientOutputs.inputHandlerHasTerminated);
    }

    /**
     *
     * @param input should ether be y or n to indicate weather or not to continue game or start a new one
     */
    private void dealWithContinueGame(String input){
        if(input.equalsIgnoreCase("n")){
            continueGame(false);
        } else if (input.equalsIgnoreCase("y")) {
            continueGame(true);
        }
        else {
            System.out.println(ClientOutputs.continueGameAnswerNotValid);
        }
    }

    /**
     * change client screen to show the board of the player indicated by the input
     * @param input should come in the form of "show 'username'"
     */
    private void dealWithShowingOtherPlayerBoard(String input){
        String[] inputArray=input.split(" ");
        if(inputArray.length>1 && inputArray[0].equalsIgnoreCase("show")){
            showPlayerBoardAndHand(inputArray[1]);
        }
    }

    /**
     * takes index indicated by client and informs server of client choice
     * @param objectiveIndex should be 0 or 1
     */
    private void dealWithPrivateObjectiveChoice(int objectiveIndex) {
        if(objectiveIndex==0 || objectiveIndex==1){
            messageDispatcher.secondRound(viewController.getMyPlayer().getUsername(),objectiveIndex);
            viewController.setPrivateObjective(actionsCLI.getPrivateObjectiveChoices()[objectiveIndex]);
            System.out.println(ClientOutputs.validPrivateObjectiveChoice);
            actionsCLI.disableChoosePrivateObjective();
        }
        else{
            System.out.println(ClientOutputs.invalidPrivateObjectiveChoice);
        }
    }

    /**
     * informs server weather client wants to continue an existing game orr start a new one
     * @param continueGame is true if player wants to continue an existing game, false if player want's to start a new game
     */
    private void continueGame(boolean continueGame){
        messageDispatcher.notifyPersistence(continueGame);
        actionsCLI.disableContinueGame();
    }

    /**
     * sends server the message
     * @param input should be in the form "t -all/player2(receiver of message): message(body of message)"
     */
    private void sendMessageToChat(String input){
        String[] completeMessage= input.split(":");
        if(completeMessage.length>1) {
            String receiver = completeMessage[0].strip().split(" ")[1].toUpperCase();
            viewController.sendChatMessage(viewController.getMyPlayer().getUsername(), receiver, completeMessage[1]);
        }
        else {
            System.out.println(ClientOutputs.invalidChatMessageFormat);
        }
    }

    /**
     * shows player the different commands he can use
     */
    private void helpPlayer(){
        if(actionsCLI.isHelp()) {
            printInstructions();
        }
    }

    /**
     * terminates thread
     */
    public void terminate(){
        running=false;
    }

    /**
     *
     * @param inputArray players input where each word is an element in the array,
     *                   to place a card it should come in the form:
     *                   1 (index of card in hand that will be placed) 0,0(coordinates)
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
                        success=true;
                        actionsCLI.disablePlaceCard();
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
     * informs server of where and how a player wants to place a card
     * @param input should be in the form " 'place' 1/2/3(index of card in hand) 0,0(Coordinates) up/down(orientation)"
     */
    private void dealWithCardPlacement(String input){
        input=input.toUpperCase();
        String[] inputArray = input.split(" ");
        if(inputArray[0].equalsIgnoreCase("PLACE")){
            boolean success=false;
            if(inputArray.length > 3) {
                success=placeCard(Arrays.copyOfRange(inputArray,1, 4));
            }
            if(!success){
                System.out.println(ClientOutputs.invalidPlaceCardFormat);
            }
        }
    }

    /**
     * informs server of what card client wants to draw and player if params aren't correct
     * @param input should come in the form of " 'draw' gold/resource(type of card client wants to draw) 1/2/3(index of card in drawable area)"
     */
    private void dealWithDrawingCard(String input){
        input=input.toUpperCase();
        String[] inputArray = input.split(" ");
        if(inputArray[0].equalsIgnoreCase("DRAW")) {
            boolean success=false;
            if (inputArray.length > 2) {
                success=drawCard(Arrays.copyOfRange(inputArray,1, 3));

            }
            if(!success){
                System.out.println(ClientOutputs.invalidDrawCardFormat);
            }
        }
    }

    /**
     * prints out the different commands that the player can use and how to use them
     */
    private void printInstructions(){
        System.out.println(ClientOutputs.gameCommands);
    }

    /**
     * informs server of card player wants to place
     * @param inputArray should be in the form "gold/resource(type of card player wants to draw) 1/2/3(index of card in drawable area)"
     * @return true if params to draw card are correct
     */
    private boolean drawCard(String[] inputArray){
        boolean success=false;
        int index = getIndex(inputArray[1]);
        if (index >= 0 && index <= 2) {
            switch (inputArray[0]) {
                case "GOLD" -> {
                    success=true;
                    actionsCLI.disableDrawCard();
                    messageDispatcher.drawCard(viewController.getMyPlayer().getUsername(), index, "goldDrawableArea");
                }
                case "RESOURCE" -> {
                    success=true;
                    actionsCLI.disableDrawCard();
                    messageDispatcher.drawCard(viewController.getMyPlayer().getUsername(), index, "resourceDrawableArea");
                }
            }
        }
        return success;
    }

    /**
     *
     * @param coordinatesString coordinates written like this 0,0
     * @return string translated to actual coordinates
     */
    private Coordinates getCoordinates(String coordinatesString){
        coordinatesString=coordinatesString.replaceAll("[()]","");
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
     * displays the board and back of hand of the player chosen by the client
     * @param username of player that my client wants to see
     */
    private void showPlayerBoardAndHand(String username){
        username=username.toUpperCase();
        boolean playerNotFound=true;
        for(PlayerCLI player: viewController.getPlayersInGame().getPlayers()){
            if(player.getUsername().equals(username)){
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
     * checks weather chosen username fits regex, if so server is notified, else client will be asked to choose another username
     * @param username chosen by my player that will be assigned to him if server says it's ok
     */
    private void checkUsernameAsksAgainIfNotOk(String username){
        username=username.toUpperCase();
        if(viewController.checkParamsAndSendJoin(username)){
            actionsCLI.disableJoin();
            viewController.createMyPlayer(username);
        }
        else {
            viewController.switchToJoin();
        }
    }

    /**
     * checks if params given by client are correct, if so they will be sent to server, else he will be asked again
     * @param input is the username that client has chosen and the number of players he wants in the game
     */
    private void checkUsernameAndNumberOfPlayersAsksAgainIfNotOK(String input){
        input=input.toUpperCase();
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
     * reads user input and flips card or places it, if input is not understood client will be asked again, if card is placed the card can't be flipped anymore
     * and server is notified of placement orientation
     */
    private void dealWithFirstCardPlacement(String input){
        switch (input){
            case "F"->{
                flipStartingCard();
                viewController.showScene();
            }
            case "PLACE"->{
                actionsCLI.disablePlaceStartingCard();
                System.out.println(ClientOutputs.congratulationsOnFirstCard);
                actionsCLI.enableChooseColor(actionsCLI.getAvailableColors());
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
        if(actionsCLI.getAvailableColors().contains(chosenColor)){
            actionsCLI.disableChooseColor();
            sendStartingCardOrientationAndColorChoice(chosenColor);
            if(chosenColor.equals("GREEN")){
                System.out.println(ClientOutputs.confirmationOfUnoriginalColorChoice);
            }
            else{
                System.out.println(ClientOutputs.confirmationOfColorChoice);
            }
            actionsCLI.enableShowOtherPlayerBoardAndBackOFHand();
        }
        else {
            System.out.println(ClientOutputs.invalidColorChoice);
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
