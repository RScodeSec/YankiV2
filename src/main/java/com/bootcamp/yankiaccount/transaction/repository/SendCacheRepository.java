package com.bootcamp.yankiaccount.transaction.repository;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SendCacheRepository extends ReactiveMongoRepository<Send, String> {
    Mono<Send> findByOperationNumber(String nomber);
}
