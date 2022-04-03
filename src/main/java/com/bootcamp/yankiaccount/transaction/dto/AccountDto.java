package com.bootcamp.yankiaccount.transaction.dto;



import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String idYankiAccount;
    private String documentNumber;
    private String documentType;
    private String phoneNumber;
    private String imei;
    private String email;
    private String debitCard;
    private String associatedAccount;
    private Double balance;
    private LocalDate date;
}
