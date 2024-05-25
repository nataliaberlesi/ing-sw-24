package it.polimi.ingsw.Server.Controller.DTO;

public record ParamsDTO(InParamsDTO clientOutParams, OutParamsDTO serverOutParams) {
    public ParamsDTO(InParamsDTO inParamsDTO) {
        this(inParamsDTO, null);
    }
    public ParamsDTO(OutParamsDTO outParamsDTO) {
        this(null, outParamsDTO);
    }
}
