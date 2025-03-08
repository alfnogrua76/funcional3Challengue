package co.com.bancolombia.model.account;
import co.com.bancolombia.model.exceptions.BusinessException;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {
    private final Long id;
    private final String name;
    private String status;

    public static  Account newAccount(long id, String name, String status){
        if(name.equals("error"))
            throw  new BusinessException(ACCOUNT_VALIDTION_ERROR);
        return  new Account(id, name, status);
    }

}
