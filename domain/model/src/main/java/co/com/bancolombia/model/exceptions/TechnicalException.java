package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.TechnicalErrorMessage;

public class TechnicalException extends RuntimeException{
    private final TechnicalErrorMessage errorMessage;

    public TechnicalException(TechnicalErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
