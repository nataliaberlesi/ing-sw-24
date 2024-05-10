package it.polimi.ingsw.Client.View.CLI;

/**
 * each player is presented with a choice between two objectives.
 */
public class ShowObjectiveChoices {

    /**
     * list of objectives to choose from
     */
    private final ObjectiveCLI[] objectivesToChoseFrom=new ObjectiveCLI[2];

    /**
     *
     * @param objectiveCLI1 first objective the user can choose
     * @param objectiveCLI2 second objective user can choose
     */
    public ShowObjectiveChoices(ObjectiveCLI objectiveCLI1, ObjectiveCLI objectiveCLI2) {
        this.objectivesToChoseFrom[0]=objectiveCLI1;
        this.objectivesToChoseFrom[1]=objectiveCLI2;
    }

    /**
     * prints out the two objectives the player can choose
     */
    public void showObjectiveChoices() {
        System.out.println("Pick an objective (respond with 1 or 2)");
        System.out.println("1:");
        objectivesToChoseFrom[0].printObjective();
        System.out.println("2:");
        objectivesToChoseFrom[1].printObjective();
    }

    /**
     *
     * @param indexOfObjective to determine which objective to get
     * @return the objective at the index given
     * @throws IllegalArgumentException if the index is smaller than 0 or bigger than 1
     */
    public ObjectiveCLI getObjectiveCLI(int indexOfObjective) throws IllegalArgumentException {
        if( indexOfObjective == 1 || indexOfObjective == 0 ){
            return objectivesToChoseFrom[indexOfObjective];
        }
        throw new IndexOutOfBoundsException("Only two objectives are present");
    }

}
