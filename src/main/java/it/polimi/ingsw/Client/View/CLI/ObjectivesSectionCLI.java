package it.polimi.ingsw.Client.View.CLI;

/**
 * objectives that are in game
 */
public class ObjectivesSectionCLI {

    /**
     * objective that are specific to player
     */
    private ObjectiveCLI privateObjective;

    /**
     * objectives that are common between players
     */
    private final ObjectiveCLI[] publicObjectives=new ObjectiveCLI[2];

    /**
     *
     * @param objective that is being added
     * @param isPrivate true if objective specific to player
     * @throws RuntimeException if objective is null, or if it is trying to take place of objective already in play
     */
    public void addObjective(ObjectiveCLI objective, boolean isPrivate) throws RuntimeException{
        if(objective == null){
            throw new RuntimeException("Objective is null");
        }
        if(isPrivate){
            if(privateObjective != null){
                throw new RuntimeException("Player already has a private objective");
            }
            privateObjective = objective;
        }
        else {
            if(publicObjectives[0] == null){
                publicObjectives[0] = objective;
            }
            else if(publicObjectives[1] == null){
                publicObjectives[1] = objective;
            }
            else {
                throw new RuntimeException("Player already has a public objective");
            }
        }
    }

    /**
     * prints objectives common between players
     */
    public void printCommonObjectives(){
        System.out.println("COMMON OBJECTIVES:");
        for(ObjectiveCLI obj : publicObjectives){
            obj.printObjective();
        }
    }

    /**
     * prints objective specific to player
     */
    public void printPrivateObjective(){
        System.out.println("PRIVATE OBJECTIVES:");
        privateObjective.printObjective();
    }
}
