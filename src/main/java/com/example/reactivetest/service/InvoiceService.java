package com.example.reactivetest.service;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.entity.InvoiceEntity;
import com.example.reactivetest.entity.converter.InvoiceEntityMapper;
import com.example.reactivetest.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Mono<InvoiceEntity> create(InvoiceDTO invoiceDTO) {

    return invoiceRepository.save(InvoiceEntityMapper.from(invoiceDTO));

    }

    public Flux<InvoiceEntity> findAll() {
        return invoiceRepository.findAll();
    }
}
