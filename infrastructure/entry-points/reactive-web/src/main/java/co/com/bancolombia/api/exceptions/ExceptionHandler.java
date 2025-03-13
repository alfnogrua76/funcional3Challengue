package co.com.bancolombia.api.exceptions;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {


    public ExceptionHandler(ErrorAttributes errorAttributes, WebProperties resources,
                            ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());

    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunction.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request){
        return Mono.error(getError(request))
                .onErrorResume(BusinessException.class, error -> {
                    log.warn("BussinessException", error);
                    return ServerResponse.badRequest().bodyValue(Map.of("code", error.getErrorMessage().getCode()));
                })
                .onErrorResume(TechnicalException.class, error -> {
                    log.warn("TechnicalException", error);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(Map.of("code", error.getMessage()));
                })
                .onErrorResume(ResponseStatusException.class, error -> 
                        ServerResponse.status(error.getStatusCode()).build()
                )
                .onErrorResume( error -> {
                    log.warn("Internal Server Error", error);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                })
                .cast(ServerResponse.class);
    }
}
