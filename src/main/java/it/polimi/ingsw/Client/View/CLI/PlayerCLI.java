package it.polimi.ingsw.Client.View.CLI;

public class PlayerCLI {

    /**
     * name of player, no two players have the same name
     */
    private String username;
    /**
     * players have a color that can be green, blu, red, yellow.
     */
    private ColoredText playerColor;
    /**
     * points earned by player
     */
    private int score;
    /**
     * board of player, showing all cards placed by player
     */
    private final BoardCLI playerBoard;
    /**
     * the first player to connect is the Master
     */
    private boolean isMaster=false;
    /**
     * true if this is the player that is controlling this view
     */
    private boolean isMyPlayer=false;
    /**
     * tha hand belonging to player
     */
    private final HandCLI playerHand;

    /**
     * constructor used when a player joins game
     * @param username unique name of player
     */
    public PlayerCLI(String username, boolean isMaster) {
        this.username = username;
        this.score = 0;
        this.playerBoard = new BoardCLI();
        this.playerHand = new HandCLI();
        this.isMaster=isMaster;
    }

    /**
     *
     * @return player that controls view
     */
    public PlayerCLI makeMyPlayer(boolean isMyMaster){
        isMyPlayer=true;
        return new PlayerCLI(null, isMyMaster);
    }

    /**
     *
     * @param username of player that controls board
     * @throws RuntimeException if method tries to change the name of another player
     */
    public void setUsername(String username) throws RuntimeException{
        if(!isMyPlayer){
            throw new RuntimeException("Can't change other player username");
        }
        this.username = username;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public BoardCLI getPlayerBoard() {
        return playerBoard;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public boolean isMyPlayer() {
        return isMyPlayer;
    }

    public HandCLI getPlayerHand() {
        return playerHand;
    }

    public int getScore() {
        return score;
    }
}
