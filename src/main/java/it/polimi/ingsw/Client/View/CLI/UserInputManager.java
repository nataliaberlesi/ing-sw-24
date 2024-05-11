package it.polimi.ingsw.Client.View.CLI;

public class UserInputManager {
    /*
        ALWAYS VALID:
            EXIT
        ALWAYS VALID DOORING GAME:
            SHOW "NAME_OF_PLAYER"
        LOGIN:
            USERNAME
        LOGIN MASTER:
            USERNAME
            NUMBER OF PLAYERS
        DOORING GAME:
            SHOW "USERNAME"
        FIRST ROUND ANYTIME:
            FLIP
        FIRST ROUND DOORING TURN:
            1)PLACE
            2)"COLOR"
        SECOND ROUND:
            #OBJECTIVE (1/2)
         NORMAL ROUND:
            1)PLACE #NUMBER_OF_CARD_IN_HAND FACE_UP/FACE_DOWN
            2)DRAW RESOURCE_CARD/GOLD_CARD #INDEX_OF_CARD
     */

    private ViewControllerCLI viewController;

    public void doAction(String userInput){
        String[] args = userInput.split(" ");

        switch(args[0].toLowerCase()){
            case "draw"->{

            }
            case "show"->{

            }

        }
    }


}
