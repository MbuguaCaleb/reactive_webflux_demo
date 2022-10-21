package com.codewithcaleb.webfluxdemo.config;


import com.codewithcaleb.webfluxdemo.dto.Response;
import com.codewithcaleb.webfluxdemo.service.ReactiveMathService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class RequestHandler {

    private final ReactiveMathService reactiveMathService;

    public RequestHandler(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }


    //My handlers take in the Server Request which have everything i need
    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = reactiveMathService.mutliplicationTable(input);
        return  ServerResponse.ok().body(responseFlux,Response.class);
    }


}
