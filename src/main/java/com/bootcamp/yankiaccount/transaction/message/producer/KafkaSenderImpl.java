package com.bootcamp.yankiaccount.transaction.message.producer;

import com.bootcamp.yankiaccount.transaction.dto.BootCoinBuyConfirm;
import com.bootcamp.yankiaccount.transaction.dto.SendKafka;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Service
public class KafkaSenderImpl implements KafkaSender {

    @Autowired
    KafkaTemplate<String, SendKafka> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, BootCoinBuyConfirm> kafkaTemplateBootCoin;

    @Override
    public void sendMessage(String topic, SendKafka sendKafka) {
        ListenableFuture<SendResult<String, SendKafka>> future = kafkaTemplate.send(topic, sendKafka);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("fail topic submission" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, SendKafka> result) {
                log.info("successful topic submission");
                System.out.println(result.getProducerRecord().value().getAmount() + " --" +result.getRecordMetadata().topic());
            }
        });
    }

    @Override
    public void sendMessageToBootCoin(String topic, BootCoinBuyConfirm bootCoinBuyConfirm) {
        ListenableFuture<SendResult<String, BootCoinBuyConfirm>> future = kafkaTemplateBootCoin.send(topic, bootCoinBuyConfirm);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("fail topic submission" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, BootCoinBuyConfirm> result) {
                log.info("successful topic submission");
                //System.out.println(result.getProducerRecord().value().getAmount() + " --" +result.getRecordMetadata().topic());
            }
        });

    }
}
