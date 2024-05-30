package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.ArrayList;

/**
 * controller of CLI
 */
public class ViewControllerCLI extends ViewController {

    /**
     * list of all players currently in game
     */
    private final PlayersInGameCLI playersInGame=new PlayersInGameCLI();
    /**
     * contains all the objectives that belong to client
     */
    private final ObjectivesSectionCLI objectivesSection=new ObjectivesSectionCLI();
    /**
     * contains all the cards that can be drawn by players
     */
    private final DrawableAreaCLI drawableArea=new DrawableAreaCLI();
    /**
     * is player used by client
     */
    private PlayerCLI myPlayer;
    /**
     * is the player that is currently being shown to clint in his view, client can change player in view at any time
     */
    private PlayerCLI currentPlayerInScene;
    /**
     * contains all the actions a player can do and weather they are enabled or not
     */
    private final ClientActionsCLI clientActions=new ClientActionsCLI();
    /**
     * runs a separate thread that handles clint inputs
     */
    private final HandleClientInputCLI clientInputHandler;
    /**
     * contains all the messages sent by players
     */
    private final ChatCLI chatRoom=new ChatCLI();

    private int maxBoardWidths=133;

    /**
     * sets up CLI controller and starts input handler thread
     * @param messageParser parses messages sent by server
     * @param messageDispatcher dispatches messages to server
     */
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

    /**
     * client can change the player that is in his view to inspect other players boards and back of hand
     * @param currentPlayerInScene is the player that is currently in view of client
     */
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

