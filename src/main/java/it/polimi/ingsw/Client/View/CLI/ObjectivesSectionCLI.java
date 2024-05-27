package it.polimi.ingsw.Client.View.CLI;

/**
 * objectives that are in game, 2 objectives are public and are applied to all players,
 * every player also has a private objective that only applies to them and only they can see it
 */
public class ObjectivesSectionCLI {


    /**
     * clients private objective, only client can view it
     */
    private ObjectiveCLI privateObjective;

    /**
     * objectives that are common between players
     */
    private final ObjectiveCLI[] publicObjectives=new ObjectiveCLI[2];

    /**
     *
     * @param objective that is being added, common amongst all players
     * @throws RuntimeException if objective is null, or if it is trying to take place of objective already in play
     */
    public void addObjective(ObjectiveCLI objective) throws RuntimeException{
        if(objective == null){
            throw new RuntimeException("Objective is null");
        }
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

    /**
     *
     * @param privateObjective is objective visible only to specific player
     * @throws RuntimeException if private objective was already assigned
     */
    public void setPrivateObjective(ObjectiveCLI privateObjective) throws RuntimeException{
        if(!(this.privateObjective == null)){
            throw new RuntimeException("Private Objective already set");
        }
        this.privateObjective = privateObjective;
    }

    /**
     * prints objectives common between players
     */
    private void printCommonObjectives(){
        System.out.println("COMMON OBJECTIVES:");
        for(ObjectiveCLI obj : publicObjectives){
            if(obj == null){
                System.out.println("-");
            }
            else {
                obj.printObjective();
            }
        }
    }

    /**
     * prints objective of player in control of view
     */
    private void printPrivateObjective(){
        System.out.println("PRIVATE OBJECTIVE:");
        if(privateObjective == null){
            System.out.println("-");
        }
        else {
            privateObjective.printObjective();
        }
    }

    /**
     * shows all objectives that effect client
     */
    public void printObjectivesSection(){
        printCommonObjectives();
        printPrivateObjective();
    }

}
