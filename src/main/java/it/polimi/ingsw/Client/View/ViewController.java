package it.polimi.ingsw.Client.View;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;

public abstract class ViewController {

    /**
     * Regex for valid usernames.
     */
    protected static final String USERNAME_REGEX = "^\\w{1,8}$";

    protected final MessageParser messageParser;

    protected final MessageDispatcher messageDispatcher;

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
            createMyPlayer(messageParser.getCurrentPlayer());
            switchToLoading();
        }
    }

    /**
     *
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
     *
     * @param username chosen by player
     * @return true if username passes local screening (length >0 and <9), if not it shows the user an error message
     */
    protected boolean correctUsernameShowAlertIfFalse(String username){
        if (username == null || !correctUsername(username)){
            showErrorAlert("Invalid username", "Username must contain between 1 and 8 alphanumeric characters");
            return false;
        }
        return true;
    }

    /**
     *
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
     * @param username chosen by player
     * @return true if username length is >0 and <9, if not then it shows the user an error message
     */
    public boolean checkParamsAndSendJoin(String username){
        if(correctUsernameShowAlertIfFalse(username)){
            messageDispatcher.joinGame(username);
            switchWaitingServerResponse();
        }
        return false;
    }

    protected abstract void switchWaitingServerResponse();

    /**
     * Method called to switch to create mode in the initial game view.
     */
    protected abstract void switchToCreate();
    /**
     * Method called to switch to join mode in the initial game view.
     */
    protected abstract void switchToJoin();

    protected abstract void showErrorAlert(String header, String content);

    protected abstract boolean isMyTurn(String usernameOfPlayerWhoseTurnItIs);

    protected abstract void switchToLoading();

    protected abstract void updatePlayerBoard(String affectedPlayer);

    protected abstract void setPlayerColor(String affectedPlayer, String color);
    /**
     * Method called to update the view according to received message type.
     */
    public void updateView() {
        switch (this.messageParser.getMessageType()) {

            case CONNECT -> connectScene();

            case JOIN -> manageJoinStatus();

            case START_FIRSTROUND -> {
                setUpGame(); //create instance for every player, fill drawableArea, give first player starting card
                showScene();
                if(isMyTurn(messageParser.getPlayers().getFirst())){  // getPlayers().get(0) restituisce username del primo giocatore, da confrontare con il tuo client
                    enableFirstRoundActions(); // player must place starting card and choose color for available colors
                }
           }
            case FIRSTROUND -> {
                updatePlayerBoard(messageParser.getAffectedPlayer());
                setPlayerColor(messageParser.getAffectedPlayer(), messageParser.getColor());
                updateAvailableColors(messageParser.getAvailableColors());
                giveInitialCard(messageParser.getCurrentPlayer());
                if(isMyTurn(messageParser.getCurrentPlayer())){
                    enableFirstRoundActions();
                }
            }
            case START_SECONDROUND -> {

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

    protected abstract void enablePlaceCard();

    protected abstract void enableDrawCard();

    protected abstract void enableSecondRoundActions();

    protected abstract void enablePlaceStartingCard();

    protected abstract void enableChooseColor();

    protected abstract void connectScene();

    protected abstract void enableFirstRoundActions();

    protected abstract void showScene();

    private void setUpGame() {
        addPlayers(messageParser.getPlayers()); // -> create instance players (myPlayer already initialized when join), show usernames and points in scene, set player's hand label, set see other player's game buttons
        giveInitialCard(messageParser.getPlayers().getFirst()); // -> adds the initial card to the first player
        updateDrawableArea(); // -> create instance of drawable area with given cards
        updateAvailableColors(messageParser.getAvailableColors()); // -> put all colors in pop up scene
    }

    protected abstract void updateAvailableColors(ArrayList<String> availableColors);

    protected abstract void updateDrawableArea();

    protected abstract void giveInitialCard(String username);

    protected abstract void addPlayers(ArrayList<String> playerUsernames);

    /**
     * Connects player to create or join mode based on server response indicating the master status of the player trying to connect
     * */
    protected void connectPlayer() {
        if (messageParser.masterStatus()) {
            switchToCreate();
        } else switchToJoin();
    }
}
