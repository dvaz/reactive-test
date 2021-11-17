package com.example.reactivetest.repository;

import com.example.reactivetest.entity.InvoiceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface InvoiceRepository extends ReactiveMongoRepository<InvoiceEntity, String> {

    Flux<InvoiceEntity> findAllByExternalId(String externalId);


}
