package it.polimi.ingsw.Client.Network;

import com.google.gson.*;
import it.polimi.ingsw.Client.Network.DTO.InParamsDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CardDTO;
import it.polimi.ingsw.Client.Network.DTO.ModelDTO.PlacedCardDTO;
import it.polimi.ingsw.Client.View.CLI.CardCLI;
import it.polimi.ingsw.Client.View.GUI.CardGUI;
import it.polimi.ingsw.Client.View.ViewController;
import it.polimi.ingsw.Server.Controller.DTO.OutParamsDTO;
import it.polimi.ingsw.Server.Model.Coordinates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Used to parse data from the incoming message from server
 * */
public class MessageParser {
    /**
     * Instance of the networkManager gateway to the server
     */
    private NetworkManager networkManager;
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
    private Gson parser;
    /**
     * Current input parameters handled by the messageParser
     */
    private InParamsDTO inParamsDTO;
    /**
     * Current messageType handled by the messageParser
     */
    private MessageType messageType;


    private MessageParser(NetworkManager networkManager) {
        this.networkManager=networkManager;
        this.parser=new Gson();
    }

    /**
     * Used to get the singleton instance
     * @param networkManager
     * @return
     */
    public static MessageParser getInstance(NetworkManager networkManager) {
        if(instance==null) {
            instance= new MessageParser(networkManager);
        }
        return instance;
    }
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets the current message data. From now on until the message is consumed the message will remain the same
     * @param inMessage
     */
    public void buildMessage(String inMessage) {
        Message message=parser.fromJson(inMessage,Message.class);
        this.messageType=message.type();
        this.inParamsDTO=parser.fromJson(message.params(), InParamsDTO.class);
        viewController.updateView();
    }
    /**
     * Parses the master status from server
     * @return
     * @throws IOException
     */
    public boolean masterStatus(){
        return inParamsDTO.masterStatus();
    }

    /**
     * Parses if the given username is unavailable
     * @return
     */
    public Boolean unavailableUsername(){
        return inParamsDTO.unavailableUsername();
    }

    /**
     * Parses the current player username
     * @return
     */
    public String getCurrentPlayer() {
        return inParamsDTO.currentPlayer();
    }

    /**
     * Parses the affected player username
     * @return
     */
    public String getAffectedPlayer() {
        return inParamsDTO.affectedPlayer();
    }

    /**
     * Sets the controlled view
     * @param viewController
     */
    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }

    /**
     * Parses the list of winners
     * @return
     */
    public List<String> getWinners() {
        return inParamsDTO.winners();
    }

    /**
     * Parses the ordered list of players
     * @return
     */
    public ArrayList<String> getPlayers() {
        return inParamsDTO.players();
    }
    /**
     * Parses the list of available colors to choose
     * @return
     */
    public ArrayList<String> getAvailableColors() {
        return inParamsDTO.availableColors();
    }

    /**
     * Parses the chosen color by the affected player
     * @return
     */
    public String getColor() {
        return inParamsDTO.color();
    }
    /**
     * Parses the starting card front center symbols
     * @return
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
            Coordinates coordinates= new Coordinates(placedCardDTO.cardCoordinates().x(), placedCardDTO.cardCoordinates().y());
            placedCardsCLI.add(
                    new CardCLI(
                            cardDTO.cardID(),
                            cardDTO.frontCorners(),
                            cardDTO.backCorners(),
                            cardDTO.cardObjective().type(),
                            cardDTO.cardObjective().data().symbolOfInterest(),
                            cardDTO.cardObjective().data().points(),
                            cardDTO.prerequisites(),
                            cardDTO.frontCenterSymbols(),
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
            handCLI[i]=
                    new CardCLI(
                            handDTO[i].cardID(),
                            handDTO[i].frontCorners(),
                            handDTO[i].backCorners(),
                            handDTO[i].cardObjective().type(),
                            handDTO[i].cardObjective().data().symbolOfInterest(),
                            handDTO[i].cardObjective().data().points(),
                            handDTO[i].prerequisites()
                    );
        }
        return handCLI;
    }
    public String[] getHandIDs() {
        String[] handIDs=new String[3];
        CardDTO[] handDTO=inParamsDTO.hand();
        for(int i=0;i<3;i++) {
            handIDs[i]=
                    handDTO[i].cardID();
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

