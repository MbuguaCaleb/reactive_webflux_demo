package com.codewithcaleb.webfluxdemo;

import com.codewithcaleb.webfluxdemo.dto.MultiplyRequestDto;
import com.codewithcaleb.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest{
    @Autowired
    private WebClient webClient;


    //difference between Body and BodyValue is that for BodyValue it is used for normal Objects
    //body on the other hand is used for publisher types

    @Test
    public void headersTest(){
        //Making a Post Request
        //The token is as well part of the header
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .headers(h->h.set("someKey","someValue"))
               // .headers(h->h.setBasicAuth("username","password"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        //step verifier takes in the Response you have created and verifies it if its correct
        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    //Builder Object for my Post Request
    private MultiplyRequestDto buildRequestDto(int a , int b){
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }

}
