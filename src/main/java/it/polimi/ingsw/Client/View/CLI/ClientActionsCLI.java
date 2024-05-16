package it.polimi.ingsw.Client.View.CLI;

public class ClientActionsCLI {


    private boolean firstRoundActions =false;
    private boolean placeCard=false;
    private boolean flipHand=false;
    private boolean showOtherPlayerBoardAndBackOFHand=false;
    private boolean drawCard=false;
    private boolean secondRoundActions =false;
    private boolean join=false;
    private boolean exit=false;
    private boolean create=false;

    public void enableJoin() {
        join = true;
    }

    public void disableJoin() {
        join = false;
    }

    public boolean isJoinEnabled() {
        return join;
    }

    public void enableExit() {
        exit = true;
    }

    public void disableExit() {
        exit = false;
    }

    public boolean isExitEnabled() {
        return exit;
    }

    public void enableCreate() {
        create = true;
    }

    public void disableCreate() {
        create = false;
    }

    public boolean isCreateEnabled() {
        return create;
    }

    public void enableFirstRoundActions() {
        firstRoundActions = true;
    }
    public void enablePlaceCard() {
        placeCard = true;
    }

    public void enableFlipHand() {
        flipHand = true;
    }
    public void enableShowOtherPlayerBoardAndBackOFHand() {
        showOtherPlayerBoardAndBackOFHand = true;
    }
    public void enableDrawCard() {
        drawCard = true;
    }
    public void enableChooseObjective() {
        secondRoundActions = true;
    }

    public boolean isFirstRoundActionsEnabled() {
        return firstRoundActions;
    }
    public boolean isPlaceCardEnabled() {
        return placeCard;
    }

    public boolean isFlipHandEnabled() {
        return flipHand;
    }
    public boolean isShowOtherPlayerBoardAndBackOFHandEnabled() {
        return showOtherPlayerBoardAndBackOFHand;
    }
    public boolean isDrawCardEnabled() {
        return drawCard;
    }
    public boolean isChooseObjectiveEnabled() {
        return secondRoundActions;
    }

    public void disableFirstRoundActions() {
        firstRoundActions = false;
    }
    public void disablePlaceCard() {
        placeCard = false;
    }

    public void disableFlipHand() {
        flipHand = false;
    }
    public void disableShowOtherPlayerBoardAndBackOFHand() {
        showOtherPlayerBoardAndBackOFHand = false;
    }
    public void disableDrawCard() {
        drawCard = false;
    }
    public void disableChooseObjective() {
        secondRoundActions = false;
    }


}
