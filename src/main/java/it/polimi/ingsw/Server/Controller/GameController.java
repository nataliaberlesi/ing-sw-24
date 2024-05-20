package it.polimi.ingsw.Server.Controller;


import com.google.gson.JsonObject;
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
    public GameController(Server server) {
        this.server=server;
        this.messageParser=MessageParser.getINSTANCE();
    }

    public Message dispatchMessage(String message) {
        Message currentMessage=messageParser.parseMessage(message);
        MessageType messageType=currentMessage.type();
        JsonObject jsonParams=currentMessage.params();
        return queryModel(messageType, jsonParams);
    }
    /**
     * Given a specific type of message, queries the model to get/change some information
     */
    public Message queryModel(MessageType messageType,JsonObject jsonParams) {
        switch (messageType) {
            case CREATE -> {
                return createGame(jsonParams);
            }
            case JOIN -> {
                return joinGame(jsonParams);
            }
            case FIRSTROUND -> {
                return playFirstRound(jsonParams);
            }
            case SECONDROUND -> {
                //TODO
                return playSecondRound(jsonParams);
            }
            case ACTION_PLACECARD -> {
                //TODO
                return placeCard(jsonParams);
            }
            case ACTION_DRAWCARD -> {
                //TODO
                return drawCard(jsonParams);
            }
            case ABORT -> {
                //TODO
            }
        }
        return null;
    }

    /**
     * Creates a game using master player data
     */
    public Message createGame(JsonObject jsonParams){
        if(this.gameInstance!=null){
            MessageType type=MessageType.ABORT;
            String params="Game is already created";
            return new Message(type, null);
        }
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonParams);
        this.server.setMaxAllowablePlayers(inParamsDTO.numberOfPlayers());
        this.gameInstance=new GameInstance(inParamsDTO.username(),inParamsDTO.numberOfPlayers());
        Message message = MessageCrafter.craftCreateMessage(inParamsDTO.username());
        previousMessageType=message.type();
        return message;
    }

    /**
     * Lets a player join a game if his username is not taken
     * @param jsonParams username of the joining player
     * @return an affermative answer if the username is available, a negative answer otherwise
     */
    public Message joinGame(JsonObject jsonParams) {
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonParams);
        Boolean unavailableUsername=this.gameInstance.unavailableUsername(inParamsDTO.username());
        if(!unavailableUsername){
            gameInstance.joinPlayer(inParamsDTO.username());
        }
        Message message = MessageCrafter.craftJoinMessage(inParamsDTO.username(),unavailableUsername);
        previousMessageType=message.type();
        return message;

    }
    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters in JSON format
     */
    public JsonObject getJSONStartFirstRoundParams() {
        OutParamsDTO startFirstRound=SetUpGame.getStartFirstRoundParams(gameInstance);
        return messageParser.toJsonObject(startFirstRound);
    }

    /**
+     * Plays the current player's first round with given parameters
     * @param jsonParams the parameters of his action
     * @return
     */
    public Message playFirstRound(JsonObject jsonParams) {
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonParams);
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
    public JsonObject getJSONStartSecondRoundParams() {
        OutParamsDTO startSecondRound=SetUpGame.getStartSecondRoundParams(gameInstance);
        return messageParser.toJsonObject(startSecondRound);
    }
    public Message playSecondRound(JsonObject jsonParams) {
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonParams);
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
    public Message placeCard(JsonObject jsonParams) {
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonParams);
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
    public Message drawCard(JsonObject jsonObject) {
        InParamsDTO inParamsDTO=messageParser.parseInParamsDTO(jsonObject);
        gameInstance.nextTurn();
        String currentPlayer=gameInstance.getTurn();
        String affectedPlayer=inParamsDTO.username();
        if(inParamsDTO.drawableSection().equals("resourceDrawableArea")) {
            gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(
                    gameInstance.getDrawableArea().getResourceDrawingSection().drawCard(inParamsDTO.index())
            );
        }
        if(inParamsDTO.drawableSection().equals("goldDrawableArea")) {
            gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(
                    gameInstance.getDrawableArea().getGoldDrawingSection().drawCard(inParamsDTO.index())
            );
        }
        if(!endgameIsStarted()) {
            if(gameInstance.checkEndgame() && !endgameIsStarted()){
                startEndgame();
            }
        } else{
            if(gameInstance.getCurrentPlayerIndex()==0 && endgameIsStarted() && !finalroundIsStarted()){
                startFinalRound();
            }
            if(gameInstance.getCurrentPlayerIndex()== gameInstance.getNumberOfPlayers()-1 && endgameIsStarted() && finalroundIsStarted()) {
                calculateEndGamePoints();
                endGame();
            }
        }

        Message message=MessageCrafter.craftDrawCardMessage(currentPlayer,
                affectedPlayer,
                gameInstance.getHand(affectedPlayer),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea()
                );
        this.previousMessageType=message.type();
        return message;
    }
    public boolean gameIsFull() {
        return this.gameInstance.checkIfGameIsFull();
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
    public void startGame() {
        this.gameInstance.startGame();
    }
    public void startEndgame(){this.gameInstance.startEndgame();}
    public void startFinalRound(){this.gameInstance.startFinalRound();}
    public void endGame(){this.gameInstance.endGame();}
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
}
