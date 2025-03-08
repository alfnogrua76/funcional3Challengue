package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RequestAccountDto;
import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.usecase.account.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.*;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final AccountUseCase useCase;
//private  final UseCase2 useCase2;



    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        //// la manera vieja de hacerlo pero se debe evitar variabilizar//**////
        //Mono<Account> register= serverRequest.bodyToMono(RequestAccountDto.class)
        return serverRequest.bodyToMono(RequestAccountDto.class)
                .switchIfEmpty(Mono.error(new BusinessException(CHANNEL_TRANSACTION_NOT_FOUND)))
                .flatMap(request -> useCase.register(request.getName(), request.getStatus()))
                .flatMap(account -> ServerResponse.ok().bodyValue(account));//para darle cuerpo al body con la cuenta
        //Este seria el return de la manera antigua de hacerlo ojo se debe evitar
        //return ServerResponse.ok().bodyValue(register);
    }
}
