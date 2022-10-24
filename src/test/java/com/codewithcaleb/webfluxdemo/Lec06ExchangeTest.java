package com.codewithcaleb.webfluxdemo;

import com.codewithcaleb.webfluxdemo.dto.InputFailedValidationResponse;
import com.codewithcaleb.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest  extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest(){
        //difference Between retrieve and exchange in webClient
        //exchange = retrieve + additional information like http status code
        //The biggest advantage of exchange is that it gives us the statusCode
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{input}/throw",5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err-> System.out.println(err.getMessage()));

        //Verifying BadRequest
        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();


    }

    private Mono<Object> exchange(ClientResponse cr){
        if(cr.rawStatusCode() ==400 ){
            return cr.bodyToMono(InputFailedValidationResponse.class);
        }else{
            return cr.bodyToMono(Response.class);
        }
    }
}