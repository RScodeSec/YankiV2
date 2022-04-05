package com.bootcamp.yankiaccount.transaction.config.producer;

import com.bootcamp.yankiaccount.transaction.dto.BootCoinBuyConfirm;
import com.bootcamp.yankiaccount.transaction.dto.SendKafka;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServer;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, SendKafka> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, SendKafka> kafkaTemplate(
            ProducerFactory<String, SendKafka> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    //Producer to BootCoin
    @Bean
    public ProducerFactory<String, BootCoinBuyConfirm> producerFactoryBootCoin() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, BootCoinBuyConfirm> kafkaTemplateBootCoin(
            ProducerFactory<String, BootCoinBuyConfirm> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

}
