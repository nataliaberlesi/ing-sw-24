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
    private GameInstance gameInstance;
    private final Server server;
    private boolean persistence;
    public GameController(Server server){
        this.server=server;
        persistence =false;
    }

    public Message dispatchMessage(Message message) {
        MessageType messageType=message.type();
        InParamsDTO inParamsDTO=message.params().clientOutParams();
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
                return playSecondRound(inParamsDTO);
            }
            case ACTION_PLACECARD -> {
                return placeCard(inParamsDTO);
            }
            case ACTION_DRAWCARD -> {
                return drawCard(inParamsDTO);
            }
            case CHAT -> {
                return chat(inParamsDTO);
            }
            case ABORT -> {
                return closeGame(inParamsDTO);
            }
            case POKE->{
                return MessageCrafter.craftPokeMessage();
            }
        }
        return null;
    }
    private Message checkPersistency(InParamsDTO inParamsDTO) {
        persistence =inParamsDTO.persistence();
        if(persistence) {
            try {
                gameInstance= PersistenceHandler.fetchState();
                PersistenceHandler.setPlayersTemp((ArrayList<String>) gameInstance.getPlayerTurnOrder().clone());
                server.openLobby(gameInstance.getNumberOfPlayers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return MessageCrafter.craftConnectMessage(false);
        } else {
            PersistenceHandler.deleteGame();
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
        return MessageCrafter.craftCreateMessage(inParamsDTO.username().toUpperCase());
    }

    /**
     * Lets a player join a game if his username is not taken
     * @return an affermative answer if the username is available, a negative answer otherwise
     */
    public Message joinGame(InParamsDTO inParamsDTO) {
        String username=inParamsDTO.username().toUpperCase();
        boolean unavailableUsername;
        if(persistence) {
            unavailableUsername= PersistenceHandler.checkPlayerTemp(username);
        } else {
            unavailableUsername=this.gameInstance.unavailableUsername(username);
            if(!unavailableUsername){
                gameInstance.joinPlayer(username);
            }
        }
        return MessageCrafter.craftJoinMessage(inParamsDTO.username(),unavailableUsername);

    }
    /**
     * Generates first round parameters and sets DrawableArea for the game instance
     * @return the starting first round parameters as message
     */
    public Message doStartFirstRound() {
        ParamsDTO params=SetUpGame.getStartFirstRoundParams(gameInstance);
        return new Message(MessageType.START_FIRSTROUND,params);
    }

    /**
+     * Plays the current player's first round with given parameters. When the first round is ended, starts the second round
     * @return a FIRSTROUND message
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
        return MessageCrafter.craftFirstRoundMessage(card,currentPlayer,gameInstance.getAvailableColors(),inParamsDTO.username(),placedCards,inParamsDTO.color());
    }
    /**
     * Generates second round parameters
     * @return the starting second round parameters as message
     */
    public Message doStartSecondRound() {
        ParamsDTO startSecondRound=SetUpGame.getStartSecondRoundParams(gameInstance);
        return new Message(MessageType.START_SECONDROUND,startSecondRound);
    }

    /**
     * Plays the current player's second round with given parameters. When the second round is ended, starts the game
     * @return a SECONDROUND message
     */
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
        gameInstance.checkIfAllObjectivesHaveBeenChosen();
        return MessageCrafter.craftSecondRoundMessage(
                currentPlayer,
                inParamsDTO.username(),
                hand,
                privateObjectives,
                gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerBoard().seeObjective(2)
        );
    }

    /**
     * Tries to place a card. If it's placeable, places it in the board and removes it from the hand.
     * If the drawable area is empty, the turn in the gameInstance is increased
     * @return
     * a ACTION_DRAWCARD with the same currentplayer if the action was illegal
     * a ACTION_PLACECARD with the same currentplayer if the action was legal
     * a ACTION_DRAWCARD with a new currentplayer if the drawableArea is empty
     */
    public Message placeCard(InParamsDTO inParamsDTO) {
        if(!place(inParamsDTO)) {
            return MessageCrafter.craftDrawCardMessage(
                    inParamsDTO.username(),
                    inParamsDTO.username(),
                    gameInstance.getPlacedCards(inParamsDTO.username()),
                    gameInstance.getScore(inParamsDTO.username()),
                    gameInstance.getHand(inParamsDTO.username()),
                    gameInstance.getResourceDrawableArea(),
                    gameInstance.getGoldDrawableArea()
            );
        }
        gameInstance.getCardFromPlayerHand(inParamsDTO.username(),inParamsDTO.index());
        if(!gameInstance.getDrawableArea().isEmpty()) {
            return MessageCrafter.craftPlaceCardMessage(
                    inParamsDTO.username(),
                    inParamsDTO.username(),
                    gameInstance.getPlacedCards(inParamsDTO.username()),
                    gameInstance.getScore(inParamsDTO.username()),
                    gameInstance.getHand(inParamsDTO.username())
            );
        }
        checkIfFinalRoundHasToStart(gameInstance.getCurrentPlayerIndex());
        checkIfGameHasToEnd();
        gameInstance.nextTurn();
        return MessageCrafter.craftDrawCardMessage(
                gameInstance.getTurn(),
                inParamsDTO.username(),
                gameInstance.getPlacedCards(inParamsDTO.username()),
                gameInstance.getScore(inParamsDTO.username()),
                gameInstance.getHand(inParamsDTO.username()),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea()
        );
    }
    public Message drawCard(InParamsDTO inParamsDTO) {
        int affectedPlayerIndex=gameInstance.getCurrentPlayerIndex();

        String affectedPlayer=inParamsDTO.username();

        boolean isLegal=draw(affectedPlayer,inParamsDTO.drawableSection(),inParamsDTO.index());
        checkIfFinalRoundHasToStart(affectedPlayerIndex);
        checkIfGameHasToEnd();
        Message message;
        if(isLegal) {
            gameInstance.nextTurn();
            String currentPlayer=gameInstance.getTurn();

            message=MessageCrafter.craftDrawCardMessage(currentPlayer,
                    affectedPlayer,
                    gameInstance.getPlacedCards(affectedPlayer),
                    gameInstance.getScore(affectedPlayer),
                    gameInstance.getHand(affectedPlayer),
                    gameInstance.getResourceDrawableArea(),
                    gameInstance.getGoldDrawableArea()
            );
        } else {
            message=MessageCrafter.craftPlaceCardMessage(affectedPlayer,
                    affectedPlayer,
                    gameInstance.getPlacedCards(affectedPlayer),
                    gameInstance.getScore(affectedPlayer),
                    gameInstance.getHand(affectedPlayer)
            );
        }
        try {
            PersistenceHandler.saveState(gameInstance);
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
        return PersistenceHandler.playersTempIsEmpty();
    }
    public boolean firstRoundIsStarted() {
        return gameInstance.firstRoundIsStarted();
    }
    public boolean secondRoundIsStarted() {
        return gameInstance.secondRoundIsStarted();
    }
    public boolean finalroundIsStarted(){return gameInstance.isFinalRoundIsStarted();}

    /**
     * Starts a game when all user are joined
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
                gameInstance.getPlacedCards(gameInstance.getTurn()),
                gameInstance.getScore(gameInstance.getTurn()),
                gameInstance.getHand(gameInstance.getTurn()),
                gameInstance.getResourceDrawableArea(),
                gameInstance.getGoldDrawableArea());
    }
    public void startFinalRound(){this.gameInstance.startFinalRound();}
    public void endGame(){
        PersistenceHandler.closePersistence();
        PersistenceHandler.deleteGame();
        this.gameInstance.endGame();
    }
    public boolean gameInstanceExists() {
        return gameInstance!=null;
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
        return this.persistence;
    }

    public Message continueGame() {
        return MessageCrafter.craftContinueGameMessage(gameInstance);
    }

    public void turnOffPersistency() {
        this.persistence =false;
    }

    public void unpauseGame() {
        this.gameInstance.unpauseGame();
    }

    /**
     * Helper method used to check if a card is placeable.
     * If it's placeable, it places it.
     * @return
     * true if the card is placeable
     * false if the hand index contains a null card or if the card is not placeable
     */
    private boolean place(InParamsDTO inParamsDTO) {
        String cardID=gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerHand().showCardInHand(inParamsDTO.index());
        if(cardID==null) {
            return false;
        }
        return gameInstance.getPlayers().get(inParamsDTO.username()).getPlayerBoard().placeCard(
                cardID,
                inParamsDTO.coordinates(),
                inParamsDTO.isFacingUp());
    }
    /**
     * Helper method used to draw a card for a player
     */
    private boolean draw(String affectedPlayer, String drawingSection, Integer index) {
        String drawnCard;
        if(drawingSection.equals("resourceDrawableArea")) {
            drawnCard=gameInstance.getDrawableArea().getResourceDrawingSection().drawCard(index);
            if(drawnCard!=null) {
                gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(drawnCard);
                return true;
            }
            return false;
        }
        if(drawingSection.equals("goldDrawableArea")) {
            drawnCard=gameInstance.getDrawableArea().getGoldDrawingSection().drawCard(index);
            if(drawnCard!=null) {
                gameInstance.getPlayers().get(affectedPlayer).getPlayerHand().placeCardInHand(drawnCard);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Helper method to check if final round requirements are meet
     */
    private void checkIfFinalRoundHasToStart(Integer affectedPlayerIndex) {
        if(affectedPlayerIndex == 0 && gameInstance.checkEndgame() && !finalroundIsStarted()){
            startFinalRound();
        }
    }

    /**
     * Helper method to check if game has to end
     */
    private void checkIfGameHasToEnd() {
        if(gameInstance.getCurrentPlayerIndex() == gameInstance.getNumberOfPlayers()-1 && finalroundIsStarted()) {
            calculateEndGamePoints();
            endGame();
        }
    }
}
