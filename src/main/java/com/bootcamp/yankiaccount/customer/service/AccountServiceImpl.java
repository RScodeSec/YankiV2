package com.bootcamp.yankiaccount.customer.service;

import com.bootcamp.yankiaccount.customer.entity.Account;
import com.bootcamp.yankiaccount.customer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Mono<Account> saveAccount (Account account){
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateAccount (Account account){
        /*return accountRepository.findByPhoneNumber(account.getPhoneNumber()).flatMap(user -> user.getPhoneNumber().isEmpty()?Mono.empty():Mono.just(account));
        return accountRepository.findByPhoneNumber(account.getPhoneNumber()).filter(user -> !user.getIdYankiAccount().isEmpty() || user.getIdYankiAccount() !=null)
                .flatMap(value ->accountRepository.save(account));*/
        return accountRepository.findByIdYankiAccount(account.getIdYankiAccount()).switchIfEmpty(Mono.empty()).flatMap(user -> accountRepository.save(account));

    }

    @Override
    public Mono<Account> findAccount (String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Flux<Account> saveAll(List<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }

}
