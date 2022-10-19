package com.codewithcaleb.webfluxdemo.controller;


import com.codewithcaleb.webfluxdemo.dto.MultiplyRequestDto;
import com.codewithcaleb.webfluxdemo.dto.Response;
import com.codewithcaleb.webfluxdemo.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;


@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

    private final ReactiveMathService reactiveMathService;

    public ReactiveMathController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return this.reactiveMathService.mutliplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input){
        return this.reactiveMathService.mutliplicationTable(input);
    }

    //i can also read the request in a Non-Blocking Way
    //i can transform my DTo into a reactive Type
    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String,String> headers){

        System.out.println(headers);
        return this.reactiveMathService.multiply(requestDtoMono);
    }
}
