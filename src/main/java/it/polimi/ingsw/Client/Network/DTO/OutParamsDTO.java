package it.polimi.ingsw.Client.Network.DTO;

import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CoordinatesDTO;
import it.polimi.ingsw.Server.Model.Coordinates;

public record OutParamsDTO(String username,
                          Integer numberOfPlayers,
                          Boolean isFacingUp,
                          String color,
                          Integer index,
                          CoordinatesDTO coordinates,
                          String drawableSection,
                          String cause) {
    /**
     * Constructor for CREATE params
     * @param username
     * @param numberOfPlayers
     */
    public OutParamsDTO(String username, Integer numberOfPlayers) {
        this(username, numberOfPlayers, null, null, null, null, null, null);
    }

    /**
     * Constructor for JOIN params
     * @param username
     */
    public OutParamsDTO(String username) {
        this(username, null,null,null,null,null,null,null);
    }

    /**
     * Constructor for FIRSTROUND params
     * @param username
     * @param isFaceUp
     * @param color
     */
    public OutParamsDTO(String username, Boolean isFaceUp, String color) {
        this(username, null, isFaceUp, color, null, null, null, null);
    }

    /**
     * Constructor for SECONDROUND params
     * @param username
     * @param index
     */
    public OutParamsDTO(Integer index, String username) {
        this(username,null,null,null,index,null,null,null);
    }

    /**
     * Constructor for ACTION_PLACECARD params
     * @param username
     * @param isFaceUp
     * @param index
     * @param coordinatesDTO
     */
    public OutParamsDTO(String username, Boolean isFaceUp,Integer index, CoordinatesDTO coordinatesDTO) {
        this(username,null,isFaceUp,null,index,coordinatesDTO,null,null);
    }
    /**
     * Constructor for ACTION_DRAWCARD params
     * @param username
     * @param index
     */
    public OutParamsDTO(String username, Integer index, String drawableSection) {
        this(username,null,null,null,index,null,drawableSection,null);
    }
    /**
     * Constructor for ABORT params
     */
    public OutParamsDTO(String cause, Boolean isABORT) {
        this(null,null,null,null,null,null,null,cause);
    }
}
