package it.polimi.ingsw.Client.Network;

import com.google.gson.*;
import it.polimi.ingsw.Client.Network.DTO.InParamsDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.*;
import it.polimi.ingsw.Client.View.CLI.CardCLI;
import it.polimi.ingsw.Client.View.CLI.FinalScoreBoardCLI;
import it.polimi.ingsw.Client.View.CLI.ObjectiveCLI;
import it.polimi.ingsw.Client.View.GUI.CardGUI;
import it.polimi.ingsw.Client.View.ViewController;
import it.polimi.ingsw.Server.Model.Coordinates;
import java.util.ArrayList;

/**
 * Used to parse data from the incoming message from server
 * */
public class MessageParser {
    /**
     * Singleton instance of this
     */
    private static MessageParser instance;
    /**
     * viewController handled by this messageParser
     */
    private ViewController viewController;
    /**
     * Gson instance for message parsing
     */
    private final Gson parser;
    /**
     * Current input parameters handled by the messageParser
     */
    private InParamsDTO inParamsDTO;
    /**
     * Current messageType handled by the messageParser
     */
    private MessageType messageType;


    private MessageParser() {
        this.parser=new Gson();
    }

    /**
     * Used to get the singleton instance
     * @return the singleton instance
     */
    public static MessageParser getInstance() {
        if(instance==null) {
            instance= new MessageParser();
        }
        return instance;
    }
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets the current message data. From now on until the message is consumed the message will remain the same
     * @param inMessage the message to be built
     */
    public void buildMessage(String inMessage) {
        Message message=parser.fromJson(inMessage,Message.class);
        this.messageType=message.type();
        this.inParamsDTO=message.params().serverOutParams();
        viewController.updateView();
    }
    /**
     * Parses the master status from server
     * @return if the current player is the master
     */
    public boolean masterStatus(){
        return inParamsDTO.masterStatus();
    }

    /**
     * Parses if the given username is unavailable
     * @return if the given username is unavailable
     */
    public Boolean unavailableUsername(){
        return inParamsDTO.unavailableUsername();
    }

    /**
     * Parses the current player username
     * @return the current player
     */
    public String getCurrentPlayer() {
        return inParamsDTO.currentPlayer();
    }

    /**
     * Parses the affected player username
     * @return the affected player
     */
    public String getAffectedPlayer() {
        return inParamsDTO.affectedPlayer();
    }

