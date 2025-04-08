package co.com.bancolombia.usecase.getstatus;

import co.com.bancolombia.model.statusaccount.StatusAccount;
import co.com.bancolombia.model.statusaccount.gateways.StatusAccountService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetStatusUseCase {
    private  final StatusAccountService service;

    public Mono<StatusAccount> getStatus(String id){
        return service.getStatus(id);
        //return Mono.empty();
    }
}
