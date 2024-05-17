package it.polimi.ingsw.Client.View.CLI;

public class PlayerCLI {

    /**
     * name of player, no two players have the same name
     */
    private final String username;
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
     * the first player to connect is the Master
     */
    private boolean isCurrentPlayer =false;
    /**
     * tha hand belonging to player
     */
    private final HandCLI playerHand;



    /**
     * constructor used when a player joins game
     * @param username unique name of player
     */
    public PlayerCLI(String username) {
        this.username = username;
        this.score = 0;
        this.playerBoard = new BoardCLI();
        this.playerHand = new HandCLI();
    }



    public void setScore(int score) {
        this.score = score;
    }

    public void updateHand(CardCLI[] newHand){
        this.playerHand.updateHand(newHand);
    }


    /**
     *
     * @param color of player
     * @throws IllegalArgumentException if color does not exist
     */
    public void setPlayerColor(String color) throws IllegalArgumentException{
        switch (color.toUpperCase()) {
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
     * prints hand all face up
     */
    public void printHand(){
        playerHand.printHand();
    }

    /**
     * prints hand all face down
     */
    public void printBackOfHand(){
        playerHand.printBackOfHand();
    }

    /**
     * prints colored username followed by the players score
     * if score 20 or above then it is red (to indicate final phase of game)
     */
    public void printScore(){
        String score=""+this.score;
        if(this.score>19){
            score=ColoredText.ANSI_RED+score+ColoredText.ANSI_RESET;
        }
        System.out.println(getColoredUsername()+": "+score);
    }

    /**
     * prints players board and hand, if player is not myPlayer the method will only print back of hand
     */
    public void printPlayerSituation(){
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


    public HandCLI getPlayerHand() {
        return playerHand;
    }

    public int getScore() {
        return score;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.isCurrentPlayer = currentPlayer;
    }
}
