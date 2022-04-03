package com.bootcamp.yankiaccount.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessConfirmation {
    private String operationNumber;
    private String sendTo;
    private Boolean confirmation;
    private Double amount;
}
