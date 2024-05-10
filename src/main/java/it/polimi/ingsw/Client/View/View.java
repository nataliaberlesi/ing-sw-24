package it.polimi.ingsw.Client.View;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageHandlerException;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.MessageType;
public abstract class View {

    /**
     * Regex for valid usernames.
     */
    protected static final String USERNAME_REGEX = "^\\w{2,8}$";

    /**
     * Chosen number of players for the game.
     */
    protected int playersNumber;
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

    protected View(MessageParser messageParser, MessageDispatcher messageDispatcher){
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

    /**
     * Resets the view properties.
     */
    protected void reset() {
        this.isMaster = false;
        this.previousMessageType = null;
        this.tokenColor = "";
        this.playersNumber = 0;
        this.username = "";
    }

    /**
     * Check if message params for create or join are valid, and wait for start.
     */
    protected void checkWaitForStart() {
        if (messageParser.checkWaitForStart())
            throw new ViewException("Unexpected non-ok message content");
        this.waitForStart();
    }

    /**
     * Check if client has to start the game.
     */

    protected void checkStart() {
        if (messageParser.getUsername().equals(username) && messageParser.unavailableUsername()){
            this.showErrorAlert("Invalid username", "Username already taken, please select another one");
            switchToJoin();
        }
        else if (this.messageParser.checkStart()) {//checks if is full
            if (this.isMaster)
                this.startGame();
        } if (this.previousMessageType != MessageType.START)
            this.checkWaitForStart();
    }

    /**
     * Update winners after final round.
     */
    protected void checkWinners() {
        this.winners = this.messageParser.getWinners();
    }

    /**
     * Checks if player entered the information correctly.
     * @param isMaster master flag
     * @param playersNumber number of players
     * @param username username
     * */
    protected boolean playerGivesCorrectInformation(boolean isMaster, String username, Integer playersNumber) {
        if (username == null || !correctUsername(username)){
            showErrorAlert("Invalid username", "Username must contain between 2 and 8 alphanumeric characters");
            return false;
        }
        if (isMaster){
            if (playersNumber == null || !(playersNumber >= 2 && playersNumber <= 4)){
                showErrorAlert("Invalid players number", "Please select a number of players for the game");
                return false;
            }
        }
        return true;
    }

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
     * Method called to display loading screen until all players connect to start the game.
     * */
    protected abstract void waitForStart();

    /**
     * Method called to create the game.
     */
    protected abstract void createGame();

    /**
     * Method called to join the game.
     */
    protected abstract void joinGame();

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
    protected abstract void closeGame(List<String> winners);

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

    /**
     * Method called to update the view according to received message type.
     */
    public void updateView() {
        String messageParams = this.messageParser.getMessageParams();
        switch (this.messageParser.getMessageType()) {

            case CREATE -> {
                this.checkWaitForStart();
                this.isMaster = true;
            }
            case JOIN -> {
                this.checkStart();
            }
            case START -> {
                this.startShow();
                this.continueGame();
            }
            case ACTION -> {
                this.updateGame();
                this.checkFinalRound();
                if(!finalRound)
                    this.continueGame();

            }
            case FINAL_ROUND -> {
                this.updateGame();
                this.checkWinners();
                this.closeGame(this.winners);
                this.returnToMainMenu();
                this.reset();
            }
            case ABORT -> {
                this.checkEmptyContent(messageParams);
                this.showAbort(messageParams);
                this.reset();
                this.returnToMainMenu();
            }
            case ERROR -> {
                this.checkEmptyContent(messageParams);
                this.showError(messageParams);
                if (this.previousMessageType == MessageType.CREATE || this.previousMessageType == MessageType.JOIN) {
                    this.reset();
                    this.returnToMainMenu();
                } else {
                    this.continueGame();
                }
            }
        }
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
