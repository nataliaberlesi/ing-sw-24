package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewControllerCLI extends ViewController {


    private PlayersInGameCLI playersInGame;
    private ObjectivesSectionCLI objectivesSection;
    private DrawableAreaCLI drawableArea;
    private PlayerCLI myPlayer;

    public ViewControllerCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
    }

    /**
     * creates instance of client player and adds player to playersInGame
     * @param username of client
     *
     */
    @Override
    protected void createMyPlayer(String username) {
        PlayerCLI myPlayer= new PlayerCLI(username, true);
        playersInGame = new PlayersInGameCLI();
        playersInGame.addPlayer(myPlayer);
        this.myPlayer=myPlayer;
    }

    /**
     * notifies client that view is awaiting server to check the username
     */
    @Override
    protected void switchWaitingServerResponse() {
        System.out.println("Waiting for server to check username...");
    }


    public PlayersInGameCLI getPlayersInGame() {
        return playersInGame;
    }


    @Override
    protected void startGame() {

    }

    @Override
    protected void startShow() {

    }

    /**
     *
     * @param scanner reading client response
     * @return client chosen username
     */
    private String askUsername(Scanner scanner){
        System.out.println("Choose a Username:");
        return scanner.nextLine();
    }

    /**
     *
     * @param scanner reading client response
     * @return client chosen number of players
     */
    private Integer askNumberOfPlayers(Scanner scanner){
        System.out.println("Choose number of players:");
        return scanner.nextInt();
    }

    /**
     * shows scene for player to create a game, where he needs to provide his username and number of players that will be playing
     */
    @Override
    protected void switchToCreate() {
        Scanner scanner = new Scanner(System.in);
        String username = askUsername(scanner);
        Integer numberOfPlayers = askNumberOfPlayers(scanner);
        if(!checkParamsAndSendCreate(username, numberOfPlayers)){
            switchToCreate();
        }
        createMyPlayer(username);
    }

    /**
     * loading screen notifying client to attend other players to join the game
     */
    @Override
    protected void switchToLoading(){
        System.out.println("All good\nWaiting for players...\n\n");
    }

    @Override
    protected void enablePlaceCard() {

    }

    @Override
    protected void enableDrawCard() {

    }

    @Override
    protected void enableSecondRoundActions() {

    }

    @Override
    protected void enablePlaceStartingCard() {

    }

    @Override
    protected void enableChooseColor() {

    }


    /**
     * shows screen for player just connecting
     */
    @Override
    protected void connectScene() {
        connectPlayer();
    }

    /**
     * shows screen for player that is joining a game, here player will be asked to choose a username
     */
    @Override
    protected void switchToJoin() {
        Scanner scanner = new Scanner(System.in);
        String username = askUsername(scanner);
        if (!checkParamsAndSendJoin(username)) {
            switchToJoin();
        }
    }

    @Override
    protected void updateGame() {

    }

    @Override
    protected void showErrorAlert(String header, String content) {
        System.out.println(header);
        System.out.println(content);
    }

    @Override
    protected void showAbort(String message) {

    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void displayWinners(List<String> winners) {

    }

    @Override
    protected void enableActions() {

    }

    @Override
    protected void waitTurn() {

    }

    @Override
    protected void returnToMainMenu() {

    }

    @Override
    public void main(String[] args) {

    }

    /**
     * in the first round the client is given a starting card that he will place on his board in the orientation of his choice,
     * after that he will have to choose his color from the list of available colors.
     */
    @Override
    protected void enableFirstRoundActions() {
        Scanner scanner = new Scanner(System.in);
        dealWithFirstCardPlacement(scanner);
        String chosenColor=dealWithPlayerColorChoice(scanner);
        messageDispatcher.firstRound(myPlayer.getUsername(),myPlayer.getPlayerBoard().getStartingCard().isFaceUp(), chosenColor);//see with Kevin
    }

    /**
     * shows client available colors to choose from
     * @param scanner that reads player input
     * @return chosen color (if it is one of the available colors)
     */
    private String dealWithPlayerColorChoice(Scanner scanner){
        ArrayList<String> availableColors= messageParser.getAvailableColors();
        System.out.println("Now it's time to choose your color:\nSimply type one of these colors to choose one");
        for(String availableColor: availableColors){
            System.out.println(availableColor);
        }
        String chosenColor=scanner.nextLine();
        if(availableColors.contains(chosenColor)){
            return chosenColor;
        }
        System.out.println("I'm afraid that color is unavailable, let's try again");
        return dealWithPlayerColorChoice(scanner);
    }

    /**
     * explains to client how to flip or place the starting card
     * @param scanner that reads player input
     */
    private void askPlayerToPlaceStartingCard(Scanner scanner){
        System.out.println("You have been assigned a starting card!");
        System.out.println("You can flip the card by typing 'F' ");
        System.out.println("When you are happy with the cards orientation type 'Place'");
        System.out.println("once placed you can't change card orientation");
        dealWithFirstCardPlacement(scanner);
    }

    /**
     * reads user input and flips card or places it accordingly, if input is not understood the method calls itself
     * @param scanner that reads player input
     */
    private void dealWithFirstCardPlacement(Scanner scanner){
        String input= scanner.nextLine().toLowerCase();
        switch (input){
            case "f"->{
                myPlayer.getPlayerBoard().getStartingCard().flip();
                showScene();
                dealWithFirstCardPlacement(scanner);
            }
            case "place"->{
                System.out.println("Congrats! You have placed your first card");
            }
            default -> {
                showErrorAlert("Command Not Recognized", "Remember 'F' is to flip the card and 'place' is to place the card ");
                dealWithFirstCardPlacement(scanner);
            }
        }
    }




    @Override
    protected void showScene() {
        System.out.println("\n\n\n-----------------------------------------------------------------------------\n");
        for(PlayerCLI player: playersInGame.getPlayers()){
            if(!player.isMyPlayer()){
                player.printPlayerSituation();
            }
        }
        playersInGame.printScores();
        drawableArea.printDrawableArea();
        objectivesSection.printObjectivesSection();
        playersInGame.getMyPlayer().printPlayerSituation();
    }

    @Override
    protected void setAvailableColors() {

    }

    @Override
    protected void setDrawableArea() {

    }

    @Override
    protected void giveInitialCard(String username) {
    }

    /**
     * adds players in order in playersInGame, since my client is already inserted the method makes sure
     * that the order of players in input is respected in playersInGame
     * @param playerUsernames of all players in game, the list contains the names in the turn order
     * @throws RuntimeException if my player is not in the list of players
     */
    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) throws RuntimeException{
        String myPlayerUsername= playersInGame.getMyPlayer().getUsername();
        if(!playerUsernames.contains(myPlayerUsername)){
            throw new RuntimeException("Player " + myPlayerUsername + " does not exist");
        }
        ArrayList<PlayerCLI> players=playersInGame.getPlayers();
        int i=0;
        while(!playerUsernames.get(i).equals(myPlayerUsername)){
            players.addFirst(new PlayerCLI(myPlayerUsername, false));
            i++;
        }
        i++;
        while(i<playerUsernames.size()){
            PlayerCLI newPlayer= new PlayerCLI(playerUsernames.get(i), false);
            players.add(new PlayerCLI(myPlayerUsername, false));
        }
    }

    /*
        ALWAYS VALID:
            EXIT
        ALWAYS VALID DOORING GAME:
            SHOW "NAME_OF_PLAYER"
        LOGIN:
            USERNAME
        LOGIN MASTER:
            USERNAME
            NUMBER OF PLAYERS
        DOORING GAME:
            SHOW "USERNAME"
        FIRST ROUND ANYTIME BEFORE PLACE:
            FLIP
        FIRST ROUND DOORING TURN:
            2)COLOR "color"
        SECOND ROUND:
            #OBJECTIVE (1/2)
         NORMAL ROUND:
            1)PLACE #NUMBER_OF_CARD_IN_HAND FACE_UP/FACE_DOWN
            2)DRAW RESOURCE_CARD/GOLD_CARD #INDEX_OF_CARD
     */


    public void doAction(String userInput){
        String[] args = userInput.split(" ");

        switch(args[0].toLowerCase()){
            case "draw"->{

            }
            case "show"->{
                try{
                    PlayerCLI newCurrentPlayerView=playersInGame.getPlayer(args[1]);
                    showScene();
                }
                catch (Exception e){
                    showErrorAlert(args[1]+ " not found", "try another username");
                }
                showScene();
            }
            case "exit"->{

            }
            case "flip"->{
                try{
                    playersInGame.getMyPlayer().getPlayerBoard().getStartingCard().flip();
                }
                catch (Exception ignored){

                }
                showScene();
            }
            case "color"->{

            }
            case "place"->{

            }
            case "objective"->{

            }


        }
    }

}
