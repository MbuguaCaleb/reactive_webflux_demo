package com.codewithcaleb.webfluxdemo.service;


import com.codewithcaleb.webfluxdemo.dto.MultiplyRequestDto;
import com.codewithcaleb.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){

        //when i am doing no computation on a Mono i can return Mono.just
        //however if i am doing computation i will use mono.fromSupplier()
        return Mono.fromSupplier(()->input * input)
                .map(Response::new);
    }


    public Flux<Response> mutliplicationTable(int input){

        //We should ensure that we do everything inside the pipeline to avoid blocking our code
        List<Response> list = IntStream.rangeClosed(1,10)
                .peek(i->SleepUtil.sleepSeconds(1))
                .peek(i-> System.out.println("math-service-processing :" + i))
                .mapToObj(i-> new Response(i*input))
                .collect(Collectors.toList());

        return Flux.fromIterable(list);

//        return Flux.range(1,10)
//                .delayElements(Duration.ofSeconds(1))
//               //.doOnNext(i->SleepUtil.sleepSeconds(1))
//                .doOnNext(i -> System.out.println("reactive-math-service-processing: " + i))
//                .map(i-> new Response(i*input));

    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}
