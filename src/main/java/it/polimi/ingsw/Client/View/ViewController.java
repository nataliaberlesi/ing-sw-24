package it.polimi.ingsw.Client.View;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageType;
public abstract class ViewController {

    /**
     * Regex for valid usernames.
     */
    protected static final String USERNAME_REGEX = "^\\w{1,8}$";

    /**
     * Chosen number of players for the game.
     */
    protected int numberOfPlayers;

    /**
     * Chosen player username.
     */
    protected String username;

    /**
     * Chosen player token color
     */
    protected String tokenColor;

    /**
     * Flag for game creator.
     */
    protected boolean isMaster = false;

    /**
     * Flag for final round.
     */
    protected boolean finalRound = false;

    /**
     * List of winners.
     */
    protected List<String> winners = Collections.emptyList();

    protected final MessageParser messageParser;

    protected final MessageDispatcher messageDispatcher;

    protected MessageType previousMessageType;

    protected ViewController(MessageParser messageParser, MessageDispatcher messageDispatcher){
        this.messageParser = messageParser;
        this.messageDispatcher = messageDispatcher;
        this.messageParser.setView(this);
    }

    /**
     * Verifies if chosen username matches provided regex.
     *
     * @param username player username
     * @return true if username is valid, false otherwise
     */
    protected static boolean correctUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }



    protected abstract void createMyPlayer(String username);
    /**
     * If username is already taken then the user ìs notified and brought back to join screen.
     * If not new instance of player is made with approved username.
     */
    protected void manageJoinStatus(){
        if(messageParser.unavailableUsername()){
            this.showErrorAlert("Invalid username", "Username already taken, please select another one");
            switchToJoin();
        }
        else {
            createMyPlayer(messageParser.getUsername());
        }
    }


    /**
     * Update winners after final round.
     */
    protected void checkWinners() {
        this.winners = this.messageParser.getWinners();
    }

    //DA NON USARE PIU'
    /**
     * If player gave correct information, sends a message to the server with player's information, sets loading screen and returns true
     * @param username player's chosen username
     * @param numberOfPlayers number of players for the game, chosen by master
     * */
    /*
    protected boolean checkParamsAndSendCreateOrJoinMessage(String username, Integer numberOfPlayers){
        if (playerGivesCorrectInformation(username, numberOfPlayers)){
            if (messageParser.masterStatus()) {
                messageDispatcher.createGame(numberOfPlayers, username);
            }
            else {
                messageDispatcher.joinGame(username);
            }
            switchToLoading();
            return true;
        }
        return false;
    }
     */

    //....
    protected boolean checkParamsAndSendCreate(String username, Integer numberOfPlayers){
        if(correctUsernameShowAlertIfFalse(username)&correctNumberOfPlayersShowAlertIfFalse(numberOfPlayers)){
            messageDispatcher.createGame(numberOfPlayers, username);
            switchToLoading();
            return true;
        }
        return false;
    }

    //....
    protected boolean correctUsernameShowAlertIfFalse(String username){
        if (username == null || !correctUsername(username)){
            showErrorAlert("Invalid username", "Username must contain between 1 and 8 alphanumeric characters");
            return false;
        }
        return true;
    }

    //....
    protected boolean correctNumberOfPlayersShowAlertIfFalse(Integer numberOfPlayers){
        if (numberOfPlayers == null || !(numberOfPlayers >= 2 && numberOfPlayers <= 4)){
            showErrorAlert("Invalid number of players", "Please select a number of players for the game");
            return false;
        }
        return true;
    }

    //....
    protected boolean checkParamsAndSendJoin(String username){
        if(correctUsernameShowAlertIfFalse(username)){
            messageDispatcher.joinGame(username);
            switchToLoading();
            return true;
        }
       return false;
    }


    // DA NON USARE PIU'
    /**
     * Checks if player entered the information correctly, display error alerts if not.
     * @param playersNumber number of players
     * @param username username
     * */
    /*
    protected boolean playerGivesCorrectInformation(String username, Integer playersNumber) {
        if (username == null || !correctUsername(username)){
            showErrorAlert("Invalid username", "Username must contain between 1 and 8 alphanumeric characters");
            return false;
        }
        if (messageParser.masterStatus()){
            if (playersNumber == null || !(playersNumber >= 2 && playersNumber <= 4)){
                showErrorAlert("Invalid number of players", "Please select a number of players for the game");
                return false;
            }
        }
        return true;
    }
    */


    /**
     * Check if message content is empty.
     *
     * @param messageParams message content
     */
    protected void checkEmptyContent(String messageParams) {
        if (messageParams.isEmpty())
            throw new ViewException("Unexpected empty message content");
    }

    /**
     * Continue game if winners is empty.
     */
    protected void continueGame() {
        if (!finalRound) {
            if (this.messageParser.getCurrentPlayer().equals(this.username)) {
                this.enableActions();
            } else {
                this.waitTurn();
            }
        }
    }

    /**
     * Updates final round flag after action
     * */
    private void checkFinalRound() {
        this.finalRound = this.messageParser.checkFinalRound();
        //TODO: checkFinalRound() : should return true if it's final round
    }



    /**
     * Method called to start the game.
     */
    protected abstract void startGame();

    /**
     * Method called to start showing the game.
     */
    protected abstract void startShow();
    /**
     * Method called to switch to create mode in the initial game view.
     */
    protected abstract void switchToCreate();
    /**
     * Method called to switch to join mode in the initial game view.
     */
    protected abstract void switchToJoin();

    /**
     * Method called to update the view game information.
     */
    protected abstract void updateGame();

    protected abstract void showErrorAlert(String header, String content);

    /**
     * Method called to show abort.
     *
     * @param message abort message
     */
    protected abstract void showAbort(String message);

    /**
     * Method called to show an error.
     *
     * @param message error message
     */
    protected abstract void showError(String message);

    /**
     * Method called to show the game end.
     *
     * @param winners list of winners
     */
    protected abstract void displayWinners(List<String> winners);

    /**
     * Method called to show actions options.
     */
    protected abstract void enableActions();

    /**
     * Method called to show waiting for turn.
     */
    protected abstract void waitTurn();

    /**
     * Method called to go back to starting choice of create or player.
     */
    protected abstract void returnToMainMenu();

    /**
     * Method called to start the view.
     *
     * @param args arguments
     */
    public abstract void main(String[] args);

    private boolean isMyTurn(){
        // in comune facciamo dopo (controlla se  è il turno del giocatore che controlla la view)
        return false;
    }

    protected abstract void switchToLoading();

    /**
     * Method called to update the view according to received message type.
     */
    public void updateView() {
        switch (this.messageParser.getMessageType()) {

            case CONNECT -> {
                connectScene();
                /*
                if(messageParser.masterStatus()){
                    switchToCreate();
                }
                else {
                    switchToJoin();
                }

                 */
            }


            case JOIN -> manageJoinStatus();

            case START_FIRSTROUND -> {
                setUpGame(); //create instance for every player, fill drawableArea, give first player starting card
                showScene();
                if(isMyTurn()){
                    enableFirstRoundActions(); // player must place starting card and choose color for available colors
                }
           }
            case FIRSTROUND -> {
                /*
                placeStartingCard();
                setPlayerColor();
                if(isMyTurn()){
                    enableFirstRoundActions();
                }
                 */
            }
            case START_SECONDROUND -> {
                /*

                 */
            }
            case SECONDROUND -> {

            }
            case START_ACTION -> {

            }
            case ACTION_PLACECARD -> {

            }
            case ACTION_DRAWCARD -> {

            }

            case FINALROUND -> {

            }
            case ABORT -> {

            }
            case ERROR -> {
            }
        }
    }

    protected abstract void connectScene();


    protected abstract void enableFirstRoundActions();

    protected abstract void showScene();

    private void setUpGame() {
        addPlayers(); // -> create instance players, show usernames and points in scene, set player's hand label, set see other player's game buttons
        giveInitialCards(); // -> create instance of board with initial card's back
        setDrawableArea(); // -> create instance of drawable area with given cards
        setAvailableColors(); // -> put all colors in pop up scene

    }

    protected abstract void setAvailableColors();

    protected abstract void setDrawableArea();

    protected abstract void giveInitialCards();

    protected abstract void addPlayers();

    /**
     * Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */
    protected void connectPlayer() {
        if (messageParser.masterStatus()) {
            switchToCreate();
        } else switchToJoin();
    }

    /**
     * Runtime exception for errors within any view class.
     */
    public static class ViewException extends RuntimeException {
        /**
         * ViewException constructor with message.
         *
         * @param message message to be shown
         */
        public ViewException(String message) {
            super(message);
        }

        /**
         * ViewException constructor with message and cause.
         *
         * @param message message to be shown
         * @param cause   cause of the exception
         */
        public ViewException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
