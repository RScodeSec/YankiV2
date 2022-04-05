package com.bootcamp.yankiaccount.transaction.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Rpaymen-bootcoin")
public class BootcoinPay {
    @Id
    private String id;
    private String codeRequestBuy;
    private Double amount;
    private Double totalPrice;
    private String accountNumber;
    private String userDocBuy;

    @Builder.Default
    private Boolean transferSuccessful = true;
}
