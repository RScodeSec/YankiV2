package com.bootcamp.yankiaccount.transaction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BootCoinBuyConfirm {
    private String codeRequestBuy;
    private Double amount;
    private String accountNumber;
    private String userDocBuy;

}
