package it.polimi.ingsw.Client.Network.DTO;

public record ParamsDTO(InParamsDTO serverOutParams, OutParamsDTO clientOutParams) {
    public ParamsDTO(InParamsDTO inParamsDTO) {
        this(inParamsDTO, null);
    }
    public ParamsDTO(OutParamsDTO outParamsDTO) {
        this(null,outParamsDTO);
    }
}
