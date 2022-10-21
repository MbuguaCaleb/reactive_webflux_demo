package com.codewithcaleb.webfluxdemo.config;


import com.codewithcaleb.webfluxdemo.dto.InputFailedValidationResponse;
import com.codewithcaleb.webfluxdemo.exceptions.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final RequestHandler requestHandler;

    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    //This is Like a Way of Grouping MyRoutes
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router",this::serverResponseRouterFunction)
                .build();
    }

    //server response is a class that Must Be returned By My Router Functions
    private RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                .GET("square/{input}",requestHandler::squareHandler)
                .GET("table/{input}",requestHandler::tableHandler)
                .GET("table/{input}/stream",requestHandler::tableStreamHandler)
                .POST("multiply",requestHandler::multiplyHandler)
                .GET("square/{input}/validation",requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class,exceptionHandler())
                .build();
    }



    //Bi functions are what we use to handle exceptions in Functional Programming
    //If it was a controller the thrown exception was being caught by the controller advice
    //I must create an exception handler now when using my Router Pattern
    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return ((throwable, serverRequest) -> {
            InputValidationException ex = (InputValidationException) throwable;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        });
    }
}
