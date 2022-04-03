package com.bootcamp.yankiaccount.transaction.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "send-money")
public class Send {

    @Id
    private String sendId;

    private String destinationPhone;
    private String originPhone;
    private Double amount;

    @Builder.Default
    private String operationNumber = java.util.UUID.randomUUID().toString();

    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Builder.Default
    private Boolean paymentWitDebit = false;

    @Builder.Default
    private Boolean transferSuccessful = true;
}
