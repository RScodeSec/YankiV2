package com.bootcamp.yankiaccount.customer.repository;

import com.bootcamp.yankiaccount.customer.entity.Account;
import com.bootcamp.yankiaccount.transaction.dto.AccountDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByPhoneNumber(String phoneNumber);
    Mono<AccountDto> findByPhoneNumber(String phoneNumber, String lol);
    Mono<Account> findByIdYankiAccount(String idAccount);
}
