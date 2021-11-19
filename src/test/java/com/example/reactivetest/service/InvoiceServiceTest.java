package com.example.reactivetest.service;

import com.example.reactivetest.BaseUtilTests;
import com.example.reactivetest.entity.InvoiceEntity;
import com.example.reactivetest.exception.InvoiceNotFoundException;
import com.example.reactivetest.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest extends BaseUtilTests {

    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;


    @Test
    public void getEntities() {
        String fileName = "__files/invoicesEntity";
        List<InvoiceEntity> invoiceEntities = mapperFileJsonToList(fileName, InvoiceEntity.class);

        Answer<Flux<InvoiceEntity>> answer = invocation -> Flux.fromIterable(invoiceEntities);
        will(answer).given(invoiceRepository).findAll();

        StepVerifier.create(invoiceService.findAll())
                .expectNextCount(invoiceEntities.size())
                .verifyComplete();
    }

    @Test
    public void getEntityById() {
        String fileName = "__files/invoiceEntity";
        InvoiceEntity invoiceEntity = (InvoiceEntity) mapperFileJsonToObject(fileName, InvoiceEntity.class);

        when(invoiceRepository.findById(anyString())).thenReturn(Mono.just(invoiceEntity));

        StepVerifier.create(invoiceService.findById(invoiceEntity.getId()))
//                .assertNext(invoice -> Assertions.assertEquals(invoiceEntity.getId(), invoice.getId()))
                .expectNext(invoiceEntity)
                .verifyComplete();
    }

    @Test
    public void getEntityByIdO() {
        String fileName = "__files/invoiceEntity";
        InvoiceEntity invoiceEntity = (InvoiceEntity) mapperFileJsonToObject(fileName, InvoiceEntity.class);

        when(invoiceRepository.findById(anyString())).thenReturn(Mono.just(invoiceEntity));

        StepVerifier.create(invoiceService.findById(invoiceEntity.getId()))
                .expectNext(invoiceEntity)
                .verifyComplete();
    }

    @Test
    public void getEntityByIdNotFound() {
        String fileName = "__files/invoiceEntity";
        InvoiceEntity invoiceEntity = (InvoiceEntity) mapperFileJsonToObject(fileName, InvoiceEntity.class);

        when(invoiceRepository.findById(eq(invoiceEntity.getId()))).thenReturn(Mono.empty());

        StepVerifier.create(invoiceService.findById(invoiceEntity.getId()))
                .verifyErrorMatches(throwable -> throwable instanceof InvoiceNotFoundException
                        && "NF não encontrado".equals(throwable.getLocalizedMessage()))
        ;
    }

    // ta pendente criar uma nova funcionalidade no codigo que vai ser integrar com um API externa, para poder exemplificar
    // o uso do stubfor que tem como objetivo mocar a chama http
}
