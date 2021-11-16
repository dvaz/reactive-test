package com.example.reactivetest.repository;

import com.example.reactivetest.entity.InvoiceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends ReactiveMongoRepository<InvoiceEntity, String> {


}
