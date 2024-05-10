package it.polimi.ingsw.Client.View.CLI;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.View.View;

import java.util.List;

public class ViewCLI extends View {


    private final PlayersInGame playersInGame=new PlayersInGame();

    private final ObjectiveCLI[] objectives= new ObjectiveCLI[3];





    protected ViewCLI(MessageParser messageParser, MessageDispatcher messageDispatcher) {
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

    @Override
    protected void switchToCreate() {

    }

    @Override
    protected void switchToJoin() {

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
    protected void closeGame(List<String> winners) {

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
}
