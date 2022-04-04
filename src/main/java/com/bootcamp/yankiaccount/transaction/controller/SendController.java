package com.bootcamp.yankiaccount.transaction.controller;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import com.bootcamp.yankiaccount.transaction.service.SendService;
import com.bootcamp.yankiaccount.transaction.service.cache.SendServiceCache;
import com.bootcamp.yankiaccount.transaction.service.cache.SendServiceNoCacheImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class SendController {


    @Autowired
    private SendService sendService;

    @Autowired
    private SendServiceCache sendServiceCache;

    @PostMapping("/send")
    public Mono<Send> implementTransaction (@RequestBody Send send) {
        return  sendService.sendTransaction(send);
    }

    @GetMapping("/status/{opNumber}")
    public Mono<Send> verifyTransactionStatus(@PathVariable("opNumber") String opNumber) {
        return this.sendServiceCache.findByOperationNumber(opNumber);
    }
}
