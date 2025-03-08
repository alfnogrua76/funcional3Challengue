package co.com.bancolombia.usecase.account;

import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.account.gateways.AccountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

import java.time.Duration;

@RequiredArgsConstructor
public class AccountUseCase {

    private final AccountRepository repository;

    public Mono<Account> register(String name, String statusId){
        return getStatus(name)
                .flatMap(status -> Mono.zip(//par hacer las 2 primeras validaciones en paralelo
                        legalValidation(name),
                        disponibilityValidation()
                ))
                .map(TupleUtils.function((n1,n2)->{
                    return n1.toString();
                }))
                .map(status -> generateAccount(name, status))
                .flatMap(this::finalValidation)
                .flatMap(this::saveAccount);
         /*
        return Mono.zip(//par hacer las 2 primeras validaciones en paralelo
                        legalValidation(name),
                        disponibilityValidation()
                )
                /*      Manera practica con utilidad reactor de usar la tupla
                        .map(TupleUtils.function((n1,n2)->{
                            return n1.toString();
                        }))

                 */
                //        legalValidation(name)
                //        .flatMap(legal -> disponibilityValidation())
                /*       .map(tuple ->{//una forma de trabajar la tupla
                           String t1 = tuple.getT1();
                           Integer t2 = tuple.getT2();
                           return "ALGO";
                       })*/

                //map(status -> generateAccount(name, status)).
//                .flatMap(this::finalValidation)
//                .flatMap(this::saveAccount);
    }

    private Account generateAccount (String name, String status){

        return Account.newAccount(999, name, status);
    }

    private Mono<String> legalValidation(String accountName){
        return Mono.just(accountName).delayElement(Duration.ofSeconds(2));
    }

    private Mono<Integer> disponibilityValidation(){
        return Mono.just(9).delayElement(Duration.ofSeconds(2));
    }

    private Mono<String> getStatus(String accountName){
        return Mono.just("Ok");
    }

    private Mono<Account> finalValidation(Account account){
        return Mono.just(account);
    }

    private Mono<Account> saveAccount(Account account){
        return Mono.just(account);
    }
}
