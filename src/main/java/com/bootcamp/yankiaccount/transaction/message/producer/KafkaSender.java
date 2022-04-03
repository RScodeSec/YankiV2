package com.bootcamp.yankiaccount.transaction.message.producer;

import com.bootcamp.yankiaccount.transaction.dto.SendKafka;

public interface KafkaSender {
    void sendMessage (String topic, SendKafka sendKafka);
}
