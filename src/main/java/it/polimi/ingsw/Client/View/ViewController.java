package it.polimi.ingsw.Client.View;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;

/**
 * Controller class for View
 */
public abstract class ViewController {

    /**
     * Regex for valid usernames.
     */
    protected static final String USERNAME_REGEX = "^\\w{2,10}$";

    /**
     * Instance of message parser to read messages from server
     */
    protected final MessageParser messageParser;
    /**
     * Instance of message dispatcher to send messages to server
     */
    protected final MessageDispatcher messageDispatcher;

    protected boolean masterStatus;

    /**
     * Constructor of ViewController
     * @param messageParser MessageParser instance
     * @param messageDispatcher MessageDispatcher instance
     */
    protected ViewController(MessageParser messageParser, MessageDispatcher messageDispatcher){
        this.messageParser = messageParser;
        this.messageDispatcher = messageDispatcher;
        this.messageParser.setView(this);
    }

    /**
     * Method called to update the view according to received message type from server.
     */
    public void updateView() {
        switch (this.messageParser.getMessageType()) {

            case PERSISTENCE ->
                    askCreateOrContinue();
            case CONTINUE ->{
                if(playersInfoNotYetAdded()){
                    addPlayers(messageParser.getPlayers());
                    setPublicObjectives();
                    updateDrawableArea();
                }
                if(isMyPlayer(messageParser.getAffectedPlayer())){
                    setPrivateObjective();
                }
                updatePlayerBoardHandScore(messageParser.getAffectedPlayer(), messageParser.getScore());
            }

            case CHAT -> updateChat();

            case CONNECT -> {
                connectScene();
                masterStatus = messageParser.masterStatus();
            }

            case JOIN -> manageJoinStatus();

            case START_FIRSTROUND -> {
                setUpGame(); //create instance for every player, fill drawableArea, give first player starting card
                setCurrentPlayer(messageParser.getPlayers().getFirst());
                showScene();
                if(isMyTurn(messageParser.getPlayers().getFirst())){  // getPlayers().get(0) returns username of first player, to confront with myplayer
                    enableFirstRoundActions(); // player must place starting card and choose color for available colors
                }
            }

            case FIRSTROUND -> {
                setCurrentPlayer(messageParser.getCurrentPlayer());
                giveInitialCard(messageParser.getCurrentPlayer());
                setPlayerColor(messageParser.getAffectedPlayer(), messageParser.getColor());
                updateAvailableColors(messageParser.getAvailableColors());
                updatePlayerBoard(messageParser.getAffectedPlayer());//update the board of the last player to act
                showScene();
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    enableFirstRoundActions();
                }
            }

            case START_SECONDROUND -> {
                setCurrentPlayer(messageParser.getCurrentPlayer());
                updatePlayerHand(messageParser.getCurrentPlayer());
                setPublicObjectives();
                showScene();
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    setPrivateObjectiveChoice();
                    enableSecondRoundActions();
                }
            }

