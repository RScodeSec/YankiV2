package com.bootcamp.yankiaccount.transaction.service.cache;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import com.bootcamp.yankiaccount.transaction.repository.SendCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class SendServiceNoCacheImpl implements SendServiceCache {

    @Autowired
    private SendCacheRepository sendRepository;

    @Override
    public Mono<Send> findByOperationNumber(String opNumber) {
        return sendRepository.findByOperationNumber(opNumber);
    }
}
