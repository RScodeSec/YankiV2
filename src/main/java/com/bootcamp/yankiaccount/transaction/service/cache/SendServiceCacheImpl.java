package com.bootcamp.yankiaccount.transaction.service.cache;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class SendServiceCacheImpl extends SendServiceNoCacheImpl{

    private static final String KEY = "transact";

    @Autowired
    private ReactiveHashOperations<String, String, Send> hashOperations;

    @Override
    public Mono<Send> findByOperationNumber(String opNumber) {
        return hashOperations.get(KEY, opNumber)
                .switchIfEmpty(this.findByOperationNumberOfDBandCache(opNumber));
    }

    public Mono<Send> findByOperationNumberOfDBandCache(String opNumber) {
        return super.findByOperationNumber(opNumber)
                .flatMap(dto -> this.hashOperations.put(KEY, opNumber, dto)
                        .thenReturn(dto));

    }
}
