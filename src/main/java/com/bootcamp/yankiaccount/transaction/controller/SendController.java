package com.bootcamp.yankiaccount.transaction.controller;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import com.bootcamp.yankiaccount.transaction.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class SendController {

    @Autowired
    private SendService sendService;

    @PostMapping("/send")
    public Mono<Send> implementTransaction (@RequestBody Send send) {
        return  sendService.sendTransaction(send);
    }
}
