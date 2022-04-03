package com.bootcamp.yankiaccount.transaction.dto;

import lombok.*;

@Getter
@Setter
public class SendKafka {
    private String associatedAccount;
    private Double amount;
    private String operationNumber;
    private String sendTo;
}
