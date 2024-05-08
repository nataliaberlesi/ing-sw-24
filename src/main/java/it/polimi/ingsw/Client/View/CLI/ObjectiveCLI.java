package it.polimi.ingsw.Client.View.CLI;

public class ObjectiveCLI {

    /**
     * objective as seen in CLI
     */
    private String objective;

    /**
     *
     * @param objective generic objective type
     * @param specificType symbol that specifies the objective
     */
    public ObjectiveCLI(String objective, String specificType) throws IllegalArgumentException{
        switch (objective){
            case "DiagonalPatternObjective":
                this.objective="Gain 2 points for each diagonal pattern like this:\n";
                setDiagonalPatternObjective(specificType);
                break;
            case "VerticalPatternObjective":
                this.objective="Gain 3 points for each vertical pattern like this:\n";
                setVerticalPatternObjective(specificType);
                break;
            case "SymbolObjective":
                setSymbolObjective(specificType);
                break;
            case "MixSymbolObjective":
                this.objective="Gain 3 points per set of 3 different symbols (FEATHER, SCROLL, INK)\n";
                break;
            default:
                throw new IllegalArgumentException(objective+" is not a valid objective");
        }
    }

    private void setSymbolObjective(String specificType) {
        this.objective="Gain 2 points for every ";
        if(specificType.equals("INK")||specificType.equals("SCROLL")||specificType.equals("FEATHER")){
            this.objective+="2 "+specificType+" symbols\n";
        }
        else if(specificType.equals("MUSHROOM")||specificType.equals("BUTTERFLY")||specificType.equals("LEAF")||specificType.equals("WOLF")){
            this.objective+="3 "+specificType+" symbols\n";
        }
        else{
            throw new IllegalArgumentException(specificType+" is not a valid objective type");
        }


    }


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


    private void diagonalStringBuilder(StringBuilder diagonalPattern,String emptySpace, boolean isIncreasing){
        if(isIncreasing){
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

    public static String createEmptyString(int length) {
        return " ".repeat(Math.max(0, length));
    }

    public void printObjective(){
        System.out.println(this.objective);
    }
}
