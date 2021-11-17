package com.example.reactivetest.service;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.entity.InvoiceEntity;
import com.example.reactivetest.entity.InvoiceItem;
import com.example.reactivetest.entity.converter.InvoiceEntityMapper;
import com.example.reactivetest.exception.InvoiceNotFoundException;
import com.example.reactivetest.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Mono<InvoiceEntity> create(InvoiceDTO invoiceDTO) {
        return Mono.justOrEmpty(InvoiceEntityMapper.from(invoiceDTO))
                .flatMap(this::calculateTotalInvoice)
                .flatMap(invoiceRepository::save);
    }

    public Flux<InvoiceEntity> findAll() {
        return invoiceRepository.findAll();
    }

    public Flux<InvoiceEntity> findByExternalId(String externalId) {
        return invoiceRepository.findAllByExternalId(externalId);
    }

    public Mono<InvoiceEntity> findById(String id) {
        return invoiceRepository.findById(id).switchIfEmpty(Mono.error(InvoiceNotFoundException::new));
    }

    private Mono<InvoiceEntity> calculateTotalInvoice(InvoiceEntity invoiceEntity) {
        return Mono.just(invoiceEntity)
                .map(entity -> {
                    entity.setTotalItemsAmount(calculateTotalItems(entity.getItems()));
                    entity.setTotalAmount(entity.getTotalItemsAmount().add(entity.getTotalShippingAmount()));
                    return entity;
                });

    }

    private BigDecimal calculateTotalItems(List<InvoiceItem> items) {
        BigDecimal totalItems = items.stream()
                .map(item -> item.getItemAmount().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalItems;
    }


}
