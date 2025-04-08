package co.com.bancolombia.consumer;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.statusaccount.StatusAccount;
import co.com.bancolombia.model.statusaccount.gateways.StatusAccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ACCOUNT_FIND_ERROR;

@Service
@RequiredArgsConstructor
public class RestConsumer  implements StatusAccountService {
    private final WebClient client;

    @Override
    public Mono<StatusAccount> getStatus(String id) {
        return client
                .get()
                .uri("/v3/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new BusinessException(ACCOUNT_FIND_ERROR)))
                .bodyToMono(StatusAccountDto.class)
                .map(statusAccountDto -> StatusAccount.builder()
                        .status(statusAccountDto.getStatus())
                        .build());
    }


    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.

    //@CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
    /*
    public Mono<StatusAccountDto> testGet() {
        return client
                .get()
                .retrieve()
                .bodyToMono(StatusAccountDto.class);
    }
*/


// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

/*
    @CircuitBreaker(name = "testPost")
    public Mono<ObjectResponse> testPost() {
        ObjectRequest request = ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval2")
            .build();
        return client
                .post()
                .body(Mono.just(request), ObjectRequest.class)
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }

 */
}
