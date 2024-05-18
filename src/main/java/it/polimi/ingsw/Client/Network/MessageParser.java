package it.polimi.ingsw.Client.Network;

import com.google.gson.*;
import it.polimi.ingsw.Client.Network.DTO.InParamsDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CardDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.ObjectiveDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.PlacedCardDTO;
import it.polimi.ingsw.Client.View.CLI.CardCLI;
import it.polimi.ingsw.Client.View.CLI.ObjectiveCLI;
import it.polimi.ingsw.Client.View.GUI.CardGUI;
import it.polimi.ingsw.Client.View.ViewController;
import it.polimi.ingsw.Server.Model.Coordinates;
import java.util.ArrayList;
import java.util.List;

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
        this.inParamsDTO=parser.fromJson(message.params(), InParamsDTO.class);
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
     * Parses the list of winners
     * @return the list of possible winners
     */
    public List<String> getWinners() {
        return inParamsDTO.winners();
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
    public ObjectiveCLI getChosenObjective() {
        ObjectiveDTO objectiveDTO= inParamsDTO.chosenObjective();
        return new ObjectiveCLI(objectiveDTO.type(),
                objectiveDTO.data().points(),
                objectiveDTO.data().numberOfOccurrences(),
                objectiveDTO.data().symbolOfInterest());
    }
    /**
     * Parses the starting card front center symbols
     * @return the starting card DTO
     */
    private CardDTO getCardDTO() {
        return inParamsDTO.card();
    }

    /**
     * Gets a cardDTO from a given drawableArea
     * @param drawableArea the given drawableArea
     * @param index the index inside the drawableArea
     * @return
     */
    private CardDTO getCardDTO(String drawableArea, int index) {
        switch(drawableArea) {
            case("goldDrawableArea") -> {
                return inParamsDTO.goldDrawableArea()[index];
            }
            case("resourceDrawableArea") -> {
                return inParamsDTO.resourceDrawableArea()[index];
            }
        } throw new MessageParserException("Not such drawableArea:"+ drawableArea);
    }

    /**
     * Parses a CardCLI from the message
     * @return
     */
    public CardCLI getCardCLI() {
        CardDTO cardDTO=getCardDTO();
        ArrayList<String> prerequisites=new ArrayList<>();
        if (cardDTO.prerequisites()!=null) {
            prerequisites=cardDTO.prerequisites();
        }
        return new CardCLI(
                cardDTO.cardID(),
                cardDTO.frontCorners(),
                cardDTO.backCorners(),
                cardDTO.cardObjective().type(),
                cardDTO.cardObjective().data().symbolOfInterest(),
                cardDTO.cardObjective().data().points(),
                prerequisites
        );
    }

    /**
     * Parses the placed cards in CLI format
     * @return an ordered ArrayList of PlacedCards in CLI format
     */
    public ArrayList<CardCLI> getPlacedCardsCLI() {
        ArrayList<PlacedCardDTO> placedCardsDTO=inParamsDTO.placedCards();
        ArrayList<CardCLI> placedCardsCLI=new ArrayList<>();
        for(PlacedCardDTO placedCardDTO: placedCardsDTO) {
            CardDTO cardDTO=placedCardDTO.placedCard();
            String cardObjectiveType=null;
            String cardObjectiveSymbolOfInterest=null;
            Integer cardObjectivePoints=0;
            if(cardDTO.cardObjective()!=null) {
                cardObjectiveType= cardDTO.cardObjective().type();
                cardObjectiveSymbolOfInterest=cardDTO.cardObjective().data().symbolOfInterest();
                cardObjectivePoints=cardDTO.cardObjective().data().points();
            }
            ArrayList<String> prerequisites=new ArrayList<>();
            if(cardDTO.prerequisites()!=null) {
                prerequisites=cardDTO.prerequisites();
            }
            ArrayList<String> frontCenterSymbols=new ArrayList<>();
            if(cardDTO.frontCenterSymbols()!=null) {
                frontCenterSymbols=cardDTO.frontCenterSymbols();
            }
            Coordinates coordinates= new Coordinates(placedCardDTO.cardCoordinates().x(), placedCardDTO.cardCoordinates().y());
            placedCardsCLI.add(
                    new CardCLI(
                            cardDTO.cardID(),
                            cardDTO.frontCorners(),
                            cardDTO.backCorners(),
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
            Coordinates coordinates=new Coordinates(placedCardDTO.cardCoordinates().x(),placedCardDTO.cardCoordinates().y());
            placedCardsGUI.add(
                    new CardGUI(
                            placedCardDTO.placedCard().cardID(),
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
        CardDTO cardDTO=getCardDTO(drawingSection,index);
        if(cardDTO!=null) {
            ArrayList<String> prerequisites=new ArrayList<>();
            if (cardDTO.prerequisites()!=null) {
                prerequisites=cardDTO.prerequisites();
            }
            return new CardCLI(
                    cardDTO.cardID(),
                    cardDTO.frontCorners(),
                    cardDTO.backCorners(),
                    cardDTO.cardObjective().type(),
                    cardDTO.cardObjective().data().symbolOfInterest(),
                    cardDTO.cardObjective().data().points(),
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
        CardDTO cardDTO=getCardDTO();
        return new CardCLI(
                cardDTO.cardID(),
                cardDTO.frontCorners(),
                cardDTO.backCorners(),
                cardDTO.frontCenterSymbols()
        );
    }
    public CardCLI[] getHandCLI() {
        CardCLI[] handCLI=new CardCLI[3];
        CardDTO[] handDTO=inParamsDTO.hand();
        for(int i=0;i<3;i++) {
            if(handDTO[i]!=null) {
                ArrayList<String> prerequisites=new ArrayList<>();
                if(handDTO[i].prerequisites()!=null) {
                    prerequisites=handDTO[i].prerequisites();
                }
                handCLI[i]=
                        new CardCLI(
                                handDTO[i].cardID(),
                                handDTO[i].frontCorners(),
                                handDTO[i].backCorners(),
                                handDTO[i].cardObjective().type(),
                                handDTO[i].cardObjective().data().symbolOfInterest(),
                                handDTO[i].cardObjective().data().points(),
                                prerequisites
                        );
            } else {
                handCLI[i]=null;
            }
        }
        return handCLI;
    }
    public String[] getHandIDs() {
        String[] handIDs=new String[3];
        CardDTO[] handDTO=inParamsDTO.hand();
        for(int i=0;i<3;i++) {
            if(handDTO[i]!=null) {
                handIDs[i]=
                        handDTO[i].cardID();
            } else {
                handIDs[i]=null;
            }
        }
        return handIDs;
    }
    /**
     * Parses the starting card ID
     * @return
     */
    public String getCardID() {
        return inParamsDTO.card().cardID();
    }

    /**
     * Parses the card ID from a given drawableArea
     * @param drawableArea the given drawableArea
     * @param index the index inside the drawableArea
     * @return
     */
    public String getCardID(String drawableArea, int index) {
        switch (drawableArea) {
            case("resourceDrawableArea")->{
                return inParamsDTO.resourceDrawableArea()[index].cardID();
            }
            case("goldDrawableArea")->{
                return inParamsDTO.goldDrawableArea()[index].cardID();
            }
        }
        throw new MessageParserException("Not such case exception: "+drawableArea);
    }

    /**
     * Transforms an Object into a navigable JsonObject
     * @param object
     * @return
     */
    public JsonObject toJsonObject(Object object) {
        return parser.toJsonTree(object).getAsJsonObject();
    }

    /**
     * Transforms an Object into a json string
     * @param object
     * @return
     */
    public String toJson(Object object) {
        return parser.toJson(object);
    }

}

