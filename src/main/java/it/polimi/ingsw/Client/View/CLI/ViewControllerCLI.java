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

    public ViewControllerCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
        HandleClientInputCLI handleClientInput=new HandleClientInputCLI(this,messageParser, messageDispatcher, clientActions );
        Thread clientInputHandlerthread=new Thread(handleClientInput);
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
     * asks player to choose a username
     */
    private void askUsername(){
        System.out.println("Choose a Username:");
    }

    /**
     * shows scene for player to create a game, where he needs to provide his username and number of players that will be playing
     */
    @Override
    protected void switchToCreate() {
        clientActions.enableCreate();
        askUsername();
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

    }

    @Override
    protected void setPlayerColor(String affectedPlayer, String color) {
        playersInGame.getPlayer(affectedPlayer).setPlayerColor(color);
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
        clientActions.enableJoin();
        askUsername();
    }


    @Override
    protected void showErrorAlert(String header, String content) {
        System.out.println(header);
        System.out.println(content);
    }

    @Override
    protected boolean isMyTurn(String usernameOfPlayerWhosTurnItIs) {
        try {
            playersInGame.getPlayer(usernameOfPlayerWhosTurnItIs);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return usernameOfPlayerWhosTurnItIs.equals(myPlayer.getUsername());
    }


    /**
     * explains to client how to flip or place the starting card
     */
    private void askPlayerToPlaceStartingCard(){
        System.out.println("""
                You have been assigned a starting card!
                You can flip the card by typing 'F'
                When you are happy with the cards orientation type 'Place'
                once placed you can't change card orientation""");
    }

    /**
     * in the first round the client is given a starting card that he will place on his board in the orientation of his choice,
     * after that he will have to choose his color from the list of available colors. This method enables those actions.
     */
    @Override
    protected void enableFirstRoundActions() {
        clientActions.enableFirstRoundActions();
        askPlayerToPlaceStartingCard();
    }

    private synchronized void printScene(){
        System.out.println("\n\n\n-----------------------------------------------------------------------------\n\n\n");
        playersInGame.printScores();
        objectivesSection.printObjectivesSection();
        drawableArea.printDrawableArea();
        currentPlayerInScene.printBoard();
        System.out.println("HAND:");
        if(currentPlayerInScene == myPlayer){
            System.out.println("hand facing up:");
            myPlayer.printHand();
        }
        System.out.println("hand facing down:");
        currentPlayerInScene.printBackOfHand();
    }

    @Override
    protected void showScene() {
        printScene();
    }

    @Override
    protected void updateAvailableColors(ArrayList<String> availableColors) {
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
        playersInGame.getPlayer(username).getPlayerBoard().placeStartingCard(messageParser.getStartingCardCLI());
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
        playersInGame.getPlayer(playerUsernames.getFirst()).setCurrentPlayer(true);
    }


    public PlayerCLI getMyPlayer() {
        return myPlayer;
    }

}
