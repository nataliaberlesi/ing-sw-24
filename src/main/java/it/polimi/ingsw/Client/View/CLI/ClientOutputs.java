package it.polimi.ingsw.Client.View.CLI;

/**
 * all constant messages shown to client are grouped here
 */
public class ClientOutputs {
    public static final String loadingScreen = "All good\nWaiting for players...\n\n";
    public static final String askIfPlayerWantsToContinueGame = "There is a saved game, do you want to continue?\n(type Y to continue or N to start a new game";
    public static final String titleScreen = """
             _____  _____ ______  _____ __   __
            /  __ \\|  _  ||  _  \\|  ___|\\ \\ / /
            | /  \\/| | | || | | || |__   \\ V /\s
            | |    | | | || | | ||  __|  /   \\\s
            | \\__/\\\\ \\_/ /| |/ / | |___ / /^\\ \\
             \\____/ \\___/ |___/  \\____/ \\/   \\/
                                              \s""";
    public static final String itsClientsTurn = "It's your turn!";
    public static final String itsNotClientsPlayersTurn = "Wait for your turn...\nRemember you can type HELP at any moment to see all the commands you can use";
    public static final String inputHandlerHasTerminated = "Input handler has terminated";
    public static final String continueGameAnswerNotValid="You can only respond Y or N";
    public static final String validPrivateObjectiveChoice="Hmm that looks like a tough objective, but ok, I'll let the server know";
    public static final String invalidPrivateObjectiveChoice = "try again, accepted indexes are either 1 or 2";
    public static final String invalidChatMessageFormat = "message invalid, message must be in the form -all/username(to indicate the receiver): body of message";
    public static final String invalidPlaceCardFormat = "remember to follow the instructions to place card.\nIf you need help, type help";
    public final static String invalidDrawCardFormat = "remember to follow the instructions to draw card.\nIf you need help, type help";
    public static final String gameCommands = """
                            ACTIONS:

                            1) PLACE CARD
                            To place a card simply type PLACE followed by the index of the card in your hand you want to place,
                            followed by the coordinates where you want to place the card 'x,y',
                            followed UP/DOWN depending on weather you want the card facing up or facing down
                            EXAMPLE: PLACE 3 -4,7 DOWN

                            2) DRAW CARD
                            To draw a card simply type DRAW followed by GOLD/RESOURCE and the index of the card you want to draw
                            EXAMPLE: DRAW RESOURCE 2
                            
                            3) SEE OTHER PLAYER BOARD AND BACK OF HAND
                            You can see another players board and the back of their hand by typing
                            SHOW followed by the username of the player you want to see
                            EXAMPLE: SHOW player3
                            
                            3) SEND MESSAGE
                            If you want send a message to other players type T then the username of the player you want to talk to
                            or -ALL if you want to send a message to everyone, then : followed by your message.
                            EXAMPLE: T -all: hello everybody
                                     T kevin: i think you are very cute <3
                            
                            4) SEE COMPLETE CHAT
                            If you want to see all messages sent up to this point simply type CHAT
                            
                            5) EXIT
                            To exit the game simply type EXIT, though I will personally get offended if you do decide to leave...
                            EXAMPLE: exit""";
    public static final String congratulationsOnFirstCard = "Congrats! You have placed your first card";
    public static final String confirmationOfColorChoice = "\nGREAT CHOICE!\n";
    public static final String confirmationOfUnoriginalColorChoice = "\nGREEN IS NOT A CREATIVE COLOR\n";
    public static final String invalidColorChoice = "I'm afraid that color is unavailable, let's try again";
    public static final String enableColorChoice = "Now it's time to choose your color:\nSimply type one of these colors to choose one";
    public static final String chooseUsername = "Choose your username";
    public static final String chooseUsernameAndNumberOfPlayers = "Choose your username and number of players (min 2, max 4):";
    public static final String enablePlaceStartingCard = """
                You have been assigned a starting card!
                You can flip the card by typing 'F'
                When you are happy with the cards orientation type 'Place'
                once placed you can't change card orientation""";
    public static final String enablePlaceCard="""
                Place a card
                type "place", then the index of card in your hand you want to place,
                then the coordinates of where you want to place them, like '0,0' ,
                and then UP or DOWN depending if you want the card face up or not""";
    public static final String enableDrawCard = """
                Draw a card
                type "draw", then gold/resource,
                and then the index of the card you want to draw""";
    public static final String enableChoosePrivateObjective = """
                If you look up you will see the objectives for this game.
                Those objectives are valid for all players,
                but each of you can choose a private objective.
                You can choose from these two:""";
    public static final String choosePrivateObjectiveInstructions = "Type 1 to choose the first one, type 2 to choose the second one";

}