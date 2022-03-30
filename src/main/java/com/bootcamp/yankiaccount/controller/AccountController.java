package com.bootcamp.yankiaccount.controller;

import com.bootcamp.yankiaccount.entity.Account;
import com.bootcamp.yankiaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/yanki")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/save")
    public Mono<ResponseEntity<Account>> saveAccount (@RequestBody Account account) {
        System.out.println(account);
        return accountService.saveAccount(account).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/account/update")
    public Mono<ResponseEntity<Account>> updateAccount (@RequestBody Account account) {
        return accountService.updateAccount(account).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/account/{phone}")
    public Mono<ResponseEntity<Account>> findAccount (@PathVariable("phone") String phoneNumber) {
        return accountService.findAccount(phoneNumber).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
