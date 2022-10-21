package com.codewithcaleb.webfluxdemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private final RequestHandler requestHandler;

    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    //server response is a class that Must Be returned By My Router Functions
    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){

        return RouterFunctions.route()
                .GET("router/square/{input}",requestHandler::squareHandler)
                .GET("router/table/{input}",requestHandler::tableHandler)
                .GET("/router/table/{input}/stream",requestHandler::tableStreamHandler)
                .POST("/router/multiply",requestHandler::multiplyHandler)
                .build();

    }
}
