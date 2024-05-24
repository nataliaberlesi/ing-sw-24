package it.polimi.ingsw.Client.Network.DTO;

import it.polimi.ingsw.Client.Network.DTO.ModelDTO.CoordinatesDTO;

public record OutParamsDTO(String username,
                          Integer numberOfPlayers,
                          Boolean isFacingUp,
                          String color,
                          Integer index,
                          CoordinatesDTO coordinates,
                          String drawableSection,
                          String cause,
                           Boolean persistence) {
    /**
     * Constructor for CREATE params
     */
    public OutParamsDTO(String username, Integer numberOfPlayers) {
        this(username, numberOfPlayers, null, null, null, null, null, null,null);
    }

    /**
     * Constructor for JOIN params
     */
    public OutParamsDTO(String username) {
        this(username, null,null,null,null,null,null,null,null);
    }

    /**
     * Constructor for FIRSTROUND params
     */
    public OutParamsDTO(String username, Boolean isFaceUp, String color) {
        this(username, null, isFaceUp, color, null, null, null, null,null);
    }

    /**
     * Constructor for SECONDROUND params
     */
    public OutParamsDTO(Integer index, String username) {
        this(username,null,null,null,index,null,null,null,null);
    }

    /**
     * Constructor for ACTION_PLACECARD params
     */
    public OutParamsDTO(String username, Boolean isFaceUp,Integer index, CoordinatesDTO coordinatesDTO) {
        this(username,null,isFaceUp,null,index,coordinatesDTO,null,null,null);
    }
    /**
     * Constructor for ACTION_DRAWCARD params
     */
    public OutParamsDTO(String username, Integer index, String drawableSection) {
        this(username,null,null,null,index,null,drawableSection,null,null);
    }
    /**
     * Constructor for ABORT params
     */
    public OutParamsDTO(String username, String cause) {
        this(username,null,null,null,null,null,null,cause,null);
    }

    /**
     * Constructor for PERSISTENCY params
     */
    public OutParamsDTO(Boolean persistence) {
        this(null,null,null,null,null,null,null,null,persistence);
    }
}
