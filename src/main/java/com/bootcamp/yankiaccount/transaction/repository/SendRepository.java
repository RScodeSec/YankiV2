package com.bootcamp.yankiaccount.transaction.repository;

import com.bootcamp.yankiaccount.transaction.entity.Send;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SendRepository  extends ReactiveMongoRepository<Send, String> {
Mono<Send> findByOperationNumber(String nomber);
}
