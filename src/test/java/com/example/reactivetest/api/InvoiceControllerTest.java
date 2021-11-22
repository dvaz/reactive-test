package com.example.reactivetest.api;

import com.example.reactivetest.BaseUtilTests;
import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.api.request.InvoiceItemDTO;
import com.example.reactivetest.api.response.InvoiceItemResponse;
import com.example.reactivetest.api.response.InvoiceResponse;
import com.example.reactivetest.entity.InvoiceEntity;
import com.example.reactivetest.repository.InvoiceRepository;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;


@SpringBootTest
@AutoConfigureWebTestClient(timeout = "10000")
@ActiveProfiles("integration-test")
@AutoConfigureWireMock(port = 0)
public class InvoiceControllerTest extends BaseUtilTests {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    InvoiceRepository invoiceRepository;

    @BeforeEach
    public void before() {
        invoiceRepository.deleteAll().block();
    }

    @Test
    public void getAllOk() {
        String fileName = "__files/invoicesEntity";
        List<InvoiceEntity> invoiceEntities = mapperFileJsonToList(fileName, InvoiceEntity.class);
        invoiceRepository.saveAll(invoiceEntities).collectList().block();

        webTestClient
                .get()
                .uri("/invoices")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(InvoiceResponse.class)
                .hasSize(invoiceEntities.size())
                .equals(invoiceEntities)
        ;
    }

    @Test
    public void getAllEmptyOk() {
        webTestClient
                .get()
                .uri("/invoices")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(InvoiceResponse.class)
                .hasSize(0);
    }

    @Test
    public void getCalApi() {
        String expectedResponse = "{ \"status\": \"ACTIVE\", \"description\":\"Test call API MOCK\", \"amount\":30.5}";
        stubFor(get("/product")
                .willReturn(ok()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("externalApi.json")));
        webTestClient
                .get()
                .uri("/invoices/call-api-external")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    Assertions.assertEquals(expectedResponse.toString(), response.getResponseBody());
                })
        ;
    }

    @Test
    public void getCalApiError() {
        String expectedResponse = "{\"status\":\"FAIL\", \"messagem\":\"error call endpoint external\"}";
        stubFor(get("/product")
                .willReturn(serverError()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                ));
        webTestClient
                .get()
                .uri("/invoices/call-api-external")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    Assertions.assertEquals(expectedResponse, response.getResponseBody());
                })
        ;
    }

    @Test
    public void createEmptyOk() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        webTestClient
                .post()
                .uri("/invoices")
                .bodyValue(invoiceDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(InvoiceResponse.class)
                .consumeWith(result -> {
                    InvoiceResponse responseBody = result.getResponseBody();
                    Assertions.assertNotNull(responseBody.getId());
                    Assertions.assertNotNull(responseBody.getCreateAt());
                    Assertions.assertNotNull(responseBody.getTotalItemsAmount());
                    Assertions.assertNotNull(responseBody.getItems());
                    Assertions.assertNull(responseBody.getExternalId());
                    Assertions.assertNotNull(responseBody.getTotalAmount());
                    Assertions.assertNull(responseBody.getTotalShippingAmount());
                });
    }

    @Test
    public void createOk() {
        String fileName = "__files/invoiceDTO";
        InvoiceDTO invoiceDTO = (InvoiceDTO) mapperFileJsonToObject(fileName, InvoiceDTO.class);
        InvoiceItemDTO invoiceItemDTO = invoiceDTO.getItems().get(0);
        webTestClient
                .post()
                .uri("/invoices")
                .bodyValue(invoiceDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(InvoiceResponse.class)
                .consumeWith(result -> {
                    InvoiceResponse responseBody = result.getResponseBody();
                    Assertions.assertNotNull(responseBody.getId());
                    Assertions.assertEquals(invoiceDTO.getExternalId(), responseBody.getExternalId());
                    Assertions.assertEquals(invoiceDTO.getTotalShippingAmount(), responseBody.getTotalShippingAmount());
                    Assertions.assertEquals(invoiceDTO.getItems().size(), responseBody.getItems().size());
                    InvoiceItemResponse invoiceItemResponse = responseBody.getItems().get(0);
                    Assertions.assertEquals(invoiceItemDTO.getItemAmount(), invoiceItemResponse.getItemAmount());
                    Assertions.assertEquals(invoiceItemDTO.getQuantity(), invoiceItemResponse.getQuantity());
                    Assertions.assertEquals(invoiceItemDTO.getProductDescription(), invoiceItemResponse.getProductDescription());
                    Assertions.assertNotNull(responseBody.getTotalAmount());
                    Assertions.assertEquals(new BigDecimal(68.20).setScale(2, RoundingMode.HALF_UP), responseBody.getTotalAmount());
                    Assertions.assertNotNull(responseBody.getCreateAt());
                });
    }
}
