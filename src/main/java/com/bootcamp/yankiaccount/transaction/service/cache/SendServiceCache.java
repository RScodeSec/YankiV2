package com.bootcamp.yankiaccount.transaction.service.cache;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

public interface SendServiceCache {
    Mono<Send> findByOperationNumber(String opNumber);
}
