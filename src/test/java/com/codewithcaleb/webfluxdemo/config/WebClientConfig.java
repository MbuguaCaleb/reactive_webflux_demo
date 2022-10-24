package com.codewithcaleb.webfluxdemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    //adding webclient inMy Spring Context
    @Bean
    public WebClient webClient(){

        //It is good to set the Token in your main request
        //We can as well set the token Globally in the WebClient Level

        //There are times we want to generate the token dynamically before making the request.(Session Token)
        //Situations where the token will not be a constant
        return WebClient
                .builder()
                .baseUrl("http://localhost:8086")
               // .defaultHeaders(h->h.setBasicAuth("username","password"))
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        System.out.println("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request).headers(h->h.setBearerAuth("some-lengthy-token")).build();

        //At this stage i can check whether the existing token is valid
        //Otherwise i generate a new One
        return ex.exchange(clientRequest);
    }
}
