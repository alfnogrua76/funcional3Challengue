package co.com.bancolombia.consumer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatusAccountDto {

    private String status;

}