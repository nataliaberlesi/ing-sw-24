package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.ArrayList;


public class ViewControllerCLI extends ViewController {


    private final PlayersInGameCLI playersInGame=new PlayersInGameCLI();
    private final ObjectivesSectionCLI objectivesSection=new ObjectivesSectionCLI();
    private final DrawableAreaCLI drawableArea=new DrawableAreaCLI();
    private PlayerCLI myPlayer;
    private PlayerCLI currentPlayerInScene;
    private final ClientActionsCLI clientActions=new ClientActionsCLI();
    private FinalScoreBoardCLI finalScoreBoard;
    private final HandleClientInputCLI clientInputHandler;
    private final ChatCLI chatRoom=new ChatCLI();

    public ViewControllerCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
        this.clientInputHandler=new HandleClientInputCLI(this, messageDispatcher, clientActions );
        Thread clientInputHandlerthread=new Thread(clientInputHandler);
        clientInputHandlerthread.start();
    }

    /**
     * creates instance of client player and adds player to playersInGame
     * @param username of client
     */
    @Override
    protected void createMyPlayer(String username) {
        PlayerCLI myPlayer= new PlayerCLI(username);
        this.myPlayer=myPlayer;
        this.currentPlayerInScene=myPlayer;
        clientActions.enableHelp();
    }

    public void setCurrentPlayerInScene(PlayerCLI currentPlayerInScene) {
        this.currentPlayerInScene = currentPlayerInScene;
    }


    /**
     * notifies client that view is awaiting server to check the username
     */
    @Override
    protected void switchWaitingServerResponse() {
        System.out.println("\nWaiting for server to check username...\n");
    }


    public PlayersInGameCLI getPlayersInGame() {
        return playersInGame;
    }

    /**
     * shows scene for player to create a game, where he needs to provide his username and number of players that will be playing
     */
    @Override
    protected void switchToCreate() {
        clientActions.enableCreate();
    }

    /**
     * loading screen notifying client to attend other players to join the game
     */
    @Override
    protected void switchToLoading(){
        System.out.println("All good\nWaiting for players...\n\n");
    }

    @Override
    protected void updatePlayerBoard(String affectedPlayer) {
        playersInGame.getPlayer(affectedPlayer).getPlayerBoard().updateBoard(messageParser.getPlacedCardsCLI());
    }

    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {
        playersInGame.getPlayer(affectedPlayer).setPlayerColor(color);
    }

    @Override
    protected void askCreateOrContinue() {
        clientActions.enableContinueGame();
        System.out.println("There is a saved game, do you want to continue?\n(type Y to continue or N to start a new game)");
    }

    @Override
    protected boolean playersInfoAlreadyAdded() {
        return playersInGame.getPlayers().isEmpty();
    }

    @Override
    protected boolean isMyPlayer(String username) {
        return myPlayer.getUsername().equalsIgnoreCase(username);
    }

    @Override
    protected void setPrivateObjective() {
        setPrivateObjective(messageParser.getChosenObjective());
    }

    @Override
    protected void addMessageToChat(String sender, String receiver, String bodyOfMessage, boolean isPrivate) {
        chatRoom.addMessage(sender, receiver, bodyOfMessage, isPrivate);
    }

    @Override
    protected void terminate() {
        clientInputHandler.terminate();
        System.exit(0);
    }

    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    protected void exit() {
        messageDispatcher.abortGame(myPlayer.getUsername(),"I don't want to play anymore :(");
        clearScreen();
        terminate();
    }

    @Override
    protected void showFinalScoreBoard() {
        clearScreen();
        finalScoreBoard.printFinalScoreBoard();
    }

    @Override
    protected void setFinalScoreBoard() {
        this.finalScoreBoard=messageParser.getFinalScoreBoardCLI();
    }

    @Override
    protected void setPrivateObjectiveChoice() {
        clientActions.setPrivateObjectiveChoices(messageParser.getPrivateObjectivesCLI());
        clientActions.enableShowOtherPlayerBoardAndBackOFHand();
    }

    @Override
    protected boolean isPlayerInGame(String username) {
        return playersInGame.isPlayerInGame(username);
    }

    @Override
    protected void updatePlayerScore(String username, int score) {
        playersInGame.getPlayer(username).setScore(score);
    }

    @Override
    protected void setPublicObjectives() {
        for(ObjectiveCLI objective:messageParser.getPublicObjectivesCLI()){
            objectivesSection.addObjective(objective);
        }
    }

    public void setPrivateObjective(ObjectiveCLI privateObjective){
        objectivesSection.setPrivateObjective(privateObjective);
    }

    @Override
    protected void updatePlayerHand(String username) {
        playersInGame.getPlayer(username).updateHand(messageParser.getHandCLI());
    }

    @Override
    protected void enablePlaceCard() {
        clientActions.enablePlaceCard();
    }

    @Override
    protected void enableDrawCard() {
        clientActions.enableDrawCard();
    }

    @Override
    protected void enableSecondRoundActions() {
        clientActions.enableChoosePrivateObjective();
    }



    /**
     * shows screen for player just connecting
     */
    @Override
    protected void connectScene() {
        clearScreen();
        System.out.println(
                """
                         _____  _____ ______  _____ __   __
                        /  __ \\|  _  ||  _  \\|  ___|\\ \\ / /
                        | /  \\/| | | || | | || |__   \\ V /\s
                        | |    | | | || | | ||  __|  /   \\\s
                        | \\__/\\\\ \\_/ /| |/ / | |___ / /^\\ \\
                         \\____/ \\___/ |___/  \\____/ \\/   \\/
                                                          \s""");
        connectPlayer();
    }

    /**
     * shows screen for player that is joining a game, here player will be asked to choose a username
     */
    @Override
    protected void switchToJoin() {
        clientActions.enableJoin();
    }


    @Override
    protected void showErrorAlert(String header, String content) {
        System.out.println(header);
        System.out.println(content);
    }

    /**
     *
     * @param usernameOfPlayerWhoseTurnItIs player who needs to play his/her turn
     * @return true if it is my clients turn
     */
    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs) {
        if(usernameOfPlayerWhoseTurnItIs.equalsIgnoreCase(myPlayer.getUsername())){
            System.out.println("It's your turn!");
            return true;
        }
        System.out.println("Wait for your turn...\nRemember you can type HELP at any moment to see all the commands you can use");
        return false;
    }

    /**
     *
     * @param usernameOfPlayerWhoseTurnItIs is the player whose turn it is, that player is set to current player and everyone else is set to not current player
     */
    @Override
    protected void setCurrentPlayer(String usernameOfPlayerWhoseTurnItIs) {
        for(PlayerCLI player: playersInGame.getPlayers()){
            player.setCurrentPlayer(player.getUsername().equalsIgnoreCase(usernameOfPlayerWhoseTurnItIs));
        }
    }

    /**
     * in the first round the client is given a starting card that he will place on his board in the orientation of his choice,
     * after that he will have to choose his color from the list of available colors. This method enables those actions.
     */
    @Override
    protected void enableFirstRoundActions() {
        clientActions.enablePlaceStartingCard();
    }

    private synchronized void printScene(){
        System.out.println("\n\n\n-----------------------------------------------------------------------------\n\n\n");
        playersInGame.printScores();
        objectivesSection.printObjectivesSection();
        drawableArea.printDrawableArea();
        String currentPlayerPossessiveForm=currentPlayerInScene.getUsername().toUpperCase()+"'S";
        System.out.println(currentPlayerPossessiveForm+" BOARD:");
        currentPlayerInScene.printBoard();
        System.out.println(currentPlayerPossessiveForm+" HAND:");
        if(currentPlayerInScene == myPlayer){
            System.out.println("hand facing up:");
            myPlayer.printHand();
        }
        System.out.println("hand facing down:");
        currentPlayerInScene.printBackOfHand();
        chatRoom.printRecentMessages();
    }

    @Override
    protected void showScene() {
        printScene();
    }

    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        clientActions.setAvailableColors(availableColors);
    }

    @Override
    protected void updateDrawableArea() {
        String resourceDrawableArea="resourceDrawableArea";
        String goldDrawableArea="goldDrawableArea";
        for(int i=0; i<3; i++){
            drawableArea.putResourceCard(i, messageParser.getCardCLI(resourceDrawableArea, i));
            drawableArea.putGoldCard(i, messageParser.getCardCLI(goldDrawableArea, i));
        }
    }

    @Override
    protected void giveInitialCard(String username) {
        try {
            playersInGame.getPlayer(username).getPlayerBoard().placeStartingCard(messageParser.getStartingCardCLI());
        }
        catch (RuntimeException e){
            //this happens at end of first round, when server doesn't give a new startingCard and a new currentPlayer
        }
    }

    /**
     * adds players in order in playersInGame, since my client is already inserted the method makes sure
     * that the order of players in input is respected in playersInGame
     * @param playerUsernames of all players in game, the list contains the names in the turn order
     * @throws RuntimeException if my player is not in the list of players
     */
    @Override
    protected void addPlayers(ArrayList<String> playerUsernames) throws RuntimeException{
        String myPlayerUsername= myPlayer.getUsername();
        if(!playerUsernames.contains(myPlayerUsername)){
            throw new RuntimeException("Player " + myPlayerUsername + " does not exist");
        }
        for(String playerUsername: playerUsernames){
            PlayerCLI player;
            if(playerUsername.equals(myPlayerUsername)){
                player=myPlayer;
            }
            else{
                player=new PlayerCLI(playerUsername);
            }
            playersInGame.addPlayer(player);
        }
        setCurrentPlayer(messageParser.getPlayers().getFirst());
    }

    public void printCompleteChat(){
        chatRoom.printCompleteChat();
    }

    public PlayerCLI getMyPlayer() {
        return myPlayer;
    }

}
