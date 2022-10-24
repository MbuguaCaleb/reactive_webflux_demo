package com.codewithcaleb.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;

public class Lec07QueryParamsTest extends BaseTest {

    @Autowired
    private WebClient webClient;


    String queryString = "http://localhost:8086/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest(){

        URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 20);

        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        //Expect Next count will be the no of items in MyFlux
        StepVerifier
                .create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();


    }

}
