package com.bootcamp.yankiaccount.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {
    private String idYankiAccount;
    private Integer documentNumber;
    private String documentType;
    private Integer phoneNumber;
    private Integer imei;
    private String email;
    private Integer debitCard;

    @Builder.Default
    private Double balance = 0.0;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}
