package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RequestAccountDto;
import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.usecase.account.AccountUseCase;
import co.com.bancolombia.usecase.getstatus.GetStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.*;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final AccountUseCase useCase;
    private  final GetStatusUseCase getStatususeCase2;



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

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {

        String id = serverRequest.queryParam("id").orElse("12c6d5b0-1007-46e6-8e6b-1bc065c95e5b");
        return getStatususeCase2.getStatus(id)
                .flatMap(status -> ServerResponse.ok().bodyValue(status));
        //return ServerResponse.ok().bodyValue(id);
    }
}
