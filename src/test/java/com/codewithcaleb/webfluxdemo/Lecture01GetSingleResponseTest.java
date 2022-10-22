package com.codewithcaleb.webfluxdemo;

import com.codewithcaleb.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture01GetSingleResponseTest extends BaseTest{
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){
        Response response = this.webClient
                                .get()
                                .uri("reactive-math/square/{input}", 5)
                                .retrieve()
                                .bodyToMono(Response.class)
                                .block();

        System.out.println(response);

    }


    //i Can Test My Endpoints in a Flux/Reactive fashion
    @Test
    public void stepVerifierTest(){

        //The first step is all i need to Send MyRequest and get the Response
        Mono<Response> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);


        StepVerifier.create(responseMono)
                .expectNextMatches(r->r.getOutput() == 25)
                .verifyComplete();

    }

}
