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
        //como se pueden ir concatenando pasos de funciones lambdas para que queden dentro del
        //mismo scope

        var n1 = Mono.just(1);
        var n2 = Mono.just(2);
        var n3 = Mono.just(3);
        var n4 = Mono.just(4);
        n1
        .flatMap(m1 -> n2
                .flatMap(m2 -> n2
                        .flatMap(m3 -> n4
                                .map(m4 -> m1 + m2 + m3 + m4  ))))
                .subscribe(value -> {
                    System.out.println("***************");
                    System.out.println("***************");
                    System.out.println(value);
                    System.out.println("***************");
                });

        return getStatus(name)
                //Tambien se puede hacer pos Asociatividad
                .flatMap(status -> Mono.zip(//par hacer las 2 primeras validaciones en paralelo
                        legalValidation(name),
                        disponibilityValidation()
                )
                .map(sta -> generateAccount(name, status)))
                .flatMap(this::finalValidation)
                .flatMap(this::saveAccount);

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
