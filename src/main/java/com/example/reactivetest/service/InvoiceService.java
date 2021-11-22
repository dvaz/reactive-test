package com.example.reactivetest.service;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.entity.InvoiceEntity;
import com.example.reactivetest.entity.InvoiceItem;
import com.example.reactivetest.entity.converter.InvoiceEntityMapper;
import com.example.reactivetest.exception.InvoiceNotFoundException;
import com.example.reactivetest.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final WebClient webClientbeeceptor;
    private final String pathUri;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          WebClient.Builder webClientBuilder,
                          @Value("${app.path-uri-beeceptor.uri:vazio}") String uri) {
        this.pathUri = uri;
        this.webClientbeeceptor = webClientBuilder.baseUrl(pathUri).build();
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
        return Mono.justOrEmpty(invoiceEntity)
                .map(entity -> {
                    entity.setTotalItemsAmount(calculateTotalItems(entity.getItems()));
                    BigDecimal totalInvoice = entity.getTotalItemsAmount().add(Optional.ofNullable(entity.getTotalShippingAmount()).orElse(BigDecimal.ZERO));
                    entity.setTotalAmount(totalInvoice);
                    return entity;
                });

    }

    private BigDecimal calculateTotalItems(List<InvoiceItem> items) {
        BigDecimal totalItems = items.stream()
                .map(item -> item.getItemAmount().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalItems;
    }

    public Mono<String> callApi() {
        return webClientbeeceptor
                .get()
                .uri("/product")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    log.error("Error call API");
                    return Mono.just("{\"status\":\"FAIL\", \"messagem\":\"error call endpoint external\"}");
                });
    }


}
