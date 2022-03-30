package com.bootcamp.yankiaccount.repository;

import com.bootcamp.yankiaccount.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByPhoneNumber(Integer phoneNumber);
}
