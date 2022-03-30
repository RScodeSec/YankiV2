package com.bootcamp.yankiaccount.service;

import com.bootcamp.yankiaccount.entity.Account;
import com.bootcamp.yankiaccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Mono<Account> saveAccount (Account account){
        return accountRepository.save(account);
    }

    public Mono<Account> updateAccount (Account account){
        //return accountRepository.findByPhoneNumber(account.getPhoneNumber()).flatMap(user -> user.getPhoneNumber().isEmpty()?Mono.empty():Mono.just(account));
        //return accountRepository.findByPhoneNumber(account.getPhoneNumber()).filter(user -> !user.getIdYankiAccount().isEmpty() || user.getIdYankiAccount() !=null)
                //.flatMap(value ->accountRepository.save(account));
        return accountRepository.findByIdYankiAccount(account.getIdYankiAccount()).switchIfEmpty(Mono.empty()).flatMap(user -> accountRepository.save(account));

    }

    public Mono<Account> findAccount (String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }


}
