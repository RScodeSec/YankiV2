package com.bootcamp.yankiaccount.transaction.service;

import com.bootcamp.yankiaccount.customer.entity.Account;
import com.bootcamp.yankiaccount.transaction.dto.ProcessConfirmation;
import com.bootcamp.yankiaccount.transaction.entity.Send;
import reactor.core.publisher.Mono;

public interface SendService {

    Mono<Send> sendTransaction (Send send);
    Mono<Account> processPaymentConfirmation(ProcessConfirmation processConfirmation);
}
