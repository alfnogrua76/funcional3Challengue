package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.BusinessErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessException extends RuntimeException{

    private final BusinessErrorMessage businesserrorMessage;

    public BusinessException(BusinessErrorMessage businesserrorMessage) {
        super(businesserrorMessage.getMessage());
        this.businesserrorMessage = businesserrorMessage;
    }
}
