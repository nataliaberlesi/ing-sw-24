package it.polimi.ingsw.Client.View.CLI;

/**
 * objectives that are in game
 */
public class ObjectivesSectionCLI {



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
     * prints objectives common between players
     */
    public void printCommonObjectives(){
        System.out.println("COMMON OBJECTIVES:");
        for(ObjectiveCLI obj : publicObjectives){
            obj.printObjective();
        }
    }

}
