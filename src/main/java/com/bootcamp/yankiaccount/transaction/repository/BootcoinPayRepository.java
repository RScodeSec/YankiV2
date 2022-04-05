package com.bootcamp.yankiaccount.transaction.repository;

import com.bootcamp.yankiaccount.transaction.entity.BootcoinPay;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BootcoinPayRepository extends ReactiveMongoRepository<BootcoinPay, String> {
}
