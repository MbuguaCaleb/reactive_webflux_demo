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

        //Global Logic should be kept at webClient Level
        //It is good to set the Token in your main request
        //We can as well set the token Globally in the WebClient Level

//        return WebClient
//                .builder()
//                .baseUrl("http://localhost:8086")
//                .defaultHeaders(h->h.setBasicAuth("username","password"))
//                .build();

        //Scenario Two
        //Session Token
        //There are times we want to generate the token dynamically before making the request.(Session Token)
        //Situations where the token will not be a constant

        return WebClient
                .builder()
                .baseUrl("http://localhost:8086")
               // .defaultHeaders(h->h.setBasicAuth("username","password"))
                .filter(this::sessionToken)
                .build();


        //There are situations where the same application may use two different token authentication
        //mechanisms for different types of requests

        //For instance making Payment and Order are using different mechanisms

        //Remember that such configurations must be set at the Global level

        //However how will my application distinguish/differentiate

        //We need to pass some information to the configuration class so that it decides what needs to be run at runtime

        //This is where attributes help

    }

//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
//        System.out.println("generating session token");
//        ClientRequest clientRequest = ClientRequest.from(request).headers(h->h.setBearerAuth("some-lengthy-token")).build();
//
//        //At this stage i can check whether the existing token is valid
//        //Otherwise i generate a new One
//        return ex.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
      //we assume that the service class sets the attribute
        // we are going to get the attribute in the configuration class
        //The attribute key name is auth
        //The Value will be basic/auth

        //i.e auth---->basic or oauth

        //N/B
        //attribute is an Optional Type
        ClientRequest clientRequest = request.attribute("auth")
                .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);
        return ex.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest
                .from(request)
                .headers(h->h.setBasicAuth("username","password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest request){
        return ClientRequest
                .from(request)
                .headers(h->h.setBearerAuth("some-token "))
                .build();
    }
}
