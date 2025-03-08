package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.BusinessErrorMessage;
import co.com.bancolombia.model.exceptions.message.TechnicalErrorMessage;

public class TechnicalException extends RuntimeException{
    private final TechnicalErrorMessage errorMessage;

    public TechnicalException(Throwable cause, TechnicalErrorMessage errorMessage) {
        super(errorMessage.getMessage(), cause);
        this.errorMessage = errorMessage;
    }
}
