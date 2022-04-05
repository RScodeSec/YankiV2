package com.bootcamp.yankiaccount.transaction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BootCoinRequest {
    private String codeRequestBuy;
    private Double amount;
    private String accountNumber;
    private String userDocBuy;

}
