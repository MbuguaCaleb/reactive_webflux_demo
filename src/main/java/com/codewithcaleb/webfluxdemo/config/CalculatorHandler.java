package com.codewithcaleb.webfluxdemo.config;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class CalculatorHandler {


    //creating multiple handlers(Utils)
    //calculator/{a}/{b}
    public Mono<ServerResponse> additionHandler(ServerRequest serverRequest){
      return process(serverRequest, (a,b)->ServerResponse.ok().bodyValue(a+b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest serverRequest){
        return process(serverRequest, (a,b)->ServerResponse.ok().bodyValue(a-b));

    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest serverRequest){
        return process(serverRequest, (a,b)->ServerResponse.ok().bodyValue(a*b));
    }


    public Mono<ServerResponse> divisionHandler(ServerRequest serverRequest){
        return process(serverRequest, (a,b)->{
            return b!= 0 ? ServerResponse.ok().bodyValue(a/b) :
                    ServerResponse.badRequest().bodyValue("b cannot be 0");
        });

    }


    private Mono<ServerResponse> process(ServerRequest serverRequest, BiFunction<Integer,Integer,Mono<ServerResponse>> opLogic){

        int a = getValue(serverRequest,"a");
        int b = getValue(serverRequest,"b");
        return opLogic.apply(a,b);

    }

    private int getValue(ServerRequest request,String key){
        return Integer.parseInt(request.pathVariable(key));
    }
}
