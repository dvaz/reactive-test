package com.example.reactivetest.api;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.api.response.InvoiceResponse;
import com.example.reactivetest.api.response.converter.InvoiceResponseMapper;
import com.example.reactivetest.service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Mono<InvoiceResponse> create(@RequestBody InvoiceDTO invoiceCreateRequest) {

        return invoiceService.create(invoiceCreateRequest)
                .map(InvoiceResponseMapper::from);
    }

    @GetMapping
    public Flux<InvoiceResponse> getAll() {

        return invoiceService.findAll()
                .map(InvoiceResponseMapper::from);
    }

    @GetMapping("/{id}")
    public Mono<InvoiceResponse> getById(@PathVariable String id) {

        return invoiceService.findById(id)
                .map(InvoiceResponseMapper::from);
    }

    @GetMapping("/external/{externalId}")
    public Flux<InvoiceResponse> getByExternalId(@PathVariable String externalId) {

        return invoiceService.findByExternalId(externalId)
                .map(InvoiceResponseMapper::from);
    }
}