    /**
     *
     * @return all players in game
     */
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
        System.out.println(ClientOutputs.loadingScreen);
    }

    /**
     *
     * @param affectedPlayer is player whose board will be updated
     */
    @Override
    protected void updatePlayerBoard(String affectedPlayer) {
        playersInGame.getPlayer(affectedPlayer).getPlayerBoard().updateBoard(messageParser.getPlacedCardsCLI());
    }

    /**
     *
     * @param affectedPlayer player whose color will be set
     * @param color chosen by affected player
     */
    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {
        playersInGame.getPlayer(affectedPlayer).setPlayerColor(color);
    }

    /**
     * asks whether client wants to continue an existing game or start a new one
     */
    @Override
    protected void askCreateOrContinue() {
        clientActions.enableContinueGame();
        System.out.println(ClientOutputs.askIfPlayerWantsToContinueGame);
    }

    /**
     * This method is called when a game is rejoined and server sends information to continue the game,
     * some information is sent multiple times but only needs to be inserted once.
     * This method checks whether that information has already been set.
     * @return true if information sent by server has already been dealt with
     */
    @Override
    protected boolean playersInfoNotYetAdded() {
        return playersInGame.getPlayers().isEmpty();
    }

    /**
     *
     * @param username of a player
     * @return true if username passed is the same as my clients username
     */
    @Override
    protected boolean isMyPlayer(String username) {
        return myPlayer.getUsername().equalsIgnoreCase(username);
    }

    /**
     * method is only called when a game is rejoined and it saves the private objective that was chosen by client before game was stopped
     */
    @Override
    protected void setPrivateObjective() {
        setPrivateObjective(messageParser.getChosenObjective());
    }

    /**
     * shows scene with updated chat
     */
    @Override
    protected void showUpdatedChat() {
        showScene();
    }

    /**
     * adds a message sent by player to chat
     * @param message that has been sent by a player
     */
    @Override
    protected void addMessageToChat(String message) {
        chatRoom.addMessage(message);
    }

    /**
     * terminates input handler thread and view controller
     */
    @Override
    protected void terminate() {
        clientInputHandler.terminate();
        System.exit(0);
    }

    /**
     * clears terminal screen
     */
    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * sends message to server notifying the client isn't playing anymore and terminates CLI controller
     */
    @Override
    protected void exit() {
        clearScreen();
        if(myPlayer==null){
            exit(null);
        }
        else {
            exit(myPlayer.getUsername());
        }
        terminate();
    }

    /**
     * gets final scoreboard from server and shows it to player
     */
    @Override
    protected void setAndShowFinalScoreBoard() {
        clearScreen();
        messageParser.getFinalScoreBoardCLI().printFinalScoreBoard();
    }

    public void moveBoardView(String direction){
        currentPlayerInScene.getPlayerBoard().moveView(direction);
        showScene();
    }

    public void resizeBoardWidths(int maxWidths){
        if(maxWidths>0) {
            this.maxBoardWidths = maxWidths;
            showScene();
        }
    }

    /**
     * all players are shown two private objectives but can only choose one, this method saves the two choices to show them to client
     */
    @Override
    protected void setPrivateObjectiveChoice() {
        clientActions.setPrivateObjectiveChoices(messageParser.getPrivateObjectivesCLI());
    }

    /**
     *
     * @param username of a possible player
     * @return true is username corresponds to a player in the game
     */
    @Override
    protected boolean isPlayerInGame(String username) {
        return playersInGame.isPlayerInGame(username);
    }

    /**
     *
     * @param username of player whose score needs to be updated
     * @param score updated score of player
     */
    @Override
    protected void updatePlayerScore(String username, int score) {
        playersInGame.getPlayer(username).setScore(score);
    }

    /**
     * sets the objective that apply to all players
     */
    @Override
    protected void setPublicObjectives() {
        for(ObjectiveCLI objective:messageParser.getPublicObjectivesCLI()){
            objectivesSection.addObjective(objective);
        }
    }

    /**
     * saves objective chosen by client
     * @param privateObjective objective chosen by client that only applies to client
     */
    public void setPrivateObjective(ObjectiveCLI privateObjective){
        clientActions.enableResizeBoard();
        clientActions.enableShowOtherPlayerBoardAndBackOFHand();
        objectivesSection.setPrivateObjective(privateObjective);
    }

    /**
     *
     * @param username of player whose hand needs to be updated
     */
    @Override
    protected void updatePlayerHand(String username) {
        playersInGame.getPlayer(username).updateHand(messageParser.getHandCLI());
    }

    /**
     * enables the possibility to place a card on the board
     */
    @Override
    protected void enablePlaceCard() {
        clientActions.enablePlaceCard();
    }

    /**
     * enables the possibility to draw a card
     */
    @Override
    protected void enableDrawCard() {
        clientActions.enableDrawCard();
    }

    /**
     * is it is the second round and it's the clients turn this method enables the client to choose his/her private objective
     */
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
        System.out.println(ClientOutputs.titleScreen);
        connectPlayer();
    }

    /**
     * shows screen for player that is joining a game, here player will be asked to choose a username
     */
    @Override
    protected void switchToJoin() {
        clientActions.enableJoin();
    }


    /**
     * prints out an error alert
     * @param header of error alert
     * @param content of error alert
     */
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
            System.out.println(ClientOutputs.itsClientsTurn);
            return true;
        }
        System.out.println(ClientOutputs.itsNotClientsPlayersTurn);
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
        clientActions.enableResizeBoard();
        clientActions.enablePlaceStartingCard();
    }

    /**
     * shows all current information relative to the game :
     * drawing section,
     * score board,
     * recent messages in chat,
     * board of player in view (client can choose which player he has in view)
     * hand of player in view (if player in view is not the client then only the back of the hand is shown)
     */
    private synchronized void printScene(){
        System.out.println("\n\n\n-----------------------------------------------------------------------------\n\n\n");
        playersInGame.printScores();
        objectivesSection.printObjectivesSection();
        drawableArea.printDrawableArea();
        String currentPlayerPossessiveForm=currentPlayerInScene.getUsername().toUpperCase()+"'S";
        System.out.println(currentPlayerPossessiveForm+" BOARD:");
        currentPlayerInScene.printBoard(maxBoardWidths);
        System.out.println(currentPlayerPossessiveForm+" HAND:");
        if(currentPlayerInScene == myPlayer){
            System.out.println("hand facing up:");
            myPlayer.printHand();
        }
        System.out.println("hand facing down:");
        currentPlayerInScene.printBackOfHand();
        chatRoom.printRecentMessages();
    }

    /**
     * shows main playing scene
     */
    @Override
    protected void showScene() {
        printScene();
    }

    /**
     * each player must choose a color dooring the setup of the game, this method updates which colors are still available for the player to choose from
     * @param availableColors colors still available for player to choose from
     */
    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
        clientActions.setAvailableColors(availableColors);
    }

    /**
     * updates the cards that can be drawn by players
     */
    @Override
    protected void updateDrawableArea() {
        String resourceDrawableArea="resourceDrawableArea";
        String goldDrawableArea="goldDrawableArea";
        for(int i=0; i<3; i++){
            drawableArea.putResourceCard(i, messageParser.getCardCLI(resourceDrawableArea, i));
            drawableArea.putGoldCard(i, messageParser.getCardCLI(goldDrawableArea, i));
        }
    }

    /**
     * each player is given a starting card that is placed in the center of their board, player must then choose whether the card should face up or down for the rest of the game
     * @param username of player that is currently receiving an initial card
     */
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

    /**
     * prints out all the messages sent in the chat
     */
    public void printCompleteChat(){
        chatRoom.printCompleteChat();
    }

    /**
     *
     * @return player corresponding to my client
     */
    public PlayerCLI getMyPlayer() {
        return myPlayer;
    }

}
