package com.example.reactivetest.api;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.api.response.InvoiceResponse;
import com.example.reactivetest.api.response.converter.InvoiceResponseMapper;
import com.example.reactivetest.service.InvoiceService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Mono<InvoiceResponse> create(@RequestBody InvoiceDTO invoiceCreateRequest){

        return invoiceService.create(invoiceCreateRequest)
                .map(InvoiceResponseMapper::from);
    }
    @GetMapping
    public Flux<InvoiceResponse> getAll(){

        return invoiceService.findAll()
                .map(InvoiceResponseMapper::from);
    }
}
