package it.polimi.ingsw.Client.View.CLI;

/**
 * final score board, shown at end of game
 */
public class FinalScoreBoardCLI {

    /**
     * contains the final position of every player and their final score
     */
    String finalScoreBoard="FINAL SCORE BOARD:\n";

    /**
     *
     * @param position of player
     * @param username of player
     * @param finalScore of player
     */
    public void addPlayer(int position, String username, int finalScore){
        finalScoreBoard=finalScoreBoard+position+": "+username+" "+finalScore;
        if(position==1){
            finalScoreBoard=finalScoreBoard+" \\uD83C\\uDFC6";
        }
        finalScoreBoard=finalScoreBoard+"\n";
    }

    /**
     * prints final scores and position of all the players
     */
    public void printFinalScoreBoard(){
        System.out.println(finalScoreBoard);
    }

}
