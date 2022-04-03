package com.bootcamp.yankiaccount.transaction.message.consumer;

import com.bootcamp.yankiaccount.transaction.dto.ProcessConfirmation;
import com.bootcamp.yankiaccount.transaction.service.SendService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class KafkaProcessConfirmPay {

    @Autowired
    private SendService sendService;

    @KafkaListener(id = "hello", topics = "payment-confirmation", groupId = "group_Id", containerFactory = "factory")
    public Mono<Void> listenTopic(ProcessConfirmation confirmation) {
        if(confirmation.getConfirmation().equals(true)){
           return sendService.processPaymentConfirmation(confirmation).then();
        }
        else {
            log.info("We were unable to process your payment request");
        }
        return Mono.empty();
    }
}