    /**
     * Sets the controlled view
     * @param viewController the new viewController
     */
    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }
    /**
     * Parses the ordered list of players
     * @return the turn order
     */
    public ArrayList<String> getPlayers() {
        return inParamsDTO.players();
    }
    /**
     * Parses the list of available colors to choose
     * @return a list of colors
     */
    public ArrayList<String> getAvailableColors() {
        return inParamsDTO.availableColors();
    }

    /**
     * Parses the chosen color by the affected player
     * @return a color
     */
    public String getColor() {
        return inParamsDTO.color();
    }

    /**
     * Parses the affected player score update
     * @return the score
     */
    public Integer getScore() {
        return inParamsDTO.score();
    }

    /**
     * Parses an ABORT cause
     * @return the cause
     */
    public String getCause() {
        return inParamsDTO.cause();
    }
    public ObjectiveCLI[] getPrivateObjectivesCLI() {
        ObjectiveCLI[] objectiveCLIS=new ObjectiveCLI[2];
        ObjectiveDTO[] objectiveDTOS=inParamsDTO.privateObjectives();
        for(int i=0; i<objectiveDTOS.length;i++) {
            objectiveCLIS[i]=new ObjectiveCLI(objectiveDTOS[i].type(),
                    objectiveDTOS[i].data().points(),
                    objectiveDTOS[i].data().numberOfOccurrences(),
                    objectiveDTOS[i].data().symbolOfInterest());
        }
        return objectiveCLIS;
    }

    /**
     * Parses the public objectives in CLI format
     * @return the public objectives in CLI format
     */
    public ObjectiveCLI[] getPublicObjectivesCLI() {
        ObjectiveCLI[] objectiveCLIS=new ObjectiveCLI[2];
        ObjectiveDTO[] objectiveDTOS=inParamsDTO.publicObjectives();
        for(int i=0; i<objectiveDTOS.length;i++) {
            objectiveCLIS[i]=new ObjectiveCLI(objectiveDTOS[i].type(),
                    objectiveDTOS[i].data().points(),
                    objectiveDTOS[i].data().numberOfOccurrences(),
                    objectiveDTOS[i].data().symbolOfInterest());
        }
        return objectiveCLIS;
    }

    /**
     * Parses the private objectives ID
     * @return the private objectives ID
     */
    public String[] getPrivateObjectivesID() {
        ObjectiveDTO[] objectiveDTOS=inParamsDTO.privateObjectives();
        String[] objectivesIDs= new String[objectiveDTOS.length];
        for(int i=0;i<objectiveDTOS.length;i++) {
            objectivesIDs[i]=objectiveDTOS[i].data().objectiveID();
        }
        return objectivesIDs;
    }

    /**
     * Parses the public objectives ID
     * @return the public objectives ID
     */
    public String[] getPublicObjectivesID() {
        ObjectiveDTO[] objectiveDTOS=inParamsDTO.publicObjectives();
        String[] objectivesIDs= new String[objectiveDTOS.length];
        for(int i=0;i<objectiveDTOS.length;i++) {
            objectivesIDs[i]=objectiveDTOS[i].data().objectiveID();
        }
        return objectivesIDs;
    }

    /**
     * Parses the chosen objective in CLI format
     * @return an Objective in CLI format
     */
    public ObjectiveCLI getChosenObjective() {
        ObjectiveDTO objectiveDTO= inParamsDTO.chosenObjective();
        if(objectiveDTO!=null) {
            return new ObjectiveCLI(objectiveDTO.type(),
                    objectiveDTO.data().points(),
                    objectiveDTO.data().numberOfOccurrences(),
                    objectiveDTO.data().symbolOfInterest());
        }
        return null;
    }
    /**
     * Parses the starting card front center symbols
     * @return the starting card DTO
     */
    private CardDataDTO getCardDTO() {
        return inParamsDTO.card().data();
    }

    /**
     * Gets a cardDTO from a given drawableArea
     * @param drawableArea the given drawableArea
     * @param index the index inside the drawableArea
     * @return the raw CardDataDTO
     */
    private CardDataDTO getCardDTO(String drawableArea, int index) {
        switch(drawableArea) {
            case("goldDrawableArea") -> {
                CardDTO cardDTO=inParamsDTO.goldDrawableArea()[index];
                if(cardDTO!=null) {
                    return inParamsDTO.goldDrawableArea()[index].data();
                }
                return null;
            }
            case("resourceDrawableArea") -> {
                CardDTO cardDTO=inParamsDTO.resourceDrawableArea()[index];
                if(cardDTO!=null) {
                    return inParamsDTO.resourceDrawableArea()[index].data();
                }
                return null;
            }
        } throw new MessageParserException("Not such drawableArea:"+ drawableArea);
    }
    /**
     * Parses the placed cards in CLI format
     * @return an ordered ArrayList of PlacedCards in CLI format
     */
    public ArrayList<CardCLI> getPlacedCardsCLI() {
        ArrayList<PlacedCardDTO> placedCardsDTO=inParamsDTO.placedCards();
        ArrayList<CardCLI> placedCardsCLI=new ArrayList<>();
        for(PlacedCardDTO placedCardDTO: placedCardsDTO) {
            CardDataDTO cardDataDTO =placedCardDTO.placedCard().data();
            String cardObjectiveType=null;
            String cardObjectiveSymbolOfInterest=null;
            Integer cardObjectivePoints=0;
            if(cardDataDTO.cardObjective()!=null) {
                cardObjectiveType= cardDataDTO.cardObjective().type();
                cardObjectiveSymbolOfInterest= cardDataDTO.cardObjective().data().symbolOfInterest();
                cardObjectivePoints= cardDataDTO.cardObjective().data().points();
            }
            ArrayList<String> prerequisites=new ArrayList<>();
            if(cardDataDTO.prerequisites()!=null) {
                prerequisites= cardDataDTO.prerequisites();
            }
            ArrayList<String> frontCenterSymbols=new ArrayList<>();
            if(cardDataDTO.frontCenterSymbols()!=null) {
                frontCenterSymbols= cardDataDTO.frontCenterSymbols();
            }
            Coordinates coordinates= new Coordinates(placedCardDTO.cardCoordinates().x(), placedCardDTO.cardCoordinates().y());
            placedCardsCLI.add(
                    new CardCLI(
                            cardDataDTO.cardID(),
                            cardDataDTO.frontCorners(),
                            cardDataDTO.backCorners(),
                            cardObjectiveType,
                            cardObjectiveSymbolOfInterest,
                            cardObjectivePoints,
                            prerequisites,
                            frontCenterSymbols,
                            coordinates,
                            placedCardDTO.isFacingUp()
                    )
            );
        }
        return placedCardsCLI;
    }

    /**
     * Parses the placed cards in GUI format
     * @return and ordered ArrayList of gui cards
     */
    public ArrayList<CardGUI> getPlacedCardsGUI() {
        ArrayList<CardGUI> placedCardsGUI=new ArrayList<>();
        ArrayList<PlacedCardDTO> placedCardsDTO=inParamsDTO.placedCards();
        for(PlacedCardDTO placedCardDTO: placedCardsDTO) {
            placedCardsGUI.add(
                    new CardGUI(
                            placedCardDTO.placedCard().data().cardID(),
                            new Coordinates(placedCardDTO.cardCoordinates().x(),placedCardDTO.cardCoordinates().y()),
                            placedCardDTO.isFacingUp()
                    )
            );
        }
        return placedCardsGUI;
    }

    /**
     * Parses a Card in CLI format from a given drawableArea
     * @param drawingSection the given drawableArea
     * @param index the index in the drawableArea
     * @return a Card in CLI format
     */
    public CardCLI getCardCLI(String drawingSection, int index) {
        CardDataDTO cardDataDTO =getCardDTO(drawingSection,index);
        if(cardDataDTO !=null) {
            ArrayList<String> prerequisites=new ArrayList<>();
            if (cardDataDTO.prerequisites()!=null) {
                prerequisites= cardDataDTO.prerequisites();
            }
            return new CardCLI(
                    cardDataDTO.cardID(),
                    cardDataDTO.frontCorners(),
                    cardDataDTO.backCorners(),
                    cardDataDTO.cardObjective().type(),
                    cardDataDTO.cardObjective().data().symbolOfInterest(),
                    cardDataDTO.cardObjective().data().points(),
                    prerequisites
            );
        }
        return null;
    }

    /**
     * Parses the starting card in CLI format
     * @return a starting card in CLI format
     */
    public CardCLI getStartingCardCLI() {
        CardDataDTO cardDataDTO =getCardDTO();
        return new CardCLI(
                cardDataDTO.cardID(),
                cardDataDTO.frontCorners(),
                cardDataDTO.backCorners(),
                cardDataDTO.frontCenterSymbols()
        );
    }

    /**
     * Parses the hand in CLI format
     * @return a hand made out of CardCLI
     */
    public CardCLI[] getHandCLI() {
        CardCLI[] handCLI=new CardCLI[3];
        CardDTO[] handDTO=inParamsDTO.hand();
        for(int i=0;i<3;i++) {
            if(handDTO[i]!=null) {
                ArrayList<String> prerequisites=new ArrayList<>();
                if(handDTO[i].data().prerequisites()!=null) {
                    prerequisites=handDTO[i].data().prerequisites();
                }
                handCLI[i]=
                        new CardCLI(
                                handDTO[i].data().cardID(),
                                handDTO[i].data().frontCorners(),
                                handDTO[i].data().backCorners(),
                                handDTO[i].data().cardObjective().type(),
                                handDTO[i].data().cardObjective().data().symbolOfInterest(),
                                handDTO[i].data().cardObjective().data().points(),
                                prerequisites
                        );
            } else {
                handCLI[i]=null;
            }
        }
        return handCLI;
    }

    /**
     * Parses the ID of the cards in the hand
     * @return a hand made out of card ID
     */
    public String[] getHandIDs() {
        String[] handIDs=new String[3];
        CardDTO[] handDTO=inParamsDTO.hand();
        for(int i=0;i<3;i++) {
            if(handDTO[i]!=null) {
                handIDs[i]=
                        handDTO[i].data().cardID();
            } else {
                handIDs[i]=null;
            }
        }
        return handIDs;
    }
    /**
     * Parses the starting card ID
     * @return the card ID
     */
    public String getCardID() {
        return inParamsDTO.card().data().cardID();
    }

    /**
     * Parses the card ID from a given drawableArea
     * @param drawableArea the given drawableArea
     * @param index the index inside the drawableArea
     * @return the card ID
     */
    public String getCardID(String drawableArea, int index) {
        CardDataDTO cardDataDTO =getCardDTO(drawableArea,index);
        if(cardDataDTO !=null) {
            return cardDataDTO.cardID();
        } else {
            return null;
        }
    }
    /**
     * Transforms an Object into a json string
     */
    public String toJson(Object object) {
        return parser.toJson(object);
    }

    /**
     * Parses the final score board into CLI format
     * @return the final scoreboard
     */
    public FinalScoreBoardCLI getFinalScoreBoardCLI() {
        ArrayList<ScoreboardPositionDTO> scoreboardPositionDTOS=inParamsDTO.scoreboard();
        FinalScoreBoardCLI finalScoreBoardCLI=new FinalScoreBoardCLI();
        for(ScoreboardPositionDTO scoreboardPositionDTO:scoreboardPositionDTOS) {
            finalScoreBoardCLI.addPlayer(scoreboardPositionDTO.position(), scoreboardPositionDTO.username(), scoreboardPositionDTO.score());
        }
        return finalScoreBoardCLI;
    }

    /**
     * Parses the final scoreboard in GUI format
     * @return the final scoreboard
     */
    public ArrayList<ScoreboardPositionDTO> getFinalScoreBoardGUI() {
        return inParamsDTO.scoreboard();
    }

    /**
     * Parses the chosen objective ID
     * @return the chosen objective ID
     */
    public String getChosenObjectiveID() {
        return inParamsDTO.chosenObjective().data().objectiveID();
    }

    /**
     * Parses the message in the chat
     * @return the chat message
     */
    public String getChat() {
        return inParamsDTO.chat();
    }
}

