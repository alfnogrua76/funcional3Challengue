package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechnicalErrorMessage {
    CHANNEL_FIND_ALL_ERROR("SFT0001","Error consultando los canales"),
    CHANNEL_FIND_ALL_ERROR2("SFT0002","Error2 consultando los canales");

    private final String code;
    private final String message;
}
