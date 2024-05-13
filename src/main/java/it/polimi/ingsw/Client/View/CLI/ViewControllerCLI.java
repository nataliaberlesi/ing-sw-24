package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.List;
import java.util.Scanner;

public class ViewControllerCLI extends ViewController {


    private PlayersInGameCLI playersInGame;
    private ObjectivesSectionCLI objectivesSection;
    private DrawableAreaCLI drawableArea;


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
    }

    /**
     * loading screen notifying client to attend other players to join the game
     */
    @Override
    protected void switchToLoading(){
        System.out.println("All good\nWaiting for players...\n\n");
    }


    /**
     * shows screen for player just connecting
     */
    @Override
    protected void connectScene() {
        connectPlayer();
    }

    /**
     * shows screen for player that is joining a game, here player will be asked to chose a username
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

    @Override
    protected void enableFirstRoundActions() {

    }


    @Override
    protected void showScene() {
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
    protected void giveInitialCards() {

    }

    @Override
    protected void addPlayers() {

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
