package it.polimi.ingsw.Client.View.CLI;

public class PlayerActionCLI {

    private String action;
    private boolean enabled=false;

    public PlayerActionCLI(String action) {

    }

    public void enableAction(){
        enabled=true;
    }

    public void disableAction(){
        enabled=false;
    }

    public String getAction() {
        return action;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public enum typesOfAction{
        EXIT,SHOW,PLACE,DRAW,FLIP,USERNAME,OBJECTIVE,NUMBER_OF_PLAYERS
    }
}
