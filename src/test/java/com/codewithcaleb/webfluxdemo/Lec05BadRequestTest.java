package com.codewithcaleb.webfluxdemo;


import com.codewithcaleb.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest(){

        //do onError is called when do on Next does not get executed which means i have experienced an error
        Mono<Response> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{input}/throw",5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err-> System.out.println("error printed here"))
                .doOnError(err-> System.out.println(err.getMessage()));

        //Verifying BadRequest
        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class);



    }


}
