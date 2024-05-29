package it.polimi.ingsw.Client.Network.DTO;

/**
 * Represents the parameters of a generic message clientside
 * @param serverOutParams
 * @param clientOutParams
 */
public record ParamsDTO(InParamsDTO serverOutParams, OutParamsDTO clientOutParams) {
    public ParamsDTO(InParamsDTO inParamsDTO) {
        this(inParamsDTO, null);
    }
    public ParamsDTO(OutParamsDTO outParamsDTO) {
        this(null,outParamsDTO);
    }
}
