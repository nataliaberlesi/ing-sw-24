package it.polimi.ingsw.Server.Controller;



import it.polimi.ingsw.Server.Controller.DTO.*;
import it.polimi.ingsw.Server.Model.Cards.Card;
import it.polimi.ingsw.Server.Model.Cards.ObjectiveFactory;
import it.polimi.ingsw.Server.Model.Cards.Objectives.Objective;
import it.polimi.ingsw.Server.Model.PlacedCard;
import it.polimi.ingsw.Server.Model.Scoreboard;
import it.polimi.ingsw.Server.Network.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    private final MessageParser messageParser;
    private GameInstance gameInstance;
    private final Server server;
    private MessageType previousMessageType;
    private boolean persistency;
    public GameController(Server server) throws IOException {
        this.server=server;
        this.messageParser=MessageParser.getINSTANCE();
        persistency=false;
    }

    public Message dispatchMessage(String message) {
        Message currentMessage=messageParser.parseMessage(message);
        MessageType messageType=currentMessage.type();
        InParamsDTO inParamsDTO=currentMessage.params().clientOutParams();
        return queryModel(messageType, inParamsDTO);
    }
    /**
     * Given a specific type of message, queries the model to get/change some information
     */
    public Message queryModel(MessageType messageType,InParamsDTO inParamsDTO) {
        switch (messageType) {
            case PERSISTENCE -> {
                return checkPersistency(inParamsDTO);
            }
            case CREATE -> {
                return createGame(inParamsDTO);
            }
            case JOIN -> {
                return joinGame(inParamsDTO);
            }
            case FIRSTROUND -> {
                return playFirstRound(inParamsDTO);
            }
            case SECONDROUND -> {
                //TODO
                return playSecondRound(inParamsDTO);
            }
            case ACTION_PLACECARD -> {
                //TODO
                return placeCard(inParamsDTO);
            }
            case ACTION_DRAWCARD -> {
                //TODO
                return drawCard(inParamsDTO);
            }
            case CHAT -> {
                return chat(inParamsDTO);
            }
            case ABORT -> {
                return closeGame(inParamsDTO);
            }
        }
        return null;
    }
    private Message checkPersistency(InParamsDTO inParamsDTO) {
        persistency=inParamsDTO.persistence();
        if(persistency) {
            try {
                gameInstance= PersistencyHandler.fetchState();
                PersistencyHandler.setPlayersTemp((ArrayList<String>) gameInstance.getPlayerTurnOrder().clone());
                server.openLobby(gameInstance.getNumberOfPlayers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return MessageCrafter.craftConnectMessage(false);
        } else {
            PersistencyHandler.deleteGame();
            return MessageCrafter.craftConnectMessage(true);
        }
    }
    private Message closeGame(InParamsDTO inParamsDTO) {
        return MessageCrafter.craftAbortMessage(inParamsDTO.cause());
    }

    /**
     * Creates a game using master player data
     */
    public Message createGame(InParamsDTO inParamsDTO){
        if(this.gameInstance!=null){
            return MessageCrafter.craftAbortMessage("A game already exist");
        }
        this.server.openLobby(inParamsDTO.numberOfPlayers());
        this.gameInstance=new GameInstance(inParamsDTO.username().toUpperCase(),inParamsDTO.numberOfPlayers());
        Message message = MessageCrafter.craftCreateMessage(inParamsDTO.username().toUpperCase());
        previousMessageType=message.type();
        return message;
    }

    /**
     * Lets a player join a game if his username is not taken
     * @return an affermative answer if the username is available, a negative answer otherwise
     */
    public Message joinGame(InParamsDTO inParamsDTO) {
        String username=inParamsDTO.username().toUpperCase();
        Boolean unavailableUsername;
        if(persistency) {
            unavailableUsername= PersistencyHandler.checkPlayerTemp(username);
        } else {
            unavailableUsername=this.gameInstance.unavailableUsername(username);
            if(!unavailableUsername){
                gameInstance.joinPlayer(username);
            }
        }
        Message message = MessageCrafter.craftJoinMessage(inParamsDTO.username(),unavailableUsername);
        previousMessageType=message.type();
        return message;

    }
    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters in JSON format
     */
    public Message getJSONStartFirstRoundParams() {
        ParamsDTO params=SetUpGame.getStartFirstRoundParams(gameInstance);
        return new Message(MessageType.START_FIRSTROUND,params);
    }

    /**
+     * Plays the current player's first round with given parameters
     * @return
     */
    public Message playFirstRound(InParamsDTO inParamsDTO) {
        String card=null;
        gameInstance.chooseColor(inParamsDTO.username(), inParamsDTO.color());
        gameInstance.placeStartingCard(inParamsDTO.username(),inParamsDTO.isFacingUp());
        ArrayList<PlacedCard> placedCards=gameInstance.getPlacedCards(inParamsDTO.username());
        int turn=gameInstance.nextTurn();
        String currentPlayer="";
        if(turn!=0) {
            currentPlayer=gameInstance.getTurn();
            card=gameInstance.getPlayers().get(currentPlayer).getPlayerBoard().seeStartingCardID();
        }
        gameInstance.checkIfAllBoardsAreSet();
        Message message = MessageCrafter.craftFirstRoundMessage(card,currentPlayer,gameInstance.getAvailableColors(),inParamsDTO.username(),placedCards,inParamsDTO.color());
        previousMessageType=message.type();
        return message;
    }
    public Message getJSONStartSecondRoundParams() {
        ParamsDTO startSecondRound=SetUpGame.getStartSecondRoundParams(gameInstance);
        return new Message(MessageType.START_SECONDROUND,startSecondRound);
    }
    public Message playSecondRound(InParamsDTO inParamsDTO) {
        Objective[] privateObjectives=new Objective[2];
        String currentPlayer= "";
        Card[] hand=new Card[3];
        if(gameInstance.nextTurn()!=0){
            for(int i=0;i<2;i++) {
                privateObjectives[i]=
                        ObjectiveFactory.makeObjective(gameInstance.getPlayers().get(gameInstance.getTurn()).getPlayerBoard().seeStartingPrivateObjective(i));
            }
            currentPlayer= gameInstance.getTurn();
            hand=gameInstance.getHand(currentPlayer);
        }
        gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerBoard().choosePrivateObjective(inParamsDTO.index());
        Message message=MessageCrafter.craftSecondRoundMessage(
                currentPlayer,
                inParamsDTO.username(),
                hand,
                privateObjectives,
                gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerBoard().seeObjective(2)
        );
        gameInstance.checkIfAllObjectivesHaveBeenChosen();
        this.previousMessageType=message.type();
        return message;
    }
    public Message placeCard(InParamsDTO inParamsDTO) {
        String currentPlayer=inParamsDTO.username();
        if(!gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerBoard().placeCard(
                gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerHand().showCardInHand(inParamsDTO.index()),
                inParamsDTO.coordinates(),
                inParamsDTO.isFacingUp()
        )) {
            Message message=MessageCrafter.craftDrawCardMessage(
                    currentPlayer,
                    currentPlayer,
                    gameInstance.getHand(currentPlayer),
                    gameInstance.getResourceDrawableArea(),
                    gameInstance.getGoldDrawableArea()
            );
            this.previousMessageType=message.type();
            return message;
        }
        gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerHand().getCardFromHand(inParamsDTO.index());
        Message message=MessageCrafter.craftPlaceCardMessage(
                currentPlayer,
                gameInstance.getPlacedCards(currentPlayer),
                gameInstance.getScore(currentPlayer),
                gameInstance.getHand(currentPlayer)
        );
        this.previousMessageType=message.type();
        return message;
    }
    public Message drawCard(InParamsDTO inParamsDTO) {
        int affectedPlayerIndex=gameInstance.getCurrentPlayerIndex();

        gameInstance.nextTurn();

        String currentPlayer=gameInstance.getTurn();
        String affectedPlayer=inParamsDTO.username();

        draw(affectedPlayer,inParamsDTO.drawableSection(),inParamsDTO.index());
        checkIfFinalRoundHasToStart(affectedPlayerIndex);
        checkIfGameHasToEnd();

        Message message=MessageCrafter.craftDrawCardMessage(currentPlayer,
                affectedPlayer,
                gameInstance.getHand(affectedPlayer),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea()
                );
        this.previousMessageType=message.type();
        try {
            PersistencyHandler.saveState(gameInstance);
        } catch (IOException e) {
            System.out.println("LOG: Error during state saving");
        }
        return message;
    }
    public Message chat(InParamsDTO inParamsDTO) {
        return MessageCrafter.craftChatMessage(inParamsDTO.username(), inParamsDTO.affectedPlayer(), inParamsDTO.chat());
    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
    }
    public boolean persistencyGameIsFull() {
        return PersistencyHandler.playersTempIsEmpty();
    }
    public boolean firstRoundIsStarted() {
        return gameInstance.firstRoundIsStarted();
    }
    public boolean secondRoundIsStarted() {
        return gameInstance.secondRoundIsStarted();
    }
    public boolean endgameIsStarted(){return gameInstance.isEndgameIsStarted();}
    public boolean finalroundIsStarted(){return gameInstance.isFinalRoundIsStarted();}

    /**
     * Starts a game when all user are joined
     * @throws IOException
     */
    public void startFirstRound(){
        this.gameInstance.startFirstRound();
    }
    public void startSecondRound() {
        this.gameInstance.startSecondRound();
    }
    public Message startGame() {
        this.gameInstance.startGame();
        return MessageCrafter.craftDrawCardMessage(
                gameInstance.getTurn(),
                gameInstance.getTurn(),
                gameInstance.getHand(gameInstance.getTurn()),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea());
    }
    public void startFinalRound(){this.gameInstance.startFinalRound();}
    public void endGame(){
        PersistencyHandler.closePersistence();
        PersistencyHandler.deleteGame();
        this.gameInstance.endGame();
    }
    /**
     *
     * @throws IOException
     */
    public void chooseColor() throws IOException {
    }
    public void shutDown() {

    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public boolean allBoardsAreSet() {
        return this.gameInstance.allBoardsAreSet();
    }

    public boolean allObjectivesHaveBeenChosen() {
        return this.gameInstance.allObjectivesHaveBeenChosen();
    }

    public boolean gameIsStarted() {
        return this.gameInstance.gameIsStarted();
    }

    public Scoreboard getScoreBoard() {
        return gameInstance.getScoreBoard();
    }

    public boolean gameIsEnded() {
        return gameInstance.gameIsEnded();
    }
    public void calculateEndGamePoints(){
        gameInstance.calculateEndgamePoints();
    }
    public boolean persistency() {
        return this.persistency;
    }

    public Message continueGame() {
        return MessageCrafter.craftContinueGameMessage(gameInstance);
    }

    public void turnOffPersistency() {
        this.persistency=false;
    }

    public void unpauseGame() {
        this.gameInstance.unpauseGame();
    }

    /**
     * Helper method used to draw a card for a player
     */
    private void draw(String affectedPlayer, String drawingSection, Integer index) {
        if(drawingSection.equals("resourceDrawableArea")) {
            gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(
                    gameInstance.getDrawableArea().getResourceDrawingSection().drawCard(index)
            );
        }
        if(drawingSection.equals("goldDrawableArea")) {
            gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(
                    gameInstance.getDrawableArea().getGoldDrawingSection().drawCard(index)
            );
        }
    }
    private void checkIfFinalRoundHasToStart(Integer affectedPlayerIndex) {
        if(affectedPlayerIndex == 0 && gameInstance.checkEndgame() && !finalroundIsStarted()){
            startFinalRound();
        }
    }
    private void checkIfGameHasToEnd() {
        if(gameInstance.getCurrentPlayerIndex() == 0 && finalroundIsStarted()) {
            calculateEndGamePoints();
            endGame();
        }
    }
}
