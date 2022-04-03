package com.bootcamp.yankiaccount.customer.service;

import com.bootcamp.yankiaccount.customer.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {
    Mono<Account> saveAccount (Account account);
    Mono<Account> updateAccount (Account account);
    Mono<Account> findAccount (String phoneNumber);
    Flux<Account> saveAll (List<Account> account);

}
