package com.bootcamp.yankiaccount.transaction.message.consumer;

import com.bootcamp.yankiaccount.transaction.dto.BootCoinBuyConfirm;
import com.bootcamp.yankiaccount.transaction.dto.BootCoinRequest;
import com.bootcamp.yankiaccount.transaction.dto.ProcessConfirmation;

import com.bootcamp.yankiaccount.transaction.entity.BootcoinPay;
import com.bootcamp.yankiaccount.transaction.message.producer.KafkaSender;
import com.bootcamp.yankiaccount.transaction.service.SendService;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Log4j2
@Component
public class KafkaProcessConfirmPay {

    @Autowired
    private SendService sendService;

    @Autowired
    private KafkaSender kafkaSender;

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

    //listener Bootcoin
    @KafkaListener(id = "bootcoin", topics = "paymentRequest-bootcoin", groupId = "group_Id1", containerFactory = "factoryBootcoin")
    public Mono<BootcoinPay> listenTopicBootcoin(BootCoinRequest bootCoinRequest) {
        System.out.println(bootCoinRequest);
        var result =  sendService.sendTransactionBootCoin(bootCoinRequest).block();
        if(Objects.requireNonNull(result).getTransferSuccessful().equals(true)){
            var confirm = new BootCoinBuyConfirm();
            confirm.setCodeRequestBuy(result.getCodeRequestBuy());
            confirm.setAmount(result.getAmount());
            confirm.setAccountNumber(result.getAccountNumber());
            confirm.setUserDocBuy(result.getUserDocBuy());
            //Send For Confirm to BootCoin
            kafkaSender.sendMessageToBootCoin("paymentConfirm-bootcoin", confirm);
            return Mono.just(result);
        }
        return Mono.just(result);

    }
}
