package com.codewithcaleb.webfluxdemo.controller;


import com.codewithcaleb.webfluxdemo.dto.Response;
import com.codewithcaleb.webfluxdemo.exceptions.InputValidationException;
import com.codewithcaleb.webfluxdemo.service.ReactiveMathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    private final ReactiveMathService reactiveMathService;


    public ReactiveMathValidationController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input){
        if(input < 10 || input > 20)
            throw new InputValidationException(input);
        return this.reactiveMathService.findSquare(input);
    }

    //i can as well do validation within the pipeline
    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input){
       return Mono.just(input)
               .handle(((integer, sink) -> {
                   if(integer >= 10 && integer <=20)
                       sink.next(integer);
                   else
                       sink.error(new InputValidationException(integer));
               }))
               .cast(Integer.class)
               .flatMap(i->reactiveMathService.findSquare(i));

    }


    //Only if the value is within the range of 10 and 20 will it be sent to the pipeline
    @GetMapping("square/{input}/assigment")
    public Mono<ResponseEntity<Response>> assigment(@PathVariable int input){
        return Mono.just(input)
                .filter(i -> i >=10 && i <= 20)
                .flatMap(i->reactiveMathService.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
