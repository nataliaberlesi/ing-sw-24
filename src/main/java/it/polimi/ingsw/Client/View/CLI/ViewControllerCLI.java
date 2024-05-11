package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.ViewController;

import java.util.List;
import java.util.Scanner;

public class ViewControllerCLI extends ViewController {


    protected ViewControllerCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
        super(messageParser, messageDispatcher);
    }

    @Override
    protected void waitForStart() {

    }

    @Override
    protected void createGame() {

    }

    @Override
    protected void joinGame() {

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

    private int askNumberOfPlayers(Scanner scanner){
        System.out.println("Choose number of players:");
        return scanner.nextInt();
    }

    @Override
    protected void switchToCreate() {
        Scanner scanner = new Scanner(System.in);
        String username=askUsername(scanner);
        int numberOfPlayers=askNumberOfPlayers(scanner);


    }

    @Override
    protected void switchToJoin() {
        System.out.println("Choose a Username:");
    }

    @Override
    protected void updateGame() {

    }

    @Override
    protected void showErrorAlert(String header, String content) {

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
