package com.bootcamp.yankiaccount.customer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "account")
public class Account {
    @Id
    private String idYankiAccount;

    private String documentNumber;
    private String documentType;
    private String phoneNumber;
    private String imei;
    private String email;
    private String debitCard;
    private String associatedAccount;

    @Builder.Default
    private Double balance = 0.0;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}
