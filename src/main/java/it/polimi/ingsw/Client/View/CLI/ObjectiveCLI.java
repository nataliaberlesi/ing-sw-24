package it.polimi.ingsw.Client.View.CLI;

public class ObjectiveCLI {

    /**
     * objective as seen in CLI
     */
    private String objective="Earn";


    /**
     * constructs the string that describes the objective
     * @param objectiveClass indicates what objective will be shown
     * @param objectivePoints are the points earned by completing objective
     * @param numberOfOccurrences how many occurrences of a symbol need to be present on a board in order to earn points
     * @param objectiveSymbol symbol that will be used in the model to calculate points
     * @throws IllegalArgumentException if the objective class passed doesn't exist
     */
    public ObjectiveCLI(String objectiveClass, int objectivePoints, int numberOfOccurrences, String objectiveSymbol) throws IllegalArgumentException{
        switch (objectiveClass){
            case "DiagonalPatternObjective"->{
                this.objective += objectivePoints+" points for each diagonal pattern like this:\n";
                setDiagonalPatternObjective(objectiveSymbol);
            }
            case "VerticalPatternObjective"->{
                this.objective += objectivePoints+" points for each vertical pattern like this:\n";
                setVerticalPatternObjective(objectiveSymbol);
            }
            case "SymbolObjective"->{
                this.objective += objectivePoints+" points for every " + numberOfOccurrences + " " + objectiveSymbol + " symbols visible on your board:\n";
            }
            case "MixSymbolObjective"->{
                this.objective += objectivePoints+" points for each set of three different symbols (INK,SCROLL,FEATHER)\n";
            }
            default -> {
                throw new IllegalArgumentException(objectiveClass + " is not a valid objective");
            }
        }
    }



    /**
     * builds string showing the pattern of cards that earns points
     * @param specificType is the symbol that is used to determine which pattern earns points
     */
    private void setVerticalPatternObjective(String specificType) {
        StringBuilder verticalPattern=new StringBuilder();
        String emptySpace=createEmptyString(objective.length()/2);
        verticalPattern.append(emptySpace);
        switch (specificType){
            case "MUSHROOM":
                verticalPattern.append(ColoredText.ANSI_RED);
                verticalPattern.append("C\n");
                verticalPattern.append(emptySpace);
                verticalPattern.append("C\n");
                verticalPattern.append(ColoredText.ANSI_GREEN);
                verticalPattern.append(emptySpace);
                verticalPattern.append("  C\n");
                verticalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "WOLF":
                verticalPattern.append(ColoredText.ANSI_RED);
                verticalPattern.append("  C\n");
                verticalPattern.append(ColoredText.ANSI_BLUE);
                verticalPattern.append(emptySpace);
                verticalPattern.append("C\n");
                verticalPattern.append(emptySpace);
                verticalPattern.append("C\n");
                verticalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "BUTTERFLY":
                verticalPattern.append(ColoredText.ANSI_BLUE);
                verticalPattern.append("C\n");
                verticalPattern.append(ColoredText.ANSI_PURPLE);
                verticalPattern.append(emptySpace);
                verticalPattern.append("  C\n");
                verticalPattern.append(emptySpace);
                verticalPattern.append("  C\n");
                verticalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "LEAF":
                verticalPattern.append(ColoredText.ANSI_GREEN);
                verticalPattern.append("  C\n");
                verticalPattern.append(emptySpace);
                verticalPattern.append("  C\n");
                verticalPattern.append(ColoredText.ANSI_PURPLE);
                verticalPattern.append(emptySpace);
                verticalPattern.append("C\n");
                verticalPattern.append(ColoredText.ANSI_RESET);
                break;
            default:
                throw new IllegalArgumentException(specificType+" is not a valid objective type");
        }
        this.objective+=verticalPattern.toString();
    }

    /**
     *
     * @param diagonalPattern is the string that will contain the pattern
     * @param emptySpace is the spacing that is needed to have a centered pattern
     * @param isAscending used to indicate weather the pattern is ascending or not
     */
    private void diagonalStringBuilder(StringBuilder diagonalPattern,String emptySpace, boolean isAscending){
        if(isAscending){
            diagonalPattern.append("    C\n");
            diagonalPattern.append(emptySpace);
            diagonalPattern.append("  C\n");
            diagonalPattern.append(emptySpace);
            diagonalPattern.append("C\n");
        }
        else {
            diagonalPattern.append("C\n");
            diagonalPattern.append(emptySpace);
            diagonalPattern.append("  C\n");
            diagonalPattern.append(emptySpace);
            diagonalPattern.append("    C\n");
        }
    }

    /**
     * builds string showing the pattern of cards that earns points
     * @param specificType is the symbol that is used to determine which pattern earns points
     */
    private void setDiagonalPatternObjective(String specificType){
        StringBuilder diagonalPattern=new StringBuilder();
        String emptySpace=createEmptyString(objective.length()/2);
        diagonalPattern.append(emptySpace);
        switch (specificType){
            case "MUSHROOM":
                diagonalPattern.append(ColoredText.ANSI_RED);
                diagonalStringBuilder(diagonalPattern,emptySpace,true);
                diagonalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "WOLF":
                diagonalPattern.append(ColoredText.ANSI_BLUE);
                diagonalStringBuilder(diagonalPattern,emptySpace,true);
                diagonalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "BUTTERFLY":
                diagonalPattern.append(ColoredText.ANSI_PURPLE);
                diagonalStringBuilder(diagonalPattern,emptySpace,false);
                diagonalPattern.append(ColoredText.ANSI_RESET);
                break;
            case "LEAF":
                diagonalPattern.append(ColoredText.ANSI_GREEN);
                diagonalStringBuilder(diagonalPattern,emptySpace,false);
                diagonalPattern.append(ColoredText.ANSI_RESET);
                break;
            default:
                throw new IllegalArgumentException(specificType+" is not a valid objective type");
        }
        this.objective+=diagonalPattern.toString();
    }

    /**
     *
     * @param length of string
     * @return empty string that is as long as indicated in params
     */
    public static String createEmptyString(int length) {
        return " ".repeat(Math.max(0, length));
    }

    public void printObjective(){
        System.out.println(this.objective);
    }

}
