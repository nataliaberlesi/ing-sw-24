package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.List;
import java.util.Scanner;

public class ViewControllerCLI extends ViewController {


    private PlayersInGameCLI playersInGame;
    private ObjectivesSectionCLI objectivesSection;
    private DrawableAreaCLI drawableArea;
    private PlayerCLI currentPlayerView;


    public ViewControllerCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
    }

    public PlayersInGameCLI getPlayersInGame() {
        return playersInGame;
    }

    public void setCurrentPlayerView(PlayerCLI currentPlayerView) {
        this.currentPlayerView = currentPlayerView;
    }

    @Override
    protected void startGame() {

    }

    @Override
    protected void startShow() {

    }

    private String askUsername(Scanner scanner){
        System.out.println("Choose a Username:");
        return scanner.nextLine();
    }

    private Integer askNumberOfPlayers(Scanner scanner){
        System.out.println("Choose number of players:");
        return scanner.nextInt();
    }

    @Override
    protected void switchToCreate() {
        Scanner scanner = new Scanner(System.in);
        String username = askUsername(scanner);
        Integer numberOfPlayers = askNumberOfPlayers(scanner);
        if (!checkParamsAndSendCreateOrJoinMessage(username, numberOfPlayers)) {
            switchToCreate();
        }
    }

    @Override
    protected void switchToLoading(){
        System.out.println("Waiting for players...");
    }

    @Override
    protected void switchToJoin() {
        Scanner scanner = new Scanner(System.in);
        String username = askUsername(scanner);
        if (!checkParamsAndSendCreateOrJoinMessage(username, null)) {
            switchToJoin();
        }
    }

    @Override
    protected void updateGame() {

    }

    @Override
    protected void showErrorAlert(String header, String content) {
        System.out.println(header);
        System.out.println(content);
    }

    @Override
    protected void showAbort(String message) {

    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void displayWinners(List<String> winners) {

    }

    @Override
    protected void enableActions() {

    }

    @Override
    protected void waitTurn() {

    }

    @Override
    protected void returnToMainMenu() {

    }

    @Override
    public void main(String[] args) {

    }

    @Override
    protected void enableFirstRoundActions() {

    }


    @Override
    protected void showScene() {
        playersInGame.printScores();
        objectivesSection.printCommonObjectives();
        currentPlayerView.printPlayerSituation();
        drawableArea.printDrawableArea();
    }

    @Override
    protected void setAvailableColors() {

    }

    @Override
    protected void setDrawableArea() {

    }

    @Override
    protected void giveInitialCards() {

    }

    @Override
    protected void addPlayers() {

    }
}
