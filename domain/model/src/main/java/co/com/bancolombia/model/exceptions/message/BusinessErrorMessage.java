package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {
    INVALID_REQUEST("SFT0001","Invalid request"),
    ACCOUNT_VALIDTION_ERROR("SFT0002","Account validation error"),
    ACCOUNT_FIND_ERROR("SFT0003","Account find error"),
    CHANNEL_TRANSACTION_NOT_FOUND("SFT0004","Channel transaction not found");

    private final String code;
    private final String message;
}
