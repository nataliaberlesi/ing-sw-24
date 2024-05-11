package it.polimi.ingsw.Client.View.CLI;

public class PlayerCLI {

    /**
     * name of player, no two players have the same name
     */
    private String username;
    /**
     * players have a color that can be green, blu, red, yellow.
     */
    private String playerColor="";
    /**
     * points earned by player
     */
    private int score;
    /**
     * board of player, showing all cards placed by player
     */
    private final BoardCLI playerBoard;
    /**
     * true if it is the players turn
     */
    private boolean isCurrentPlayer =false;
    /**
     * true if this is the player that is controlling this view
     */
    private boolean isMyPlayer=false;
    /**
     * tha hand belonging to player
     */
    private final HandCLI playerHand;

    /**
     * each player has one private objective that only they can see
     */
    private ObjectiveCLI privateObjective;

    /**
     * constructor used when a player joins game
     * @param username unique name of player
     * @param isMyPlayer true if player be initialized is player that controls view
     */
    public PlayerCLI(String username, boolean isMyPlayer) {
        this.username = username;
        this.score = 0;
        this.playerBoard = new BoardCLI();
        this.playerHand = new HandCLI();
        this.isMyPlayer=isMyPlayer;
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

    public void setPrivateObjective(ObjectiveCLI privateObjective) {
        this.privateObjective = privateObjective;
    }

    /**
     *
     * @param color of player
     * @throws IllegalArgumentException if color does not exist
     */
    public void setPlayerColor(String color) throws IllegalArgumentException{
        switch (color) {
            case "RED":
                this.playerColor=ColoredText.ANSI_RED;
                break;
            case "BLUE":
                this.playerColor=ColoredText.ANSI_BLUE;
                break;
            case "GREEN":
                this.playerColor=ColoredText.ANSI_GREEN;
                break;
            case "YELLOW":
                this.playerColor=ColoredText.ANSI_YELLOW;
                break;
            default:
                throw new IllegalArgumentException("Invalid color");
        }
    }


    public String getUsername() {
        return username;
    }


    /**
     *
     * @return username of the color of the player, if player is the first player then an asterisk will be added at the end of colored username
     */
    public String getColoredUsername(){
        StringBuilder coloredUsername=new StringBuilder();
        coloredUsername.append(playerColor);
        coloredUsername.append(username);
        coloredUsername.append(ColoredText.ANSI_RESET);
        if(isCurrentPlayer){
            coloredUsername.append("*");
        }
        return coloredUsername.toString();
    }

    /**
     * prints the board of player
     */
    public void printBoard(){
        playerBoard.printBoard();
    }

    /**
     * if player is the one controlling this view then method prints his card face up, otherwise it prints cards face down
     */
    public void printHand(){
        if(isMyPlayer){
            playerHand.printHand();
            return;
        }
        playerHand.printBackOfHand();
    }

    /**
     * prints colored username followed by the players score
     */
    public void printScore(){
        System.out.println(getColoredUsername()+": "+score);
    }

    /**
     * prints players board and hand, if player is not myPlayer the method will only print back of hand
     */
    public void printPlayerSituation(){
        if(isMyPlayer){
            this.privateObjective.printObjective();
        }
        this.printBoard();
        this.printHand();
    }


    public BoardCLI getPlayerBoard() {
        return playerBoard;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public boolean isMyPlayer() {
        return isMyPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        isCurrentPlayer = currentPlayer;
    }

    public HandCLI getPlayerHand() {
        return playerHand;
    }

    public int getScore() {
        return score;
    }
}