            case SECONDROUND -> {
                if(isPlayerInGame(messageParser.getCurrentPlayer())){
                    setCurrentPlayer(messageParser.getCurrentPlayer());
                    updatePlayerHand(messageParser.getCurrentPlayer());
                }
                showScene();
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    setPrivateObjectiveChoice();
                    enableSecondRoundActions();
                }
            }

            case ACTION_PLACECARD -> {
                setCurrentPlayer(messageParser.getCurrentPlayer());
                updatePlayerBoardHandScore(messageParser.getAffectedPlayer(), messageParser.getScore());
                showScene();
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    enableDrawCard();
                }
            }

            case ACTION_DRAWCARD -> {
                setCurrentPlayer(messageParser.getCurrentPlayer());
                updatePlayerBoardHandScore(messageParser.getAffectedPlayer(), messageParser.getScore());
                updateDrawableArea();
                showScene();
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    enablePlaceCard();
                }
            }

            case ENDGAME  -> setAndShowFinalScoreBoard();

            case ABORT -> {
                showErrorAlert(messageParser.getCause(), "Game has terminated");
                terminate();
            }

            case POKE -> messageDispatcher.poke();


        }
    }

    //******** VIEW CONTROLLER IMPLEMENTED METHODS USED BY ViewControllerGUI AND ViewControllerCLI ********//

    /**
     * Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */
    protected void connectPlayer() {
        if (masterStatus) {
            switchToCreate();
        } else switchToJoin();
    }

    /**
     * Verifies if chosen username matches provided regex.
     * @param username player username
     * @return true if username is valid, false otherwise
     */
    protected static boolean correctUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Manages join status
     * If username is already taken then the user ìs notified and brought back to join screen.
     * If not, new instance of player is made with approved username.
     */
    protected void manageJoinStatus(){
        if(messageParser.unavailableUsername()){
            this.showErrorAlert("Invalid username", "Please select another one");
            switchToJoin();
        }
        else {
            createMyPlayer(messageParser.getCurrentPlayer());
            switchToLoading();
        }
    }

    /**
     * Checks chosen parameters and send create message to server
     * @param username chosen by player
     * @param numberOfPlayers allowed in this game, chosen by first player to connect
     * @return true if parameters pass local screening (if name doesn't go over character limit or if numberOfPlayers is >1 and <5)
     */
    public boolean checkParamsAndSendCreate(String username, Integer numberOfPlayers){
        if(correctUsernameShowAlertIfFalse(username) && correctNumberOfPlayersShowAlertIfFalse(numberOfPlayers)){
            messageDispatcher.createGame(numberOfPlayers, username);
            createMyPlayer(username);
            switchToLoading();
            return true;
        }
        return false;
    }

    /**
     * Checks if chosen username is valid
     * @param username username chosen by player
     * @return true if username passes local screening (length >0 and <= 10), if not it shows the user an error message
     */
    protected boolean correctUsernameShowAlertIfFalse(String username){
        if (username == null || !correctUsername(username)){
            showErrorAlert("Invalid username", "Username must contain between 2 and 10 alphanumeric characters");
            return false;
        }
        return true;
    }

    /**
     * Checks if chosen number of players for the game is valid
     * @param numberOfPlayers chosen by master player (aka first one to connect)
     * @return true if number of players chosen is >1 and <5, if not then it shows the user an error message
     */
    protected boolean correctNumberOfPlayersShowAlertIfFalse(Integer numberOfPlayers){
        if (numberOfPlayers == null || !(numberOfPlayers >= 2 && numberOfPlayers <= 4)){
            showErrorAlert("Invalid number of players", "Please select a number of players for the game");
            return false;
        }
        return true;
    }

    /**
     * Checks chosen parameters and sends join message to server
     * @param username chosen by player
     * @return true if username length is >0 and <= 10, if not then it shows the user an error message
     */
    public boolean checkParamsAndSendJoin(String username){
        if(correctUsernameShowAlertIfFalse(username)){
            messageDispatcher.joinGame(username);
            switchWaitingServerResponse();
            return true;
        }
        return false;
    }

    /**
     * Adds all players to game, sets drawable area and gives the first player his staring card and shows available colors
     */
    private void setUpGame() {
        addPlayers(messageParser.getPlayers()); // -> create instance players (myPlayer already initialized when join), show usernames and points in scene, set player's hand label, set see other player's game buttons
        giveInitialCard(messageParser.getPlayers().getFirst()); // -> adds the initial card to the first player
        updateDrawableArea(); // -> create instance of drawable area with given cards
        updateAvailableColors(messageParser.getAvailableColors()); // -> put all colors in pop up scene
    }

    /**
     * Updates player's board, hand and score
     * @param affectedPlayer is player that whose board hand and points need to be updated
     * @param score are the points that belong to affected player
     */
    private void updatePlayerBoardHandScore(String affectedPlayer, int score){
        updatePlayerBoard(affectedPlayer);
        updatePlayerHand(affectedPlayer);
        updatePlayerScore(affectedPlayer, score);
    }

    /**
     * Determines if a message is sent by client, or to client, or to all players in game,
     * then the message will appear in clients chat
     */

    protected void exit(String username){
        if (username != null)
            messageDispatcher.abortGame(username, username + " doesn't want to play anymore :(");
        else messageDispatcher.abortGame(null, "A player has disconnected");
        terminate();
    }

    //******** END OF VIEW CONTROLLER IMPLEMENTED METHODS USED BY ViewControllerGUI AND ViewControllerCLI ********//


    //******** VIEW CONTROLLER ABSTRACT METHODS IMPLEMENTED IN ViewControllerGUI AND ViewControllerCLI ********//

    protected abstract void createMyPlayer(String username);

    protected abstract void switchWaitingServerResponse();

    protected abstract void switchToCreate();

    protected abstract void switchToJoin();

    protected abstract void showErrorAlert(String header, String content);

    protected abstract boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs);

    protected abstract void switchToLoading();

    protected abstract void updatePlayerBoard(String affectedPlayer);

    protected abstract void setPlayerColor(String affectedPlayer, String color);

    protected abstract void askCreateOrContinue();

    protected abstract boolean playersInfoNotYetAdded();

    protected abstract boolean isMyPlayer(String username);

    protected abstract void setPrivateObjective();

    protected abstract void terminate();

    protected abstract void exit();

    protected abstract void setAndShowFinalScoreBoard();

    protected abstract void setPrivateObjectiveChoice();

    protected abstract void setCurrentPlayer(String currentPlayer);

    protected abstract boolean isPlayerInGame(String username);

    protected abstract void updatePlayerScore(String username, int score);

    protected abstract void setPublicObjectives();

    protected abstract void updatePlayerHand(String username);

    protected abstract void enablePlaceCard();

    protected abstract void enableDrawCard();

    protected abstract void enableSecondRoundActions();

    protected abstract void connectScene();

    protected abstract void enableFirstRoundActions();

    protected abstract void showScene();

    protected abstract void updateAvailableColors(ArrayList<String> availableColors);

    protected abstract void updateDrawableArea();

    protected abstract void giveInitialCard(String username);

    protected abstract void addPlayers(ArrayList<String> playerUsernames);

    //******** END OF VIEW CONTROLLER ABSTRACT METHODS IMPLEMENTED IN ViewControllerGUI AND ViewControllerCLI ********//



    //***************** CHAT METHODS *****************//
    protected void updateChat(){
        String receiver = messageParser.getAffectedPlayer();
        String messageMeantForEveryone = "-all";
        if (shouldProcessMessage(receiver, messageMeantForEveryone)) {
            String sender = messageParser.getCurrentPlayer();
            boolean isPrivate = isPrivateMessage(receiver, messageMeantForEveryone);
            boolean addMessage = shouldAddMessage(receiver, sender, isPrivate);
            if (addMessage) {
                addMessageToChat(buildMessage(sender, receiver, messageParser.getChat(), isPrivate));
                showUpdatedChat();
            }
        }
    }

    /**
     * Builds message
     * @param sender player who sent message
     * @param receiver player who is recipient
     * @param messageBody the message
     * @param isPrivate false if message is meant for all players, true if message is meant for only one player
     * @return message that indicates weather it is private/public, who sent the message,
     *         who received the message (this only happens when client sent the private message), and finally the body of the message
     */
    private String buildMessage(String sender, String receiver, String messageBody, boolean isPrivate){
        String prefix="PUBLIC";
        if(isPrivate){
            prefix="PRIVATE";
            if(isMyPlayer(sender)){
                sender="ME";
                prefix+=" TO "+receiver;
            }
        }
        return prefix+" FROM "+sender+": "+messageBody;
    }
    /**
     * Used to determine weather or not message should be shown to client
     * @param receiver of message
     * @param sender of message
     * @param isPrivate weather or not the message is meant for all players or just one
     * @return true is sender or receiver is my client or if message is meant for everyone
     */
    private boolean shouldAddMessage( String receiver,String sender, boolean isPrivate) {
        return !isPrivate || isMyPlayer(sender) || isMyPlayer(receiver);
    }

    /**
     * Used to determine if a message should be processed, if the receiver is someone that is not in the game the message shouldn't
     * be processed.
     * @param receiver of message
     * @param messageMeantForEveryone string used to determine if message is meant for all players
     * @return true if receiver is a player in game or if message is meant for all players
     */
    private boolean shouldProcessMessage(String receiver, String messageMeantForEveryone) {
        return isPlayerInGame(receiver) || receiver.equalsIgnoreCase(messageMeantForEveryone);
    }

    /**
     * Used to determine if a message is private or not
     * @param receiver of message
     * @param messageMeantForEveryone string used to determine if message is meant for all players
     * @return true if receiver is a specific player, or if sender is my client
     */
    private boolean isPrivateMessage(String receiver, String messageMeantForEveryone) {
        return !receiver.equalsIgnoreCase(messageMeantForEveryone);
    }

    /**
     *
     * @param myPlayerUsername username of my client
     * @param receiver who the message if meant for
     * @param message that client wants to send
     */
    public void sendChatMessage(String myPlayerUsername, String receiver, String message){
        messageDispatcher.chat(myPlayerUsername, receiver, message);
    }

    protected abstract void showUpdatedChat();

    protected abstract void addMessageToChat(String message);

    //***************** END OF CHAT METHODS *****************//

}
